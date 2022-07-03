package org.liu.luiz.handler;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.liu.luiz.annotation.Column;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Columnzy<T> {

    public Object[] parse(Class<T> clazz, Object obj) {
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> values = new HashMap<>();
        List<Field> annotatedFields = Arrays.stream(fields).filter(f -> f.getAnnotation(Column.class) != null).collect(Collectors.toList());

        if(annotatedFields.size() == 0 ) return null;

        annotatedFields.forEach(
                f -> {
                    try {
                        Column col = f.getAnnotation(Column.class);
                        Object val = FieldUtils.readField(f, obj, true);
                        values.put(col.name(), val);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Object[] objects = new Object[annotatedFields.size()];
        int i = 0;
        for (String name : values.keySet()) {
            objects[i++] = values.get(name);
        }
        return objects;
    }
}
