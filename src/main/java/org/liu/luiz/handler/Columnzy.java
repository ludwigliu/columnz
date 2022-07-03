package org.liu.luiz.handler;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.liu.luiz.annotation.Column;
import org.liu.luiz.formatter.DateFormatter;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Columnzy<T> {
    List<String> headers = new ArrayList<>();

    public Object[] parse(T target) {
        Field[] fields = target.getClass().getDeclaredFields();
        List<Field> annotatedFields = Arrays.stream(fields).filter(f -> f.getAnnotation(Column.class) != null).collect(Collectors.toList());

        if (annotatedFields.size() == 0) return null;
        if (headers.size() > annotatedFields.size())
            throw new IllegalArgumentException(String.format("Required headers size %s is more than annotated fields %s", headers.size(), annotatedFields.size()));

        Map<String, Object> values = new HashMap<>();
        annotatedFields.forEach(
                f -> {
                    try {
                        Column col = f.getAnnotation(Column.class);
                        Object val = FieldUtils.readField(f, target, true);
                        if (col.dateFormat() != null && !col.dateFormat().equals("") && val != null)
                            val = new DateFormatter().convert((Date) val, col.dateFormat());
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

        for(T t : targets) {
            values.add(this.parse(t));
        }
        return values;
    }

    public void setHeaders(String[] headers) {
        if (headers.length == 0) throw new IllegalArgumentException("Headers size can't be 0");
        this.headers = Arrays.asList(headers);
    }

}
