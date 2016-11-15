package com.app.camel.processor.position;

import com.app.camel.dao.PositionRepository;
import com.app.camel.dao.impl.PositionRepositoryImpl;
import com.app.camel.dto.Position;
import com.app.camel.model.tables.records.PositionRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class PutPosition implements Processor {

    private final PositionRepository positionRepository = new PositionRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid position ID of value: \"" + id + "\"");
        String select = exchange.getIn().getBody(String.class);
        Preconditions.checkNotNull(select, "Body is null");

        PositionRecord positionRecord = getPositionRecord(id, gson.fromJson(select, Position.class));

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_CREATED);

        if (!positionRepository.update(positionRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }
        exchange.getOut().setBody(response);
    }

    private PositionRecord getPositionRecord(String id, Position position) {
        PositionRecord positionRecord = new PositionRecord();
        positionRecord.setId(Integer.parseInt(id));
        positionRecord.setPosition(position.getPosition());
        return positionRecord;
    }
}
