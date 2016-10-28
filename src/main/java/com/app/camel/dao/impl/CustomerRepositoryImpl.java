package com.app.camel.dao.impl;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.model.tables.records.CustomerRecord;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class CustomerRepositoryImpl extends GenericRepository implements CustomerRepository{

    public CustomerRepositoryImpl() {
    }

    public String getAllCustomerWithProject() throws SQLException {

        return executeQuery(ctx -> {
            Result<Record5<Integer, String, String, String, Integer>> result = ctx
                    .select(CUSTOMER.ID,
                            CUSTOMER.FIRST_NAME,
                            CUSTOMER.LAST_NAME,
                            DSL.groupConcat(PROJECT.PROJECT_NAME, "; ").as("project_name"),
                            DSL.count().as("number_of_projects"))
                    .from(CUSTOMER)
                    .join(CUSTOMER_PROJECTS)
                    .on(CUSTOMER.ID.equal(CUSTOMER_PROJECTS.CUSTOMER_ID))
                    .join(PROJECT)
                    .on(USER_PROJECTS.PROJECTS_ID.equal(PROJECT.ID))
                    .groupBy(CUSTOMER.ID)
                    .fetch();

            return String.valueOf(result);
        });
    }


    @Override
    public Optional<CustomerRecord> get(Integer id) throws SQLException {
        return executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(CUSTOMER)
                        .where(CUSTOMER.ID.equal(id))
                        .fetchOne()));
    }

    @Override
    public Collection<CustomerRecord> getAll() throws SQLException {

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(USER).fetch();

            Collection<CustomerRecord> users = Lists.newArrayList();
            for (Record r : result) {
                CustomerRecord user = new CustomerRecord(
                        r.getValue(CUSTOMER.ID),
                        r.getValue(CUSTOMER.FIRST_NAME),
                        r.getValue(CUSTOMER.LAST_NAME),
                        r.getValue(CUSTOMER.EMAIL),
                        r.getValue(CUSTOMER.STATUS));

                users.add(user);
            }

            return users;
        });
    }

    @Override
    public boolean update(CustomerRecord entity) throws SQLException {

        return executeQuery(ctx -> {
            int count = ctx.update(CUSTOMER)
                    .set(CUSTOMER.FIRST_NAME, entity.getFirstName())
                    .set(CUSTOMER.LAST_NAME, entity.getLastName())
                    .set(CUSTOMER.EMAIL, entity.getEmail())
                    .set(CUSTOMER.STATUS, entity.getStatus())
                    .where(CUSTOMER.ID.eq(entity.getId()))
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean insert(CustomerRecord entity) throws SQLException {

        return executeQuery(ctx -> {
            com.app.camel.model.tables.User user = com.app.camel.model.tables.User.USER;

            int count = ctx.insertInto(user)
                    .set(CUSTOMER.FIRST_NAME, entity.getFirstName())
                    .set(CUSTOMER.LAST_NAME, entity.getLastName())
                    .set(CUSTOMER.EMAIL, entity.getEmail())
                    .set(CUSTOMER.STATUS, entity.getStatus())
                    .execute();

            return count > 0;
        });
    }

    @Override
    public boolean delete(Integer id) throws SQLException {

        return executeQuery(ctx -> {
            int count = ctx.delete(CUSTOMER).where(CUSTOMER.ID.eq(id)).execute();

            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() throws SQLException {

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(CUSTOMER).fetch();

            int count = 0;
            for (Record r : result) {

                count = ctx.delete(CUSTOMER).where(CUSTOMER.ID.eq(r.getValue(CUSTOMER.ID))).execute();
            }

            return count > 0;
        });
    }

}
