package com.app.camel.routes;

import com.app.camel.processor.user.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;

public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from(RestUrl + "user" + MethodGet).to("direct:select");
        from(RestUrl + "user/{id}"+ MethodGet).to("direct:idSelect");
        from(RestUrl + "user" + MethodPost).to("direct:post");
        from(RestUrl + "user" + MethodPut).to("direct:put");
        from(RestUrl + "user/{id}"+ MethodPut).to("direct:putId");
        from(RestUrl + "user" + MethodDelete).to("direct:delete");
        from(RestUrl + "user/{id}"+ MethodDelete).to("direct:deleteId");

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
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });
        from("direct:putId")
                .process(new PutUser());

        from("direct:delete")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        //TODO delete all
                    }
                });
        from("direct:deleteId").process(new DeleteByIdUser())
                .transform().body();
    }
}