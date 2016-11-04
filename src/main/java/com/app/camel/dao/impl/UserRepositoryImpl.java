package com.app.camel.dao.impl;

import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.User;
import com.app.camel.model.tables.records.UserRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.USER;

public class UserRepositoryImpl extends GenericRepository implements UserRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserRecord> get(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting user failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Getting user with id: {}", id);

        Optional<UserRecord> userRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(USER)
                        .where(USER.ID.equal(id))
                        .fetchOne()));

        if (userRecord.isPresent()) {
            LOGGER.info("User with id: {} fetched successfully", id);
        } else {
            LOGGER.info("User with id: {} not found", id);
        }

        return userRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<UserRecord> getAll() {

        LOGGER.info("Getting all users from database");

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(USER).fetch();

            Collection<UserRecord> users = Lists.newArrayList();

            result.forEach(r -> {
                UserRecord user = new UserRecord(
                        r.getValue(USER.ID),
                        r.getValue(USER.FIRST_NAME),
                        r.getValue(USER.LAST_NAME),
                        r.getValue(USER.EMAIL),
                        r.getValue(USER.STATUS));

                users.add(user);
            });

            if (users.size() > 0) {
                LOGGER.info("Successfully fetched all users from database");
            } else {
                LOGGER.info("Cannot get all users. No users found in database");
            }

            return users;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(UserRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
            Preconditions.checkNotNull(entity.getId());
        } catch (NullPointerException ex) {
            LOGGER.error("Updating user record failed! UserRecord or UserRecord id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Updating user with id: {}", entity.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(USER)
                    .set(USER.FIRST_NAME, entity.getFirstName())
                    .set(USER.LAST_NAME, entity.getLastName())
                    .set(USER.EMAIL, entity.getEmail())
                    .set(USER.STATUS, entity.getStatus())
                    .where(USER.ID.eq(entity.getId()))
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully updated user with id: {}", entity.getId());
            } else {
                LOGGER.info("Cannot update user with id: {}. User not found", entity.getId());
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(UserRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding user record failed! UserRecord cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Adding user with id: {}", entity.getId());

        return executeQuery(ctx -> {
            User user = User.USER;

            int count = ctx.insertInto(user)
                    .set(user.FIRST_NAME, entity.getFirstName())
                    .set(user.LAST_NAME, entity.getLastName())
                    .set(user.EMAIL, entity.getEmail())
                    .set(user.STATUS, entity.getStatus())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully added user with id: {}", entity.getId());
            } else {
                LOGGER.info("Cannot add user with id: {}", entity.getId());
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting user failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting user with id: " + id);

        return executeQuery(ctx -> {
            int count = ctx.delete(USER).where(USER.ID.eq(id)).execute();

            if (count > 0) {
                LOGGER.info("User with id: {} deleted successfully", id);
            } else {
                LOGGER.info("User not found! Cannot delete user with id: {}", id);
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {

        LOGGER.info("Deleting all users");

        return executeQuery(ctx -> {

            int count = 0;

            count = ctx.delete(USER).execute();

            if (count > 0) {
                LOGGER.info("All users deleted successfully");
            } else {
                LOGGER.info("Cannot delete users. Database is already empty");
            }

            return count > 0;
        });
    }
}
