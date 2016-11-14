package com.app.camel.routes;

import com.app.camel.processor.DataAccessExceptionProcessor;
import com.app.camel.processor.customer.*;
import org.apache.camel.builder.RouteBuilder;
import org.jooq.exception.DataAccessException;

import static com.app.camel.restconfiguration.RestConfiguration.*;


public class CustomerRoute extends RouteBuilder {

    private static final String CUSTOMER = "customer";
    private static final String CUSTOMER_REST_URL = REST_URL + CUSTOMER;

    @Override
    public void configure() throws Exception {

        onException(DataAccessException.class)
                .process(new DataAccessExceptionProcessor())
                .transform().body();


        from(CUSTOMER_REST_URL + METHOD_GET).to("direct:select");
        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_GET).to("direct:idSelect");
        from(CUSTOMER_REST_URL + METHOD_POST).to("direct:post");
        from(CUSTOMER_REST_URL + METHOD_PUT).to("direct:put");
        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_PUT).to("direct:putId");
        from(CUSTOMER_REST_URL + METHOD_DELETE).to("direct:delete");
        from(CUSTOMER_REST_URL + PARAM_ID + METHOD_DELETE).to("direct:deleteId");

        from("direct:select")
                .process(new SelectAllCustomer())
                .transform().body();

        from("direct:idSelect")
                .process(new SelectByIdCustomer())
                .transform().body();

        from("direct:post")
                .process(new InsertCustomer())
                .transform().body();

        from("direct:put")
                .process(new PutAllCustomer());

        from("direct:putId")
                .process(new PutCustomer());

        from("direct:delete")
                .process(new DeleteAllCustomer());

        from("direct:deleteId").process(new DeleteByIdCustomer())
                .transform().body();


    }
}