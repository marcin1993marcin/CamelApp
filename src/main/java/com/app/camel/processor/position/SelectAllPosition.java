package com.app.camel.processor.position;

import com.app.camel.dao.PositionRepository;
import com.app.camel.dao.impl.PositionRepositoryImpl;
import com.app.camel.dto.Position;
import com.app.camel.model.tables.records.PositionRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllPosition implements Processor {

    private final PositionRepository positionRepository = new PositionRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<PositionRecord> positions = positionRepository.getAll();

        List<Position> positionList = positions.stream().map(positionEntity -> Position.builder()
                .id(Long.valueOf(positionEntity.getId()))
                .position(positionEntity.getPosition())
                .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(positionList));
    }
}
