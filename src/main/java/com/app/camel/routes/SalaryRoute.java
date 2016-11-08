package com.app.camel.routes;

import com.app.camel.processor.salary.*;
import org.apache.camel.builder.RouteBuilder;

import static com.app.camel.restconfiguration.RestConfiguration.*;
import static com.app.camel.restconfiguration.RestConfiguration.METHOD_DELETE;
import static com.app.camel.restconfiguration.RestConfiguration.PARAM_ID;

public class SalaryRoute extends RouteBuilder {

    private static final String SALARY = "salary";
    private static final String SALARY_REST_URL = REST_URL + SALARY;

    @Override
    public void configure() throws Exception {

        ExceptionRoutes.buildExceptionRoute(this);

        from(SALARY_REST_URL + METHOD_GET)
                .process(new SelectAllSalary())
                .transform().body();

        from(SALARY_REST_URL + PARAM_ID + METHOD_GET)
                .process(new SelectSalary())
                .transform().body();

        from(SALARY_REST_URL + METHOD_POST)
                .process(new PostSalary())
                .transform().body();

        from(SALARY_REST_URL + METHOD_PUT)
                .process(new PutAllSalary())
                .transform().body();

        from(SALARY_REST_URL + PARAM_ID + METHOD_PUT)
                .process(new PutSalary())
                .transform().body();

        from(SALARY_REST_URL + METHOD_DELETE)
                .process(new DeleteAllSalary())
                .transform().body();

        from(SALARY_REST_URL + PARAM_ID + METHOD_DELETE)
                .process(new DeleteSalary())
                .transform().body();
    }
}
