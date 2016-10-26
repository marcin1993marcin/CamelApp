package com.app.camel.dao.impl;

import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.records.UserRecord;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class UserRepositoryImpl extends GenericRepository implements UserRepository {

    final static Logger logger = Logger.getLogger(UserRepositoryImpl.class);

    public UserRepositoryImpl() {
    }

    public String getAllUserWithProject() {

        DSLContext dslContext = getDSLContext();

        Result<Record5<Integer, String, String, String, Integer>> result = dslContext
                .select(USER.ID,
                        USER.FIRST_NAME,
                        USER.LAST_NAME,
                        DSL.groupConcat(PROJECT.PROJECT_NAME, "; ").as("project_name"),
                        DSL.count().as("number_of_projects"))
                .from(USER)
                .join(USER_PROJECTS)
                .on(USER.ID.equal(USER_PROJECTS.USERS_ID))
                .join(PROJECT)
                .on(USER_PROJECTS.PROJECTS_ID.equal(PROJECT.ID))
                .groupBy(USER.ID)
                .fetch();

        return String.valueOf(result);
    }


    @Override
    public Optional<UserRecord> get(Integer id) {

        DSLContext dslContext = getDSLContext();

        Optional<UserRecord> userRecords = Optional.empty();

        try {
            userRecords = Optional.ofNullable(
                    dslContext.selectFrom(USER)
                            .where(USER.ID.equal(id))
                            .fetchOne());

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to access data");

        } finally {
            dslContext.close();
        }

        return userRecords;
    }

    @Override
    public Collection<UserRecord> getAll() {

        Collection<UserRecord> users = Lists.newArrayList();

        DSLContext dslContext = getDSLContext();

        try {
            Result<Record> result = dslContext.select().from(USER).fetch();

            for (Record r : result) {
                UserRecord user = new UserRecord(
                        r.getValue(USER.ID),
                        r.getValue(USER.FIRST_NAME),
                        r.getValue(USER.LAST_NAME),
                        r.getValue(USER.EMAIL),
                        r.getValue(USER.STATUS));

                users.add(user);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to access data");
        } finally {
            dslContext.close();
        }

        return users;
    }

    @Override
    public boolean update(UserRecord entity) {

        DSLContext dslContext = getDSLContext();

        try {
            dslContext.update(USER)
                    .set(USER.FIRST_NAME, entity.getFirstName())
                    .set(USER.LAST_NAME, entity.getLastName())
                    .set(USER.EMAIL, entity.getEmail())
                    .set(USER.STATUS, entity.getStatus())
                    .where(USER.ID.eq(entity.getId()))
                    .execute();

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to update data");
        } finally {
            dslContext.close();
        }

        return false;

    }

    @Override
    public boolean insert(UserRecord entity) {

        DSLContext dslContext = getDSLContext();

        com.app.camel.model.tables.User user = com.app.camel.model.tables.User.USER;

        try {
            dslContext.insertInto(user)
                    .set(user.FIRST_NAME, entity.getFirstName())
                    .set(user.LAST_NAME, entity.getLastName())
                    .set(user.EMAIL, entity.getEmail())
                    .set(user.STATUS, entity.getStatus())
                    .execute();

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to insert data");
        } finally {
            dslContext.close();
        }

        return false;
    }

    @Override
    public boolean delete(Integer id) {

        DSLContext dslContext = getDSLContext();

        try {
            dslContext.delete(USER).where(USER.ID.eq(id)).execute();

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to delete data");
        } finally {
            dslContext.close();
        }

        return false;
    }

    @Override
    public boolean deleteAll() {

        DSLContext dslContext = getDSLContext();

        try {
            Result<Record> result = dslContext.select().from(USER).fetch();

            for (Record r : result) {

                dslContext.delete(USER).where(USER.ID.eq(r.getValue(USER.ID))).execute();
            }

            return true;

        } catch (DataAccessException e) {
            e.printStackTrace();
            logger.error("Unable to delete data");
        } finally {
            dslContext.close();
        }

        return false;
    }
}
