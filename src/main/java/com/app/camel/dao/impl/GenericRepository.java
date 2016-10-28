package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class GenericRepository {

    Logger LOGGER = Logger.getLogger(GenericRepository.class);

    protected <T> T executeQuery(Function<DSLContext, T> consumer) {
        try (Connection connection = DriverManager.getConnection(
                Configuration.DATABASE_URL,
                Configuration.DATABASE_USER,
                Configuration.DATABASE_PASSWORD);

             DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL)) {

            return consumer.apply(dslContext);
        } catch (SQLException sqle) {

            LOGGER.error("error getting data for", sqle);

            throw new DataAccessException("error getting data for", sqle);
        }
    }
}
