package com.app.camel.processor.position;

import com.app.camel.dao.PositionRepository;
import com.app.camel.dao.impl.PositionRepositoryImpl;
import com.app.camel.dto.Position;
import com.app.camel.model.tables.records.PositionRecord;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class PostPosition implements Processor {

    private final PositionRepository positionRepository = new PositionRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        Position position = gson.fromJson(select, Position.class);
        PositionRecord positionRecord = new PositionRecord();
        positionRecord.setPosition(position.getPosition());

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!positionRepository.insert(positionRecord)) {
            response.setStatus(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
        }

        exchange.getOut().setBody(response);
    }
}
