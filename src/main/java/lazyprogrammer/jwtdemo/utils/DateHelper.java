package lazyprogrammer.jwtdemo.utils;

import io.micrometer.common.util.StringUtils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static Date parseDate(String date) throws ParseException {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = formatter.parse(date);
        return parsed;

    }

    public static String formatDate(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return formatter.format(date);
    }

    public static String formatDateToLegacy(Date date) {
        if (date == null) return null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

}
