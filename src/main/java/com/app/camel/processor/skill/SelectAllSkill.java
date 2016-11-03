package com.app.camel.processor.skill;

import com.app.camel.dao.SkillRepository;
import com.app.camel.dao.impl.SkillRepositoryImpl;
import com.app.camel.dto.Skill;
import com.app.camel.model.tables.records.SkillRecord;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class SelectAllSkill implements Processor {

    private final SkillRepository skillRepository = new SkillRepositoryImpl();
    private final Gson gson = new GsonBuilder().create();

    @Override
    public void process(Exchange exchange) throws Exception {
        Collection<SkillRecord> skills = skillRepository.getAll();

        List<Skill> skillList = skills.stream().map(skillEntity -> Skill.builder()
                            .id(Long.valueOf(skillEntity.getId()))
                            .name(skillEntity.getName())
                            .parentId(skillEntity.getParentId())
                            .build()
        ).collect(toList());

        exchange.getIn().setBody(gson.toJson(skillList));
    }
}
