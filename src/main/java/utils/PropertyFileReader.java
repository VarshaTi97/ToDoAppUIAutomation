package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//Helper class to read data of property file
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

    // get data of key in property file
    public String getValue(String key){
        return properties.getProperty(key);
    }

    // get data from specific property file for a key
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
