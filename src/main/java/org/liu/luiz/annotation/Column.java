package org.liu.luiz.annotation;

import org.liu.luiz.formatter.Converter;
import org.liu.luiz.formatter.NullConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name() default "";

    String pattern() default "";

    Class<? extends Converter> converter() default NullConverter.class;
}
