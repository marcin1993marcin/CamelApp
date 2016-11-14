package com.app.camel.routes;

import com.app.camel.processor.customer.*;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;


public class CustomerRoute extends RouteBuilder {

    private static final String CUSTOMER = "customer";
    private static final String CUSTOMER_REST_URL = REST_URL + CUSTOMER;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(CUSTOMER_REST_URL + METHOD_GET)
                .process(new SelectAllCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + METHOD_POST)
                .process(new InsertCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + METHOD_PUT)
                .process(new PutAllCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_PUT)
                .process(new PutCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + METHOD_DELETE)
                .process(new DeleteAllCustomer())
                .transform().body();

        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_DELETE)
                .process(new DeleteCustomer())
                .transform().body();
    }
}