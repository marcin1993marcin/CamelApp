package com.app.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.log4j.Logger;
import org.restlet.Response;
import org.restlet.data.Status;

public class IllegalArgumentExceptionProcessor implements Processor{

    private static final Logger LOGGER = Logger.getLogger(IllegalArgumentExceptionProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {

        IllegalArgumentException illegalArgumentException =
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, IllegalArgumentException.class);

        LOGGER.error("ERROR MESSAGE", illegalArgumentException);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        exchange.getOut().setBody(response);
    }
}
