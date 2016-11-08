package com.app.camel.dao.impl;

import com.app.camel.dao.PositionRepository;
import com.app.camel.model.tables.Position;
import com.app.camel.model.tables.records.PositionRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.POSITION;

public class PositionRepositoryImpl extends GenericRepository implements PositionRepository {

    private static final Logger LOGGER = Logger.getLogger(PositionRepositoryImpl.class);

    @Override
    public Optional<PositionRecord> get(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting position failed! Id cannot be null");
        }

        LOGGER.info("Getting position with id: " + id);

        Optional<PositionRecord> positionRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(POSITION)
                        .where(POSITION.ID.equal(id))
                .fetchOne()));

        if (positionRecord.isPresent()) {
            LOGGER.info("Position with id: " + id + " fetched successfully");
        } else {
            LOGGER.info("Position with id: " + id + " not found");
        }

        return positionRecord;
     }

    @Override
    public Collection<PositionRecord> getAll() {

        LOGGER.info("Getting all positions from database");

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(POSITION).fetch();

            Collection<PositionRecord> positions = Lists.newArrayList();

            result.forEach(r -> {
                PositionRecord positionRecord = new PositionRecord(
                        r.getValue(POSITION.ID),
                        r.getValue(POSITION.POSITION_));

                positions.add(positionRecord);
            });

            if (positions.size() > 0) {
                LOGGER.info("Successfully fetched all positions from database");
            } else {
                LOGGER.info("Cannot get all positions. No positions found in database");
            }

            return positions;
        });
    }

    @Override
    public boolean update(PositionRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
            Preconditions.checkNotNull(entity.getId());
        } catch (NullPointerException ex) {
            LOGGER.error("Updating position record failed! PositionRecord or PositionRecord id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Updating position with id: " + entity.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(POSITION)
                    .set(POSITION.POSITION_, entity.getPosition())
                    .where(POSITION.ID.eq(entity.getId()))
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully updated position with id: " + entity.getId());
            } else {
                LOGGER.info("Cannot update position with id: " + entity.getId() + ". Position not found");
            }

            return count > 0;
        });
    }

    @Override
    public boolean insert(PositionRecord entity) {

        try {
            Preconditions.checkNotNull(entity);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding position record failed! PositionRecord cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Adding position with id: " + entity.getId());

        return executeQuery(ctx -> {
            Position position = Position.POSITION;

            int count = ctx.insertInto(position)
                    .set(position.POSITION_, entity.getPosition())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully added position with id: " + entity.getId());
            } else {
                LOGGER.info("Cannot add position with id: " + entity.getId());
            }

            return count > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting position failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting position with id: " + id);

        return executeQuery(ctx -> {
            int count = ctx.delete(POSITION).where(POSITION.ID.eq(id)).execute();

            if (count > 0) {
                LOGGER.info("Position with id: " + id + " deleted successfully");
            } else {
                LOGGER.info("Position not found! Cannot delete position with id: " + id);
            }

            return count > 0;
        });
    }

    @Override
    public boolean deleteAll() {

        LOGGER.info("Deleting all positions");

        return executeQuery(ctx -> {

            int count = 0;

            count = ctx.delete(POSITION).execute();

            if (count > 0) {
                LOGGER.info("All positions deleted successfully");
            } else {
                LOGGER.info("Cannot delete positions. Database is already empty");
            }

            return count > 0;
        });
    }
}
