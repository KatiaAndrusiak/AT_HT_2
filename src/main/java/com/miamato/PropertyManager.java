package com.miamato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertyManager {

    private static final Logger logger = LogManager.getLogger(PropertyManager.class.getSimpleName());
    private static PropertyManager instance = null;
    private static final Properties properties = new Properties();

    private PropertyManager() {
        loadProperties(System.getProperty("testdata.property.path"));
    }

    public static String getProperty(String propertyName){
        if(instance == null)
            instance = new PropertyManager();
        return properties.getProperty(propertyName);
    }

    private void loadProperties(String filePath) {
        logger.info("Trying to access property file: " + filePath);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            LogUtil.logStackTrace(e, logger);
        } catch (IOException e) {
            LogUtil.logStackTrace(e, logger);
        } catch (NullPointerException e) {
            LogUtil.logStackTrace(e, logger);
        }
    }
}