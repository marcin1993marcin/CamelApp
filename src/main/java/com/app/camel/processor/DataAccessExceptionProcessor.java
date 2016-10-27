package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class DataAccessExceptionProcessor implements Processor {

    private final static Logger logger = Logger.getLogger(DataAccessExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        logger.error("In processor: DataAccessException");
        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.CLIENT_ERROR_EXPECTATION_FAILED);
        exchange.getOut().setBody(response);

    }
}
