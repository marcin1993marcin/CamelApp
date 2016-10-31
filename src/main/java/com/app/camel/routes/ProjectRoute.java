package com.app.camel.routes;

import com.app.camel.processor.DataAccessExceptionProcessor;
import com.app.camel.processor.ExceptionProcessor;
import com.app.camel.processor.project.*;
import org.apache.camel.builder.RouteBuilder;
import org.jooq.exception.DataAccessException;

import static com.app.camel.restconfiguration.RestConfiguration.*;


public class ProjectRoute extends RouteBuilder {

    private static final String PROJECT = "project";
    private static final String PROJECT_REST_URL = REST_URL + PROJECT;


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

        from(PROJECT_REST_URL + METHOD_GET)
                .process(new SelectAllProject())
                .transform().body();

        from(PROJECT_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectProject())
                .transform().body();

        from(PROJECT_REST_URL + METHOD_POST)
                .process(new PostProject())
                .transform().body();

        from(PROJECT_REST_URL + METHOD_PUT)
                .process(new PutAllProject())
                .transform().body();

        from(PROJECT_REST_URL + PARAM_ID + METHOD_PUT)
                .process(new PutProject())
                .transform().body();

        from(PROJECT_REST_URL + METHOD_DELETE)
                .process(new DeleteAllProject())
                .transform().body();

        from(PROJECT_REST_URL + PARAM_ID + METHOD_DELETE)
                .process(new DeleteProject())
                .transform().body();
    }


}
