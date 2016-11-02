package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.jooq.exception.DataAccessException;
import org.restlet.Response;
import org.restlet.data.Status;

public class DataAccessExceptionProcessor implements Processor {

    private static final Logger LOGGER = Logger.getLogger(DataAccessExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        DataAccessException dataAccessException =
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, DataAccessException.class);

        LOGGER.error("ERROR MESSAGE", dataAccessException);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        exchange.getOut().setBody(response);

    }
}
