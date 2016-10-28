package com.app.camel.dao.impl;

import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.records.UserRecord;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class UserRepositoryImpl extends GenericRepository implements UserRepository {

    public UserRepositoryImpl() {
    }

    public String getAllUserWithProject() {

        return executeQuery(ctx -> {
            Result<Record5<Integer, String, String, String, Integer>> result = ctx
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
        });
    }


    @Override
    public Optional<UserRecord> get(Integer id) {
        return executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(USER)
                        .where(USER.ID.equal(id))
                        .fetchOne()));
    }

    @Override
    public Collection<UserRecord> getAll() {

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(USER).fetch();

            Collection<UserRecord> users = Lists.newArrayList();
            for (Record r : result) {
                UserRecord user = new UserRecord(
                        r.getValue(USER.ID),
                        r.getValue(USER.FIRST_NAME),
                        r.getValue(USER.LAST_NAME),
                        r.getValue(USER.EMAIL),
                        r.getValue(USER.STATUS));

                users.add(user);
            }

            return users;
        });
    }

    @Override
    public boolean update(UserRecord entity) {

        return executeQuery(ctx -> {
            int count = ctx.update(USER)
                    .set(USER.FIRST_NAME, entity.getFirstName())
                    .set(USER.LAST_NAME, entity.getLastName())
                    .set(USER.EMAIL, entity.getEmail())
                    .set(USER.STATUS, entity.getStatus())
                    .where(USER.ID.eq(entity.getId()))
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean insert(UserRecord entity) {

        return executeQuery(ctx -> {
            com.app.camel.model.tables.User user = com.app.camel.model.tables.User.USER;

            int count = ctx.insertInto(user)
                    .set(user.FIRST_NAME, entity.getFirstName())
                    .set(user.LAST_NAME, entity.getLastName())
                    .set(user.EMAIL, entity.getEmail())
                    .set(user.STATUS, entity.getStatus())
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {

        return executeQuery(ctx -> {
            int count = ctx.delete(USER).where(USER.ID.eq(id)).execute();

            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() {

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(USER).fetch();

            int count = 0;
            for (Record r : result) {

                count = ctx.delete(USER).where(USER.ID.eq(r.getValue(USER.ID))).execute();
            }

            return count > 0;
        });
    }
}
