package com.app.camel.processor;

import com.app.camel.dto.error.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonSyntaxExceptionProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonSyntaxExceptionProcessor.class);
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        JsonSyntaxException jsonSyntaxException =
                exchange.getProperty(Exchange.EXCEPTION_CAUGHT, JsonSyntaxException.class);

        LOGGER.error("ERROR MESSAGE", jsonSyntaxException);

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.CLIENT_ERROR_BAD_REQUEST);

        String json = gson.toJson(new ErrorResponse("code", jsonSyntaxException.getMessage()));

        exchange.getOut().setBody(response);
        exchange.getOut().setBody(json);

    }
}
