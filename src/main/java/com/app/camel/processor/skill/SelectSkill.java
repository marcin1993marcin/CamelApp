package com.app.camel.processor.skill;

import com.app.camel.dao.SkillRepository;
import com.app.camel.dao.impl.SkillRepositoryImpl;
import com.app.camel.dto.Skill;
import com.app.camel.model.tables.records.SkillRecord;
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

public class SelectSkill implements Processor {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid skill ID of value: \"" + id + "\"");

        Optional<SkillRecord> skillRecord = skillRepository.get(Integer.parseInt(id));

        if (skillRecord.isPresent()) {
            Skill skill = Skill.builder()
                    .id(Long.valueOf(skillRecord.get().getId()))
                    .name(skillRecord.get().getName())
                    .parentId(skillRecord.get().getParentId())
                    .build();

            exchange.getIn().setBody(gson.toJson(skill));
        } else {
            Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
            response.setStatus(Status.SUCCESS_NO_CONTENT);
            exchange.getOut().setBody(response);
        }
    }
}
