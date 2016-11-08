package com.app.camel.routes;

import com.app.camel.processor.position.*;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;
import static com.app.camel.restconfiguration.RestConfiguration.METHOD_DELETE;
import static com.app.camel.restconfiguration.RestConfiguration.PARAM_ID;

public class PositionRoute extends RouteBuilder {

    private static final String POSITION = "position";
    private static final String POSITION_REST_URL = REST_URL + POSITION;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(POSITION_REST_URL + METHOD_GET)
                .process(new SelectAllPosition())
                .transform().body();

        from(POSITION_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectPosition())
                .transform().body();

        from(POSITION_REST_URL + METHOD_POST)
                .process(new PostPosition())
                .transform().body();

        from(POSITION_REST_URL + METHOD_PUT)
                .process(new PutAllPosition())
                .transform().body();

        from(POSITION_REST_URL + PARAM_ID + METHOD_PUT)
                .process(new PutPosition())
                .transform().body();

        from(POSITION_REST_URL + METHOD_DELETE)
                .process(new DeleteAllPosition())
                .transform().body();

        from(POSITION_REST_URL + PARAM_ID + METHOD_DELETE)
                .process(new DeletePosition())
                .transform().body();
    }
}
