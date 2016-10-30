package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

// review chyba wystarczy package-private czyli bez public
public class GenericRepository {


/**
 *  If a class A can access a logger of a class B (by inheritance, public visibility or getter)
 *  class A can of course put it’s log events to this logger. As a result, the log events will
 *  show up in the log-files as related to class B. That decreases the readability
 *  and maintainability of your logs. Furthermore the calling class can potentially
 *  manipulate the logger and—as worst case scenario—set it’s log level to off.
 */

    // review - DO NOT EXPOSE your logger! To nobody! Even in subclasses ^^^^ patrz wyżej
    final Logger LOGGER = LoggerFactory.getLogger(GenericRepository.class);

    // review - brak javadoc
    // review - chyba protected jest zbędne
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
