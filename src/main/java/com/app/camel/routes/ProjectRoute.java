package com.app.camel.routes;

import com.app.camel.processor.DataAccessExceptionProcessor;
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

        from(PROJECT_REST_URL + METHOD_GET).to("direct:projectSelect");
        from(PROJECT_REST_URL + PARAM_ID + METHOD_GET).to("direct:projectSelectId");
        from(PROJECT_REST_URL + METHOD_POST).to("direct:projectPost");
        from(PROJECT_REST_URL + METHOD_PUT).to("direct:projectPut");
        from(PROJECT_REST_URL + PARAM_ID + METHOD_PUT).to("direct:projectPutId");
        from(PROJECT_REST_URL + METHOD_DELETE).to("direct:projectDelete");
        from(PROJECT_REST_URL + PARAM_ID + METHOD_DELETE).to("direct:projectDeleteId");

        from("direct:projectSelect").process(new SelectAllProject())
                .transform().body();
        from("direct:projectSelectId")
                .process(new SelectByIdProject())
                .transform().body();
        from("direct:projectPost")
                .process(new PostProject())
                .transform().body();

        from("direct:projectPut")
                .process(new PutAllProject());

        from("direct:projectPutId")
                .process(new PutByIdProject());

        from("direct:projectDelete")
                .process(new DeleteAllProject());

        from("direct:projectDeleteId")
                .process(new DeleteByIdProject())
                .transform().body();



    }


}
