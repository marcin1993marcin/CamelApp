package com.app.camel;

import com.app.camel.configuration.DatabaseConfiguration;
import com.app.camel.routes.ProjectRoute;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.flywaydb.core.Flyway;

public class Application {
    public static void main(String[] args) throws Exception {

        Class.forName(DatabaseConfiguration.DRIVER).newInstance();
        CamelContext context = new DefaultCamelContext();

        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(DatabaseConfiguration.URL_FOR_FLYWAY_CONFIGURATION, DatabaseConfiguration.USER, DatabaseConfiguration.PASSWORD);
        flyway.setSchemas("library");
        flyway.migrate();

        UserRoute restfulRoute = new UserRoute();
        ProjectRoute projectRoute= new ProjectRoute();

        context.addRoutes(restfulRoute);
        context.addRoutes(projectRoute);

        context.start();
        Thread.sleep(5000000);
        context.stop();
    }
}
