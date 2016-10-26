package com.app.camel.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configuration {

    private static org.apache.commons.configuration.Configuration configuration;

    static {
        try {
            configuration = new PropertiesConfiguration("configuration.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String DATABASE_URL = configuration.getString("database.URL");
    public static final String DATABASE_USER = configuration.getString("database.USER");
    public static final String DATABASE_PASSWORD = configuration.getString("database.PASSWORD");
    public static final String DATABASE_DRIVER = configuration.getString("database.DRIVER");

    public static final String FLYWAY_SCHEMA_NAME = configuration.getString("flyway.SCHEMA_NAME");
    public static final String FLYWAY_URL = configuration.getString("flyway.URL_FOR_FLYWAY_CONFIGURATION");
}
