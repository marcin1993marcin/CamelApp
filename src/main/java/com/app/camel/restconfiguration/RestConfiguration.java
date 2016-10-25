package com.app.camel.restconfiguration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RestConfiguration {


    private static org.apache.commons.configuration.Configuration restConfiguration;

    static {
        try {
            restConfiguration = new PropertiesConfiguration("restConfiguration.properties");
        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String RestUrl = restConfiguration.getString("RestUrl");
    public static final String MethodGet = restConfiguration.getString("MethodGet");
    public static final String MethodPost = restConfiguration.getString("MethodPost");
    public static final String MethodPut = restConfiguration.getString("MethodPut");
    public static final String MethodDelete = restConfiguration.getString("MethodDelete");


}
