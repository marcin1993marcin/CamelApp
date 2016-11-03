package com.app.camel.routes;

import com.app.camel.processor.skill.SelectAllSkill;
import com.app.camel.processor.skill.SelectSkill;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.METHOD_GET;
import static com.app.camel.restconfiguration.RestConfiguration.PARAM_ID;
import static com.app.camel.restconfiguration.RestConfiguration.REST_URL;

public class SkillRoute extends RouteBuilder {

    private static final String SKILL = "skill";
    private static final String SKILL_REST_URL = REST_URL + SKILL;

    @Override
    public void configure() throws Exception {
        ExceptionRoutes.buildExceptionRoute(this);

        from(SKILL_REST_URL + METHOD_GET)
                .process(new SelectAllSkill())
                .transform().body();

        from(SKILL_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectSkill())
                .transform().body();

    }
}
