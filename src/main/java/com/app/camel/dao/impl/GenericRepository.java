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

    private static final Logger LOGGER = Logger.getLogger(GenericRepository.class);

    /**
     * This method open and close Connection to database
     * using URL, USER and PASSWORD specified in configuration.properties
     * and initialize DSLContext with MYSQL Dialect.
     * Method allows you to execute database query
     *
     * @param consumer type java.util.function which take DSLContext as input
     *                 and specify return type
     * @return result of query
     */
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
