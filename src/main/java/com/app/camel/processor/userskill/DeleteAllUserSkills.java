package com.app.camel.processor.userskill;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.dao.impl.UserSkillRepositoryImpl;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

public class DeleteAllUserSkills implements Processor {

    private final UserSkillRepository userSkillRepository = new UserSkillRepositoryImpl();

    @Override
    public void process(Exchange exchange) throws Exception {
        String userId = exchange.getIn().getHeader("userId", String.class);
        Preconditions.checkArgument(Precondition.isInteger(userId), "Invalid user id of value \"" + userId + "\"");

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);

        if(!userSkillRepository.deleteAll(Integer.parseInt(userId))) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
