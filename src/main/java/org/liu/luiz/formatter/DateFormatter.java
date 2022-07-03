package org.liu.luiz.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public String convert(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}
