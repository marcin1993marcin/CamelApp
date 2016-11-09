package com.app.camel.dao.impl;

import com.app.camel.dao.SalaryRepository;
import com.app.camel.model.tables.Salary;
import com.app.camel.model.tables.records.SalaryRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.SALARY;


public class SalaryRepositoryImpl extends GenericRepository implements SalaryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalaryRepositoryImpl.class);

    @Override
    public Optional<SalaryRecord> get(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting salary failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Getting salary with id: " + id);

        Optional<SalaryRecord> salaryRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(SALARY)
                        .where(SALARY.ID.equal(id))
                        .fetchOne()));

        if (salaryRecord.isPresent()) {
            LOGGER.info("Salary with id: " + id + " fetched successfully");
        } else {
            LOGGER.info("Salary with id: " + id + " not found");
        }

        return salaryRecord;
    }

    @Override
    public Collection<SalaryRecord> getAll() {

        LOGGER.info("Getting all salaries from database");

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(SALARY).fetch();

            Collection<SalaryRecord> salaries = Lists.newArrayList();

            result.forEach(r -> {
                SalaryRecord salary = new SalaryRecord(
                        r.getValue(SALARY.ID),
                        r.getValue(SALARY.USERS_ID),
                        r.getValue(SALARY.POSITIONS_ID),
                        r.getValue(SALARY.MONTHLY),
                        r.getValue(SALARY.PER_HOUR),
                        r.getValue(SALARY.DATE_FROM),
                        r.getValue(SALARY.DATE_TO));
            });

            if (salaries.size() > 0) {
                LOGGER.info("Successfully fetched all salaries from database");
            } else {
                LOGGER.info("Cannot get all salaries. No salaries found in database");
            }

            return salaries;
        });
    }

    @Override
    public boolean update(SalaryRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
            Preconditions.checkNotNull(entity.getId());
        } catch (NullPointerException ex) {
            LOGGER.error("Updating salary record failed! SalaryRecord or SalaryRecord id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Updating salary with id: " + entity.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(SALARY)
                    .set(SALARY.MONTHLY, entity.getMonthly())
                    .set(SALARY.PER_HOUR, entity.getPerHour())
                    .set(SALARY.DATE_FROM, entity.getDateFrom())
                    .set(SALARY.DATE_TO, entity.getDateTo())
                    .where((SALARY.ID.eq(entity.getId())))
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully updated salary with id: " + entity.getId());
            } else {
                LOGGER.info("Cannot update salary with id: " + entity.getId() + ". Salary not found");
            }

            return count > 0;
        });
    }

    @Override
    public boolean insert(SalaryRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding salary record failed! SalaryRecord cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Adding salary with id: " + entity.getId());

        return executeQuery(ctx -> {
            Salary salary = Salary.SALARY;

            int count = ctx.insertInto(salary)
                    .set(salary.MONTHLY, entity.getMonthly())
                    .set(salary.PER_HOUR, entity.getPerHour())
                    .set(salary.DATE_FROM, entity.getDateFrom())
                    .set(salary.DATE_TO, entity.getDateTo())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully added salary with id: " + entity.getId());
            } else {
                LOGGER.info("Cannot add salary with id: " + entity.getId());
            }

            return count > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting salary failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting salary with id: " + id);

        return executeQuery(ctx -> {
            int count = ctx.delete(SALARY).where(SALARY.ID.eq(id)).execute();

            if (count > 0) {
                LOGGER.info("Salary with id: " + id + " deleted successfully");
            } else {
                LOGGER.info("Salary not found! Cannot delete salary with id: " + id);
            }


            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() {

        LOGGER.info("Deleting all salaries");

        return executeQuery(ctx -> {

            int count = 0;

            count = ctx.delete(SALARY).execute();

            if (count > 0) {
                LOGGER.info("All salaries deleted successfully");
            } else {
                LOGGER.info("Cannot delete salaries. Database is already empty");
            }

            return count > 0;
        });
    }
}
