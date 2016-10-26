package com.app.camel;

import com.app.camel.configuration.Configuration;
import com.app.camel.routes.ProjectRoute;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.flywaydb.core.Flyway;

public class Application {
    public static void main(String[] args) throws Exception {

        Class.forName(Configuration.DATABASE_DRIVER).newInstance();

        initializeDatabase();
        CamelContext context = initializeCamel();

        context.start();
        Thread.sleep(5000000);
        context.stop();
    }

    private static CamelContext initializeCamel() throws Exception {
        CamelContext context = new DefaultCamelContext();
        UserRoute userRoute = new UserRoute();
        ProjectRoute projectRoute = new ProjectRoute();

        context.addRoutes(userRoute);
        context.addRoutes(projectRoute);
        return context;
    }

    private static void initializeDatabase() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(Configuration.FLYWAY_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD);
        flyway.setSchemas(Configuration.FLYWAY_SCHEMA_NAME);
        flyway.migrate();
    }
}
