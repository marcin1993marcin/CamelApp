package com.app.camel.processor.skill;

import com.app.camel.dao.SkillRepository;
import com.app.camel.dao.impl.SkillRepositoryImpl;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class DeleteSkill implements Processor {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {
        String id = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(id), "Invalid skill ID of value: \"" + id + "\"");

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);

        if (!skillRepository.delete(Integer.parseInt(id))) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
