package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.jooq.exception.DataAccessException;
import org.restlet.Response;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAccessExceptionProcessor implements Processor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataAccessExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        DataAccessException exception = exchange.getException(DataAccessException.class);

        // review - to jest zbedne - jest automatycznie w logach
        LOGGER.error("In processor: DataAccessException");
        // review - stacktrace to chyba sam sie zaloguje, jezeli damy tak:
        LOGGER.error("ERROR MESSAGE", exception);
        // LOGGER.error(exception.getStackTrace());
        // review ponizsze nie wiem - trzeba zobaczyc w realu co bedzie, zakładam, że wystarczy
        // LOGGER.error("DataAccessException occured", exception);
        LOGGER.error(exception.getCause().getMessage());
        LOGGER.error(exception.getMessage());

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        exchange.getOut().setBody(response);

    }
}
