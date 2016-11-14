package com.app.camel.processor.userskill;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.dao.impl.UserSkillRepositoryImpl;
import com.app.camel.dto.UserSkill;
import com.app.camel.model.tables.records.UserSkillRecord;
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

import static java.util.stream.Collectors.toList;

public class PutUserSkills implements Processor {

    private final UserSkillRepository userSkillRepository = new UserSkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String userId = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(userId), "Invalid user ID of value: \"" + userId + "\"");

        String select = exchange.getIn().getBody(String.class);
        Preconditions.checkNotNull(select, "Body is null");

        Type listType = new TypeToken<List<UserSkill>>() {}.getType();
        List<UserSkill> userSkills = gson.fromJson(select, listType);

        List<UserSkillRecord> userSkillRecords = userSkills.stream().map(userSkillEntity -> {
            UserSkillRecord userSkillRecord = new UserSkillRecord();
            userSkillRecord.setSkillId(userSkillEntity.getSkillId());
            userSkillRecord.setUserId(Integer.parseInt(userId));
            userSkillRecord.setLevel(userSkillEntity.getLevel());
            userSkillRecord.setNote(userSkillEntity.getNote());
            return userSkillRecord;
        }).collect(toList());

        Response response = exchange.getIn().getHeader(RestletConstants.RESTLET_RESPONSE, Response.class);
        response.setStatus(Status.SUCCESS_ACCEPTED);

        if (!userSkillRepository.update(userSkillRecords)) {
            response.setStatus(Status.REDIRECTION_NOT_MODIFIED);
        }
    }
}