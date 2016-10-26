package com.app.camel.routes;

import com.app.camel.processor.user.*;
import com.app.camel.restconfiguration.RestConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;


public class UserRoute extends RouteBuilder {

    private static final String USER = "user";
    private static final String USER_REST_URL = REST_URL + USER;

    @Override
    public void configure() throws Exception {



        from(USER_REST_URL + METHOD_GET).to("direct:select");
        from(USER_REST_URL + PARAM_ID + METHOD_GET).to("direct:idSelect");
        from(USER_REST_URL + METHOD_POST).to("direct:post");
        from(USER_REST_URL + METHOD_PUT).to("direct:put");
        from(USER_REST_URL + PARAM_ID + METHOD_PUT).to("direct:putId");
        from(USER_REST_URL + METHOD_DELETE).to("direct:delete");
        from(USER_REST_URL + PARAM_ID + METHOD_DELETE).to("direct:deleteId");

        from("direct:select")
                .process(new SelectAllUser())
                .transform().body();

        from("direct:idSelect")
                .process(new SelectByIdUser())
                .transform().body();

        from("direct:post")
                .process(new InsertUser())
                .transform().body();

        from("direct:put")
                .process(new PutAllUser());

        from("direct:putId")
                .process(new PutUser());

        from("direct:delete")
               .process(new DeleteAllUser());

        from("direct:deleteId").process(new DeleteByIdUser())
                .transform().body();
    }
}