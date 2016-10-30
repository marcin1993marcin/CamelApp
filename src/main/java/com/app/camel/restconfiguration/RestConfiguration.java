package com.app.camel.restconfiguration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class RestConfiguration {

    // review chyba może być final
    private static org.apache.commons.configuration.Configuration configuration;

    static {
        try {
            configuration = new PropertiesConfiguration("configuration.properties");
        } catch (ConfigurationException e) {
            // review - a gdzie logi?
            throw new RuntimeException(e);
        }
    }

    // review - nie wiem czy te wartosci powinny być w pliku konfiguracyjnym. Zajdzie taki przypadek, że można je zmienić na inne?
    public static final String REST_URL = configuration.getString("RestUrl");
    public static final String METHOD_GET = configuration.getString("MethodGet");
    public static final String METHOD_POST = configuration.getString("MethodPost");
    public static final String METHOD_PUT = configuration.getString("MethodPut");
    public static final String METHOD_DELETE = configuration.getString("MethodDelete");

    public static final String PARAM_ID = "/{id}";


}
