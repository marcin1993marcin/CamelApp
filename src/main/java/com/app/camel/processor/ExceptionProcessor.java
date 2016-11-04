package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        LOGGER.error("ERROR MESSAGE", exception);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        exchange.getOut().setBody(response);
    }
}
