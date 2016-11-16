package com.app.camel.processor.userskill;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.dao.impl.UserSkillRepositoryImpl;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.restlet.Response;
import org.restlet.data.Status;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class DeleteUserSkill implements Processor {

    private final UserSkillRepository userSkillRepository = new UserSkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String userId = exchange.getIn().getHeader("userId", String.class);
        Preconditions.checkArgument(Precondition.isInteger(userId), "Invalid user id of value \"" + userId + "\"");

        String skillId = exchange.getIn().getHeader("skillId", String.class);
        Preconditions.checkArgument(Precondition.isInteger(skillId), "Invalid skill id of value \"" + skillId + "\"");


        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_NO_CONTENT);

        if (!userSkillRepository.delete(Integer.parseInt(userId), Integer.parseInt(skillId))) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }

        exchange.getOut().setBody(response);
    }
}
