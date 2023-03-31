package utils;


public class DataFormatUtil {
    //creates array of string from string
    public static String[] convertToStringArray(String data){
        return data.trim().split(",");
    }
}
