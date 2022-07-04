package org.liu.luiz.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class DateConverter implements Converter<Date, String, String>{
    @Override
    public Function<Date, String> toPattern(String s) {
        return d -> new SimpleDateFormat(s).format(d);
    }
}
