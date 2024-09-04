package ui.eurowings.utilities;

import ui.eurowings.utilities.ConfigurationReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environment {

    public static final String UI_baseURL;
    public static final String API_baseURL;

    public static String environment;
    public static Properties properties;


    static {
        environment = System.getProperty("environment") != null ? System.getProperty("environment") : ConfigurationReader.getProperty("environment");
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/environments/" + environment + ".properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UI_baseURL = properties.getProperty("UI_baseURL");
    }

    static {
        environment = System.getProperty("environment") != null ? System.getProperty("environment") : ConfigurationReader.getProperty("environment");
        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/environments/" + environment + ".properties";

            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        API_baseURL = properties.getProperty("API_baseURL");
    }

}
