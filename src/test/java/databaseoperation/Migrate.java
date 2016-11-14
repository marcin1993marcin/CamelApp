package databaseoperation;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static configuration.Configuration.*;
import static configuration.Configuration.FLYWAY_SCHEMA_NAME;

public class Migrate {

    private static final Logger LOGGER= LoggerFactory.getLogger(Migrate.class);

    public void migrateDatabase()
    {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(FLYWAY_URL, DATABASE_USER, DATABASE_PASSWORD);
        flyway.setSchemas(FLYWAY_SCHEMA_NAME);
        flyway.clean();
        int migrate = flyway.migrate();


        if (migrate > 0) {
            LOGGER.error("Schema named '{}' or database tables not found", FLYWAY_SCHEMA_NAME);
            LOGGER.info("Automaticly generated '{}' schema and database tables", FLYWAY_SCHEMA_NAME);
        } else {
            LOGGER.info("Schema '{}' is up to date. No migration necessary.", FLYWAY_SCHEMA_NAME);
        }
    }
}
