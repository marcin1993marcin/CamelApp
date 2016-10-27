package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class GenericRepository {

    protected <T> T executeQuery(Function<DSLContext, T> consumer) throws SQLException {
        try (Connection connection = DriverManager.getConnection(
                Configuration.DATABASE_URL,
                Configuration.DATABASE_USER,
                Configuration.DATABASE_PASSWORD);

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL)) {

            return consumer.apply(dslContext);
        }
    }
}
