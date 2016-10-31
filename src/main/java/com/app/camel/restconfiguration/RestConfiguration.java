package com.app.camel.restconfiguration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class RestConfiguration {

    private static final Logger LOGGER = Logger.getLogger(RestConfiguration.class);
    private static final org.apache.commons.configuration.Configuration configuration;

    static {
        try {
            configuration = new PropertiesConfiguration("configuration.properties");
        } catch (ConfigurationException e) {
            LOGGER.error("Unable to load configuration.properties file");
            throw new RuntimeException(e);
        }
    }

    public static final String REST_URL = configuration.getString("RestUrl");

    public static final String METHOD_GET = "?restletMethod=get";
    public static final String METHOD_POST = "?restletMethod=post";
    public static final String METHOD_PUT = "?restletMethod=put";
    public static final String METHOD_DELETE = "?restletMethod=delete";

    public static final String PARAM_ID = "/{id}";
}
