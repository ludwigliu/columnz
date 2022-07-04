package org.liu.luiz.formatter;

import java.util.function.Function;

public class NullConverter implements Converter<Object, Object, Object>{
    @Override
    public Function<Object, Object> toPattern(Object o) {
        return ob -> ob;
    }
}
