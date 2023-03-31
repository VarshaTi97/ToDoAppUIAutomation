package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    //return current date and timestamp
    public static String getCurrentTimestamp(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }

}
