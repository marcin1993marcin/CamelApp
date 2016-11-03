package com.app.camel;

import com.app.camel.routes.PositionRoute;
import com.app.camel.routes.ProjectRoute;
import com.app.camel.routes.SalaryRoute;
import com.app.camel.routes.SkillRoute;
import com.app.camel.routes.UserRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.app.camel.configuration.Configuration.*;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting application");

        Class.forName(DATABASE_DRIVER).newInstance();

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
        PositionRoute positionRoute = new PositionRoute();
        SalaryRoute salaryRoute = new SalaryRoute();
        SkillRoute skillRoute = new SkillRoute();

        context.addRoutes(userRoute);
        context.addRoutes(projectRoute);
        context.addRoutes(positionRoute);
        context.addRoutes(salaryRoute);
        context.addRoutes(skillRoute);
        return context;
    }

    private static void initializeDatabase() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(FLYWAY_URL, DATABASE_USER, DATABASE_PASSWORD);
        flyway.setSchemas(FLYWAY_SCHEMA_NAME);
        int migrate = flyway.migrate();

        if (migrate > 0) {
            LOGGER.error("Schema named '{}' or database tables not found", FLYWAY_SCHEMA_NAME);
            LOGGER.info("Automatically generated '{}' schema and database tables", FLYWAY_SCHEMA_NAME);
        } else {
            LOGGER.info("Schema '{}' is up to date. No migration necessary.", FLYWAY_SCHEMA_NAME);
        }
    }
}
