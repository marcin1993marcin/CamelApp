package com.app.camel.dao.impl;

import com.app.camel.dao.UserRepository;
import com.app.camel.model.tables.records.UserRecord;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class UserRepositoryImpl extends GenericRepository implements UserRepository {



    private final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    // review - po co ten konstruktor
    public UserRepositoryImpl() {
    }

    // review - po co ten konstruktor?
//    public UserRepositoryImpl() {
//    }

    // review - bledna nazwa metody - plural
    // review - dlaczego metoda zwraca string?
    // review - brak javadoc
    // review dlaczego nie ma @Override

    // review interfejs informuje, że jest wyrzucany SQLException - ale w implementacji tej metody nie widzę teog. Dodatkowo w nagłówku metody nie widzę throws SQLException
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

//            result.forEach(record -> {
//                UserRecord user = new UserRecord(
//                        record.getValue(USER.ID),
//                        record.getValue(USER.FIRST_NAME),
//                        record.getValue(USER.LAST_NAME),
//                        record.getValue(USER.EMAIL),
//                        record.getValue(USER.STATUS));
//
//                users.add(user);
//            });

            // review - zamiast poniższego zastosowalym to co zakomentowałem wyzej. zapis ponizszy nie jest bledny, ale bardziej... fancy :-)
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

                    // review - a co sie stanie jezeli Id bedzie null?
                    .where(USER.ID.eq(entity.getId()))
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean insert(UserRecord entity) {

        LOGGER.debug("Inserting user (user={})", entity);

        return executeQuery(ctx -> {
            // review - dlaczego brak importu???
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

        // review - jaki wyjatek wyskoczy, jeżeli id będzie null?
        // czy nie powinniśmy dodąć to co zakomentowane?
        // review - a jeżeli dodamy to czy nie powinnismy coś w związku z tym zrobić dodatkowo ;-)
        // Preconditions.checkNotNull(id);

        return executeQuery(ctx -> {
            int count = ctx.delete(USER)
                    .where(USER.ID.eq(id))
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() {

        return executeQuery(ctx -> {
            // review - po co pobieramy?
            Result<Record> result = ctx.select().from(USER).fetch();

            int count = 0;

            // ctx.delete(USER).execute();
            // review - po co ta petla??? czy nie lepiej załatwić to jedną liniż, zakomentowaną powyżej?
            for (Record r : result) {
                // review - wykonujemy w petli N zapytan usuniecia rekordow
                // review - a co jeżeli któryś się wywali? też zwrócimy  true?
                count = ctx.delete(USER).where(USER.ID.eq(r.getValue(USER.ID))).execute();
            }

            return count > 0;

        });
    }
}
