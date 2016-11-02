package com.app.camel.routes;

import com.app.camel.processor.*;
import com.google.gson.JsonSyntaxException;
import org.apache.camel.builder.RouteBuilder;
import org.jooq.exception.DataAccessException;

public class ExceptionRoutes {

    public static void buildExceptionRoute(RouteBuilder builder) {
        builder.onException(DataAccessException.class)
                .handled(true)
                .process(new DataAccessExceptionProcessor());

        builder.onException(Exception.class)
                .handled(true)
                .process(new ExceptionProcessor());

        builder.onException(IllegalArgumentException.class)
                .handled(true)
                .process(new IllegalArgumentExceptionProcessor());

        builder.onException(NullPointerException.class)
                .handled(true)
                .process(new NullPointerExceptionProcessor());

        builder.onException(JsonSyntaxException.class)
                .handled(true)
                .process(new JsonSyntaxExceptionProcessor());
    }
}
