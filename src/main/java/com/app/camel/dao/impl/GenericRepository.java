package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericRepository {

    final static Logger logger = Logger.getLogger(GenericRepository.class);

    protected DSLContext getDSLContext() {
        try (Connection connection = DriverManager.getConnection(
                Configuration.DATABASE_URL,
                Configuration.DATABASE_USER,
                Configuration.DATABASE_PASSWORD);

             DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL)) {

            return dslContext;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            logger.error("Error establishing a database connection");
        }

        return null;
    }
}
