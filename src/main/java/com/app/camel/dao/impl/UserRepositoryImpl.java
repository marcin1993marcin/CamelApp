package com.app.camel.dao.impl;

import com.app.camel.configuration.Configuration;
import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.records.UserRecord;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class UserRepositoryImpl implements UserRepository {

    public UserRepositoryImpl() {
    }

    public String getAllUserWithProject() throws SQLException {

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record5<Integer, String, String, String, Integer>> result = dslContext
                    .select(USER.ID, USER.FIRST_NAME, USER.LAST_NAME, DSL.groupConcat(PROJECT.PROJECT_NAME, "; ").as("project_name"), DSL.count().as("number_of_projects"))
                    .from(USER)
                    .join(USER_PROJECTS)
                    .on(USER.ID.equal(USER_PROJECTS.USERS_ID))
                    .join(PROJECT)
                    .on(USER_PROJECTS.PROJECTS_ID.equal(PROJECT.ID))
                    .groupBy(USER.ID)
                    .fetch();

            return String.valueOf(result);
        }
    }


    @Override
    public Optional<UserRecord> get(Integer id) throws SQLException {

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Optional<UserRecord> userRecords = Optional.ofNullable(dslContext.selectFrom(USER).where(USER.ID.equal(id)).fetchOne());

            return userRecords;
        }
    }

    @Override
    public Collection<UserRecord> getAll() throws SQLException {

        Collection<UserRecord> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(USER).fetch();

            for (Record r : result) {
                UserRecord user = new UserRecord(r.getValue(USER.ID), r.getValue(USER.FIRST_NAME), r.getValue(USER.LAST_NAME), r.getValue(USER.EMAIL), r.getValue(USER.STATUS));

                users.add(user);
            }

            return users;
        }
    }

    @Override
    public boolean update(UserRecord entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.update(USER)
                    .set(USER.FIRST_NAME, entity.getFirstName())
                    .set(USER.LAST_NAME, entity.getLastName())
                    .set(USER.EMAIL, entity.getEmail())
                    .set(USER.STATUS, entity.getStatus())
                    .where(USER.ID.eq(entity.getId()))
                    .execute();

            return true;
        }
    }

    @Override
    public boolean insert(UserRecord entity) throws SQLException {
        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            com.app.camel.model.tables.User user = com.app.camel.model.tables.User.USER;

            dslContext.insertInto(user)
                    .set(user.FIRST_NAME, entity.getFirstName())
                    .set(user.LAST_NAME, entity.getLastName())
                    .set(user.EMAIL, entity.getEmail())
                    .set(user.STATUS, entity.getStatus())
                    .execute();

            return true;
        }
    }

    @Override
    public boolean delete(Integer id) throws SQLException {

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.delete(USER).where(USER.ID.eq(id)).execute();

            return true;
        }
    }

    @Override
    public boolean deleteAll() throws SQLException {

        try (Connection connection = DriverManager.getConnection(Configuration.DATABASE_URL, Configuration.DATABASE_USER, Configuration.DATABASE_PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(USER).fetch();

            for (Record r : result) {

                dslContext.delete(USER).where(USER.ID.eq(r.getValue(USER.ID))).execute();
            }

            return true;
        }
    }
}
