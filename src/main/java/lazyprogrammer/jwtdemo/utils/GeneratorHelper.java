package lazyprogrammer.jwtdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GeneratorHelper {

    public static String generateTraceContextId(String prefix) {

        Date date = new Date();

        String dateSection = new SimpleDateFormat("yyyy-MMdd-HHmm")
                .format(date);

        String randomSubString = UUID.randomUUID().toString().substring(0, 5);

        String id = String.format("%s-%s-%s", prefix, dateSection, randomSubString);

        return id;

    }

    public static String currentDateTimeToString() {

        Date date = new Date();

        String dateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .format(date);

        return dateStr;

    }

}
