package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileReader {

    private Properties properties;

    public PropertyFileReader(String filePath){
        File file = new File(filePath);
        properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public String getValue(String key){
        return properties.getProperty(key);
    }

    public static String getValue(String filePath, String key){
        File file = new File(filePath);
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
