package com.app.camel.dao.impl;

import com.app.camel.dao.CustomerRepository;
import com.app.camel.model.tables.Customer;
import com.app.camel.model.tables.records.CustomerRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.*;

public class CustomerRepositoryImpl extends GenericRepository implements CustomerRepository{

    private final static Logger logger = LoggerFactory.getLogger(CustomerRepositoryImpl.class);


    @Override
    public Optional<CustomerRecord> get(Integer id){

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            logger.error("Getting customer failed. Id cannot be null");
            ex.printStackTrace();
        }

        logger.info("Getting customer with id: " + id);

        Optional<CustomerRecord> customerRecord = executeQuery(ctx -> Optional.ofNullable(
            ctx.selectFrom(CUSTOMER)
                    .where(CUSTOMER.ID.equals(id))
                    .fetchOne()
        ));

        if(customerRecord.isPresent()){
            logger.info("Customer with id " + id + "fetched successfully");
        } else {
            logger.info("Customer with id " + id + "not found");
        }

        return customerRecord;
    }

    @Override
    public Collection<CustomerRecord> getAll() {

        logger.info("Getting all customers from database");

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(CUSTOMER).fetch();

            Collection<CustomerRecord> customers = Lists.newArrayList();
            result.forEach(record -> {
                CustomerRecord customer = new CustomerRecord(
                        record.getValue(CUSTOMER.ID),
                        record.getValue(CUSTOMER.FIRST_NAME),
                        record.getValue(CUSTOMER.LAST_NAME),
                        record.getValue(CUSTOMER.EMAIL),
                        record.getValue(CUSTOMER.STATUS));
                customers.add(customer);
            });

            if(customers.size() > 0){
                logger.info("Success fetching all customers from database");
            } else {
                logger.info("Cannot get all users. No users found in database");
            }

            return customers;
        });
    }

    @Override
    public boolean update(CustomerRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
            Preconditions.checkNotNull(entity.getId());
        } catch (NullPointerException ex) {
            logger.error("Updating customer record failed. CustomerRecord or CustomerRecordId is null");
            ex.printStackTrace();
        }

        logger.info("Updating customer with id: " + entity.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(CUSTOMER)
                    .set(CUSTOMER.FIRST_NAME, entity.getFirstName())
                    .set(CUSTOMER.LAST_NAME, entity.getLastName())
                    .set(CUSTOMER.EMAIL, entity.getEmail())
                    .set(CUSTOMER.STATUS, entity.getStatus())
                    .where(CUSTOMER.ID.eq(entity.getId()))
                    .execute();

            if(count > 0){
                logger.info("Success updating customer with id: " + entity.getId());
            } else {
                logger.info("Cannot update customer with id: " + entity.getId());
            }

            return count > 0;
        });
    }

    @Override
    public boolean insert(CustomerRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
        } catch (NullPointerException ex) {
            logger.error("Adding customer record failed. CustomerRecord cannot be null");
            ex.printStackTrace();
        }

        logger.debug("Inserting new customer with id: " + entity.getId());

        return executeQuery(ctx -> {
            Customer customer = Customer.CUSTOMER;

            int count = ctx.insertInto(customer)
                    .set(CUSTOMER.FIRST_NAME, entity.getFirstName())
                    .set(CUSTOMER.LAST_NAME, entity.getLastName())
                    .set(CUSTOMER.EMAIL, entity.getEmail())
                    .set(CUSTOMER.STATUS, entity.getStatus())
                    .execute();

            if(count > 0) {
                logger.info("Success adding customer with id: " + entity.getId());
            } else {
                logger.info("Cannot add customer with id: " + entity.getId());
            }

            return count > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            logger.error("Deleting customer failed. Id cannot be null");
            ex.printStackTrace();
        }


        return executeQuery(ctx -> {
            int count = ctx.delete(CUSTOMER)
                    .where(CUSTOMER.ID.eq(id))
                    .execute();

            if (count > 0){
                logger.info("Customer with id: " + id + " deleted successfully");
            } else {
                logger.info("Customer not found. Cannot delete user with id: " + id);
            }

            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() {

        logger.info("Deleting all customers");

        return executeQuery(ctx -> {

            int count = 0;
            count = ctx.delete(CUSTOMER).execute();

            if (count > 0) {
                logger.info("All customers deleted successfully");
            } else {
                logger.info("Cannot delete all Customers. DB is already empty");
            }

            return count > 0;
        });
    }

    @Override
    public String getAllCustomerWithProject() throws SQLException {
        return null;
    }
}
