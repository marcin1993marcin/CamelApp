package com.app.camel.routes;

import com.app.camel.processor.project.*;
import com.app.camel.restconfiguration.RestConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;


public class ProjectRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from(RestConfiguration.REST_URL + "project" + RestConfiguration.METHOD_GET).to("direct:projectSelect");
        from(RestConfiguration.REST_URL + "project/{id}" + RestConfiguration.METHOD_GET).to("direct:projectSelectId");
        from(RestConfiguration.REST_URL + "project" + RestConfiguration.METHOD_POST).to("direct:projectPost");
        from(RestConfiguration.REST_URL + "project "+ RestConfiguration.METHOD_PUT).to("direct:projectPut");
        from(RestConfiguration.REST_URL + "project/{id}" + RestConfiguration.METHOD_PUT).to("direct:projectPutId");
        from(RestConfiguration.REST_URL + "project" + RestConfiguration.METHOD_DELETE).to("direct:projectDelete");
        from(RestConfiguration.REST_URL + "project/{id}?" + RestConfiguration.METHOD_DELETE).to("direct:projectDeleteId");

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
