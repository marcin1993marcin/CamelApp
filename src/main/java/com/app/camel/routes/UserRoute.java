package com.app.camel.routes;

import com.app.camel.processor.DataAccessExceptionProcessor;
import com.app.camel.processor.ExceptionProcessor;
import com.app.camel.processor.IllegalArgumentExceptionProcessor;
import com.app.camel.processor.user.*;
import org.apache.camel.builder.RouteBuilder;
import org.jooq.exception.DataAccessException;

import static com.app.camel.restconfiguration.RestConfiguration.*;


public class UserRoute extends RouteBuilder {

    private static final String USER = "user";
    private static final String USER_REST_URL = REST_URL + USER;

    @Override
    public void configure() throws Exception {

        onException(DataAccessException.class)
                .handled(true)
                .process(new DataAccessExceptionProcessor())
                .transform().body();

        onException(Exception.class)
                .handled(true)
                .process(new ExceptionProcessor())
                .transform().body();

        onException(IllegalArgumentException.class)
                .handled(true)
                .process(new IllegalArgumentExceptionProcessor())
                .transform().body();

        from(USER_REST_URL + METHOD_GET)
                .process(new SelectAllUser())
                .transform().body();

        from(USER_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectUser())
                .transform().body();

        from(USER_REST_URL + METHOD_POST)
                .process(new InsertUser())
                .transform().body();

        from(USER_REST_URL + METHOD_PUT)
                .process(new PutAllUser())
                .transform().body();

        from(USER_REST_URL + PARAM_ID + METHOD_PUT)
                .process(new PutUser())
                .transform().body();

        from(USER_REST_URL + METHOD_DELETE)
                .process(new DeleteAllUser())
                .transform().body();

        from(USER_REST_URL + PARAM_ID + METHOD_DELETE)
                .process(new DeleteUser())
                .transform().body();
    }
}