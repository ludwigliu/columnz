package org.liu.luiz.handler;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.liu.luiz.annotation.Column;
import org.liu.luiz.formatter.Converter;
import org.liu.luiz.formatter.NullConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Columnzy<T> {

    List<Field> annotatedFields;
    List<String> headers = new ArrayList<>();

    Map<String, Converter> converterMap = new HashMap<>();

    Class<T> clazz;

    public Columnzy(Class<T> clas) {
        clazz = clas;

        init();
    }

    private void init() {
        annotatedFields = Arrays.stream(this.clazz.getDeclaredFields())
                .filter(f -> f.getAnnotation(Column.class) != null)
                .collect(Collectors.toList());

        annotatedFields.forEach(f -> {
            Column col = f.getAnnotation(Column.class);
            if(col.converter() != NullConverter.class && !col.pattern().equals("")) {
                try {
                    converterMap.put(col.name(), col.converter().getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public Object[] parse(T target) {

        if (annotatedFields.size() == 0) return null;
        if (headers.size() > annotatedFields.size())
            throw new IllegalArgumentException(String.format("Required headers size %s is more than annotated fields %s", headers.size(), annotatedFields.size()));

        Map<String, Object> values = new HashMap<>();
        annotatedFields.forEach(
                f -> {
                    try {
                        Column col = f.getAnnotation(Column.class);
                        Object val = FieldUtils.readField(f, target, true);

                        if(this.converterMap.containsKey(col.name())) {
                            Converter converter = this.converterMap.get(col.name());
                            val = converter.toPattern(col.pattern()).apply(val);
                        }
                        values.put(col.name(), val);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Object[] objects = new Object[headers.size()];
        int i = 0;
        for (String head : headers) {
            objects[i++] = values.get(head);
        }
        return objects;
    }

    public List<Object[]> parse(T[] targets) {
        List<Object[]> values = new ArrayList<>();

        for (T t : targets) {
            values.add(this.parse(t));
        }
        return values;
    }

    public void setHeaders(String[] headers) {
        if (headers.length == 0) throw new IllegalArgumentException("Headers size can't be 0");
        this.headers = Arrays.asList(headers);
    }

}
