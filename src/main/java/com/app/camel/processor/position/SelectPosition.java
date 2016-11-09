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

import java.util.Optional;

import static java.lang.Long.valueOf;

public class SelectPosition implements Processor {

    private final PositionRepository positionRepository = new PositionRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid position ID of value: \"" + id + "\"");

        Optional<PositionRecord> positionRecord = positionRepository.get(Integer.parseInt(id));

        if (positionRecord.isPresent()) {
            Position position = Position.builder()
                    .id(valueOf(positionRecord.get().getId()))
                    .position(positionRecord.get().getPosition())
                    .build();
            exchange.getIn().setBody(gson.toJson(position));
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
        }
    }
}
