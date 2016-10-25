package com.app.camel.routes;

import com.app.camel.processor.project.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;

public class ProjectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        from(RestUrl + "project" + MethodGet).to("direct:projectSelect");
        from(RestUrl + "project/{id}" + MethodGet).to("direct:projectSelectId");
        from(RestUrl + "project" + MethodPost).to("direct:projectPost");
        from(RestUrl + "project "+ MethodPut).to("direct:projectPut");
        from(RestUrl + "project/{id}" + MethodPut).to("direct:projectPutId");
        from(RestUrl + "project" + MethodDelete).to("direct:projectDelete");
        from(RestUrl + "project/{id}?" + MethodPost).to("direct:projectDeleteId");

        from("direct:projectSelect").process(new SelectAllProject())
                .transform().body();
        from("direct:projectSelectId")
                .process(new SelectByIdProject())
                .transform().body();
        from("direct:projectPost")
                .process(new PostProject())
                .transform().body();

        from("direct:projectPut")
               .process(new Processor() {
                   @Override
                   public void process(Exchange exchange) throws Exception {

                   }
               })
                .transform().body();

        from("direct:projectPutId")
                .process(new PutByIdProject());

        from("direct:projectDelete")

                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });

        from("direct:projectDeleteId")
                .process(new DeleteByIdProject())
                .transform().body();
    }
}
