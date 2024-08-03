package com.eurowings.utilities;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;

public class Environment {

    public static final String baseURL;
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
        baseURL = properties.getProperty("baseURL");
    }

    /**
     * Returns the value of Environment class static variables
     * @param variableName
     * @return value of variable
     */
    public static String getValue(String variableName) {
        String value = "";
        try {
            Field field = Environment.class.getDeclaredField(variableName);
            field.setAccessible(true);
            value = (String) field.get(null);
        } catch (NoSuchFieldException e) {
            System.out.println("Field " + variableName + " not found in Environment class.");
            return variableName;
        }  catch (IllegalAccessException e) {
            System.out.println("Access denied to field " + variableName + ".");
            return variableName;
        }
        return value;
    }

    public static void setVariable(String key, String value) {

        try {
            String path = System.getProperty("user.dir") + "/src/test/resources/environments/" + environment + ".properties";
            // Load existing properties
            FileInputStream in = new FileInputStream(path);
            properties.load(in);
            in.close();

            // Set the new property value
            properties.setProperty(key, value);

            // Write properties back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            for (String propKey : properties.stringPropertyNames()) {
                writer.write(propKey + "=" + properties.getProperty(propKey));
                writer.newLine();
            }
            // Close the writer
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to configuration.properties file.");
            e.printStackTrace();
        }
    }

}
