package com.app.camel;

import com.app.camel.configuration.Configuration;
import com.app.camel.routes.ProjectRoute;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.flywaydb.core.Flyway;


// review nie można uruchomić aplikacji - brak pliku manifest.mf
public class Application {
    public static void main(String[] args) throws Exception {
        // review może tutaj też dodać do logów informacje o uruchomieniu się aplikacji?
        Class.forName(Configuration.DATABASE_DRIVER).newInstance();

        initializeDatabase();
        CamelContext context = initializeCamel();

        // review może po uruchomieniu aplikacji dać do logów informcją o ilości uruchomionych routów? i wyświetlić to
        // co może się jakoś przydać podczas czytania logów
//        context.getRouteDefinitions().forEach(route -> {
//            System.out.println("Route: ");
//            System.out.println(" INPUTS");
//            route.getInputs().forEach(inputs -> {
//                System.out.println("  " + inputs.getUri());
//            });
//        });

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
        // review - tutaj przydaloby sie poinformowc co sie dzieje
        // 1. czy baza bya pusta
        // 2. co sie utowarzyło a co alterowało w bazie
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(Configuration.FLYWAY_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD);
        flyway.setSchemas(Configuration.FLYWAY_SCHEMA_NAME);
        flyway.migrate();
    }
}
