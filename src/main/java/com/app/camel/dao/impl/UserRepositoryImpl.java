package com.app.camel.dao.impl;

import com.app.camel.configuration.Config;
import com.app.camel.dto.User;
import com.app.camel.dto.UserStatus;
import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.records.UserRecord;
import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static com.app.camel.model.Tables.PROJECT;
import static com.app.camel.model.Tables.USER;
import static com.app.camel.model.Tables.USER_HAS_PROJECT;

public class UserRepositoryImpl implements UserRepository {

    public UserRepositoryImpl() {
    }

    public String getAllUserWithProject() {

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record5<Integer, String, String, String, Integer>> result = dslContext
                    .select(USER.ID, USER.FIRST_NAME, USER.LAST_NAME, DSL.groupConcat(PROJECT.PROJECT_NAME, "; ").as("project_name"), DSL.count().as("number_of_projects"))
                    .from(USER)
                    .join(USER_HAS_PROJECT)
                    .on(USER.ID.equal(USER_HAS_PROJECT.USERS_ID))
                    .join(PROJECT)
                    .on(USER_HAS_PROJECT.PROJECTS_ID.equal(PROJECT.ID))
                    .groupBy(USER.ID)
                    .fetch();

            return String.valueOf(result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            Result<Record> result = dslContext.select().from(USER).fetch();

            for (Record r : result) {
                User user = new User(r.getValue(USER.ID), r.getValue(USER.FIRST_NAME), r.getValue(USER.LAST_NAME), r.getValue(USER.EMAIL), r.getValue(USER.IS_ACTIVE));

                users.add(user);
            }

            return new Gson().toJson(users);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getUserById(Integer id){

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            UserRecord userRecords = dslContext.selectFrom(USER).where(USER.ID.equal(id)).fetchOne();

            User user = new User(
                    userRecords.getValue(USER.ID),
                    userRecords.getValue(USER.FIRST_NAME),
                    userRecords.getValue(USER.LAST_NAME),
                    userRecords.getValue(USER.EMAIL),
                    userRecords.getValue(USER.IS_ACTIVE));

            return new Gson().toJson(user);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addUser(String firstName, String lastName, String email, UserStatus isActive) {


        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            com.app.camel.model.tables.User user = com.app.camel.model.tables.User.USER;

            dslContext.insertInto(user)
                    .set(user.FIRST_NAME, firstName)
                    .set(user.LAST_NAME, lastName)
                    .set(user.EMAIL, email)
                    .set(user.IS_ACTIVE, isActive.getStatus())
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Integer id) {

        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.delete(USER).where(USER.ID.eq(id)).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserWithId(Integer id, String firstName, String lastName, String email, UserStatus isActive) {


        try (Connection connection = DriverManager.getConnection(Config.URL, Config.USER, Config.PASSWORD)) {

            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);

            dslContext.update(USER)
                    .set(USER.FIRST_NAME, firstName)
                    .set(USER.LAST_NAME, lastName)
                    .set(USER.EMAIL, email)
                    .set(USER.IS_ACTIVE, isActive.getStatus())
                    .where(USER.ID.eq(id))
                    .execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
