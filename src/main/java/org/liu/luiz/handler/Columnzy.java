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

    Class<T> clazz;

    public Columnzy(Class<T> clas) {
        clazz = clas;
        annotatedFields = Arrays.stream(clas.getDeclaredFields())
                .filter(f -> f.getAnnotation(Column.class) != null)
                .collect(Collectors.toList());
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

                        if (val != null && col.converter() != NullConverter.class && !col.pattern().equals("")) {
                            Converter converter = col.converter().getConstructor().newInstance();
                            val = converter.toPattern(col.pattern()).apply(val);
                        }
                        values.put(col.name(), val);
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
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
