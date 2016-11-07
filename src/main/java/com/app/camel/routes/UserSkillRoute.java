package com.app.camel.routes;

import com.app.camel.processor.userskill.DeleteUserSkills;
import com.app.camel.processor.userskill.PostUserSkills;
import com.app.camel.processor.userskill.SelectUserSkills;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;

public class UserSkillRoute extends RouteBuilder {

    private static final String USER = "user";
    private static final String SKILL = "/skill";
    private static final String USER_SKILL_REST_URL = REST_URL + USER + PARAM_ID + SKILL;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(USER_SKILL_REST_URL + METHOD_GET)
                .process(new SelectUserSkills())
                .transform().body();

        from(USER_SKILL_REST_URL + METHOD_POST)
                .process(new PostUserSkills())
                .transform().body();

        from(USER_SKILL_REST_URL + METHOD_DELETE)
                .process(new DeleteUserSkills())
                .transform().body();
    }
}
