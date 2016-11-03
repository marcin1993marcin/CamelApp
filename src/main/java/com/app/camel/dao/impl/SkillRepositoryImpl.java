package com.app.camel.dao.impl;

import com.app.camel.dao.SkillRepository;
import com.app.camel.model.tables.Skill;
import com.app.camel.model.tables.records.SkillRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.tables.Skill.SKILL;

public class SkillRepositoryImpl extends GenericRepository implements SkillRepository {

    private static final Logger LOGGER = Logger.getLogger(SkillRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SkillRecord> get(Integer id) {

        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting skill failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Getting project with id: " + id);

        Optional<SkillRecord> skillRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(SKILL)
                        .where(SKILL.ID.equal(id))
                        .fetchOne()));

        if (skillRecord.isPresent()) {
            LOGGER.info("Skill with id: " + id + " fetched successfully");
        } else {
            LOGGER.info("Skill with id: " + id + " not found");
        }

        return skillRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<SkillRecord> getAll() {
        LOGGER.info("Getting all skills from database");

        return executeQuery(ctx -> {

            Result<Record> result = ctx.select().from(SKILL).fetch();

            Collection<SkillRecord> skills = Lists.newArrayList();

            result.forEach(r -> {
                SkillRecord skill = new SkillRecord(
                        r.getValue(SKILL.ID),
                        r.getValue(SKILL.NAME),
                        r.getValue(SKILL.PARENT_ID));

                skills.add(skill);
            });

            if (skills.size() > 0) {
                LOGGER.info("Successfully fetched all skills from database");
            } else {
                LOGGER.info("Cannot get all skills. No skills found in database");
            }

            return skills;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(SkillRecord skill) {

        try {
            Preconditions.checkNotNull(skill);
            Preconditions.checkNotNull(skill.getId());
        } catch (NullPointerException ex) {
            LOGGER.error("Updating skill record failed! SkillRecord or SkillRecord id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Updating skill with id: " + skill.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(SKILL)
                    .set(SKILL.NAME, skill.getName())
                    .set(SKILL.PARENT_ID, skill.getParentId())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully updated skill with id: " + skill.getId());
            } else {
                LOGGER.info("Cannot update skill with id: " + skill.getId() + ". Skill not found");
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(SkillRecord skill) {

        try {
            Preconditions.checkNotNull(skill);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding skill record failed! SkillRecord cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Adding skill with id: " + skill.getId());

        return executeQuery(ctx -> {
            Skill s = Skill.SKILL;

            int count = ctx.insertInto(s)
                    .set(s.NAME, skill.getName())
                    .set(s.PARENT_ID, skill.getParentId())
                    .execute();

            if (count > 0) {
                LOGGER.info("Successfully added skill with id: " + skill.getId());
            } else {
                LOGGER.info("Cannot add skill with id: " + skill.getId());
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
            LOGGER.error("Deleting skill failed! Id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting skill with id: " + id);

        return executeQuery(ctx -> {
            int count = ctx.delete(SKILL).where(SKILL.ID.eq(id)).execute();

            if (count > 0) {
                LOGGER.info("Skill with id: " + id + " deleted successfully");
            } else {
                LOGGER.info("Skill not found! Cannot delete skill with id : " + id);
            }

            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {

        LOGGER.info("Deleting all skills");

        return executeQuery(ctx -> {

            int count = ctx.delete(SKILL).execute();

            if (count > 0) {
                LOGGER.info("All skills deleted successfully");
            } else {
                LOGGER.info("Cannot delete skills. Database is empty");
            }

            return count > 0;
        });
    }
}