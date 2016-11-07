package com.app.camel.processor.userskill;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.dao.impl.UserSkillRepositoryImpl;
import com.app.camel.dto.UserSkill;
import com.app.camel.model.tables.records.UserSkillRecord;
import com.app.camel.util.Precondition;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectUserSkills implements Processor {

    private final UserSkillRepository userSkillRepository = new UserSkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        String userId = exchange.getIn().getHeader("id", String.class);
        Preconditions.checkArgument(Precondition.isInteger(userId), "Invalid user ID of value: \"" + userId + "\"");

        Collection<UserSkillRecord> userSkills = userSkillRepository.getAll(Integer.parseInt(userId));

        List<UserSkill> userSkillList = userSkills.stream().map(userSkillEntity -> UserSkill.builder()
                .skillId(userSkillEntity.getSkillId())
                .userId(userSkillEntity.getUserId())
                .level(userSkillEntity.getLevel())
                .note(userSkillEntity.getNote())
                .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(userSkillList));
    }
}
