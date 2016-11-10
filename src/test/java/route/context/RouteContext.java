package route.context;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static configuration.Configuration.*;
import static configuration.Configuration.FLYWAY_SCHEMA_NAME;

public abstract class RouteContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteContext.class);
    protected final CamelContext context = new DefaultCamelContext();

    public abstract void run() throws Exception;

    public void stop() throws Exception {
        context.stop();
    }

    protected static void initializeDatabase() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(FLYWAY_URL, DATABASE_USER, DATABASE_PASSWORD);
        flyway.setSchemas(FLYWAY_SCHEMA_NAME);
        int migrate = flyway.migrate();

        if (migrate > 0) {
            LOGGER.error("Schema named '{}' or database tables not found", FLYWAY_SCHEMA_NAME);
            LOGGER.info("Automaticly generated '{}' schema and database tables", FLYWAY_SCHEMA_NAME);
        } else {
            LOGGER.info("Schema '{}' is up to date. No migration necessary.", FLYWAY_SCHEMA_NAME);
        }
    }
}
