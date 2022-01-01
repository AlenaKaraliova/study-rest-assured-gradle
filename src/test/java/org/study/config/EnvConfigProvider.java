package org.study.config;

import java.io.IOException;
import java.util.Properties;

public class EnvConfigProvider {

    public static String zipURL;
    public static String zipByCityURL;
    public static String todoURL;

    private EnvConfigProvider() {
    }

    public static void init() {
        Properties properties = new Properties();
        try {
            properties.load(EnvConfigProvider.class.getResourceAsStream("/local-test.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        zipURL = properties.getProperty("zip.url");
        zipByCityURL = properties.getProperty("zip.city.url");
        todoURL = properties.getProperty("todo.url");
    }

}
