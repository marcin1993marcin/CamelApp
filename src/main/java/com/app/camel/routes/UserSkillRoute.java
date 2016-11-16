package com.app.camel.routes;

import com.app.camel.processor.userskill.*;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;

public class UserSkillRoute extends RouteBuilder {

    private static final String USER = "user";
    private static final String SKILL = "/skill";
    private static final String USER_PARAM_ID = "/{userId}";
    private static final String SKILL_PARAM_ID = "/{skillId}";
    private static final String USER_SKILL_REST_URL = REST_URL + USER + USER_PARAM_ID + SKILL;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(USER_SKILL_REST_URL + METHOD_GET)
                .process(new SelectUserSkills())
                .transform().body();

        from(USER_SKILL_REST_URL + METHOD_POST)
                .process(new PostUserSkills())
                .transform().body();

        from(USER_SKILL_REST_URL + METHOD_PUT)
                .process(new PutUserSkills())
                .transform().body();

        from(USER_SKILL_REST_URL + SKILL_PARAM_ID + METHOD_DELETE)
                .process(new DeleteUserSkill())
                .transform().body();

        from(USER_SKILL_REST_URL + METHOD_DELETE)
                .process(new DeleteAllUserSkills())
                .transform().body();
    }
}
