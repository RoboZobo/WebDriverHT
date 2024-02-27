package config_reader;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Stream;

//Singleton
@Getter
public class ConfigReader {

    private static ConfigReader instance;
    private Properties properties = new Properties();

    private ConfigReader() {
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    private String getPropertyPath(String name) {
        String dir = ".\\src\\test\\java\\configurations\\";
        return dir.concat(Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .filter(fileName -> fileName.contains(name))
                .findFirst().orElseThrow());
    }

    public void getConfiguration(String name) {
        BufferedReader reader;
        String propertyPath = getPropertyPath(name);
        try {
            reader = new BufferedReader(new FileReader(propertyPath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Not found " + propertyPath);
        }
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}