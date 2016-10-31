package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.jooq.exception.DataAccessException;
import org.restlet.Response;
import org.restlet.data.Status;

public class DataAccessExceptionProcessor implements Processor {

    private final static Logger LOGGER = Logger.getLogger(DataAccessExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        DataAccessException exception = exchange.getException(DataAccessException.class);


        LOGGER.error(exception.getStackTrace());
        LOGGER.error(exception.getCause().getMessage());
        LOGGER.error(exception.getMessage());

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        exchange.getOut().setBody(response);

    }
}
