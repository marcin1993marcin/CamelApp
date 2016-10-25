package com.app.camel.routes;

import com.app.camel.processor.user.*;
import com.app.camel.restconfiguration.RestConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class UserRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(RestConfiguration.REST_URL + "user" + RestConfiguration.METHOD_GET).to("direct:select");
        from(RestConfiguration.REST_URL + "user/{id}"+ RestConfiguration.METHOD_GET).to("direct:idSelect");
        from(RestConfiguration.REST_URL + "user" + RestConfiguration.METHOD_POST).to("direct:post");
        from(RestConfiguration.REST_URL + "user" + RestConfiguration.METHOD_PUT).to("direct:put");
        from(RestConfiguration.REST_URL + "user/{id}"+ RestConfiguration.METHOD_PUT).to("direct:putId");
        from(RestConfiguration.REST_URL + "user" + RestConfiguration.METHOD_DELETE).to("direct:delete");
        from(RestConfiguration.REST_URL + "user/{id}"+ RestConfiguration.METHOD_DELETE).to("direct:deleteId");

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