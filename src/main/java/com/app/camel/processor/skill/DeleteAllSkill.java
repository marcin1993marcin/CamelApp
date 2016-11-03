package com.app.camel.processor.skill;

import com.app.camel.dao.SkillRepository;
import com.app.camel.dao.impl.SkillRepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class DeleteAllSkill implements Processor {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);

        if (!skillRepository.deleteAll()) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
