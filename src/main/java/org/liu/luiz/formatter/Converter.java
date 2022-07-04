package org.liu.luiz.formatter;

import java.util.function.Function;

public interface Converter<T, R, P> {

    Function<T, R> toPattern(P p);
}
