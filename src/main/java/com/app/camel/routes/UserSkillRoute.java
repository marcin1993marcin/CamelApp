package com.app.camel.routes;

import com.app.camel.processor.userskill.SelectUserSkill;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.METHOD_GET;
import static com.app.camel.restconfiguration.RestConfiguration.PARAM_ID;
import static com.app.camel.restconfiguration.RestConfiguration.REST_URL;

public class UserSkillRoute extends RouteBuilder {

    private static final String USER = "user";
    private static final String SKILL = "/skill";
    private static final String USER_SKILL_REST_URL = REST_URL + USER + PARAM_ID + SKILL;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(USER_SKILL_REST_URL + METHOD_GET)
                .process(new SelectUserSkill())
                .transform().body();

    }
}
