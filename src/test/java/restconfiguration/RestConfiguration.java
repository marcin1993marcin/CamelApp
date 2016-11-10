package restconfiguration;

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

    public static final String REQUEST_URL = configuration.getString("RequestUrl");
    public static final String REQUEST_JSON_LOCATION =  configuration.getString("RequestJsonLocation");


}
