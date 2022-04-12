package com.timetracker.auto.utils;

import groovy.util.logging.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Log4j2
public class PropertyUtil {
    private PropertyUtil(){}

    public static String getPropertyValueByKey(String key) {
        String value = null;
        String propertiesPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "config.properties").normalize().toString();

        try(FileInputStream inputStream = new FileInputStream(propertiesPath)){
            Properties properties = new Properties();
            properties.load(inputStream);
            value = properties.getProperty(key);
        } catch (IOException e) {

        }
        return value;
    }
}
