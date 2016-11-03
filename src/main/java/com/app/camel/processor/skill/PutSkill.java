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

public class PutSkill implements Processor {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        String id = exchange.getIn().getHeader("id", String.class);

        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid skill ID of value: \"" + id + "\"");

        String select = exchange.getIn().getBody(String.class);

        Preconditions.checkNotNull(select, "Body is null");

        Skill skill = gson.fromJson(select, Skill.class);

        SkillRecord skillRecord = new SkillRecord();
        skillRecord.setId(Integer.parseInt(id));
        skillRecord.setName(skill.getName());
        skillRecord.setParentId(skill.getParentId());

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_ACCEPTED);

        if (!skillRepository.update(skillRecord)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
