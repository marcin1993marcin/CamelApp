package com.app.camel.dao.impl;

import com.app.camel.dao.SkillRepository;
import com.app.camel.model.tables.Skill;
import com.app.camel.model.tables.records.SkillRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.tables.Skill.SKILL;

public class SkillRepositoryImpl extends GenericRepository implements SkillRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkillRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SkillRecord> get(Integer id) {
        try {
            Preconditions.checkNotNull(id);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting skill failed! Id cannot be null");
        }

        LOGGER.info("Getting skill with id: {}", id);

        Optional<SkillRecord> skillRecord = executeQuery(ctx -> Optional.ofNullable(
                ctx.selectFrom(SKILL)
                        .where(SKILL.ID.equal(id))
                        .fetchOne()));

        LOGGER.info("Skill with id: {} fetched successfully", id);
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

            LOGGER.info("Successfully fetched all skills from database");
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
        }

        LOGGER.info("Updating skill with id: {}", skill.getId());

        return executeQuery(ctx -> {
            int count = ctx.update(SKILL)
                    .set(SKILL.NAME, skill.getName())
                    .set(SKILL.PARENT_ID, skill.getParentId())
                    .where(SKILL.ID.eq(skill.getId()))
                    .execute();

            LOGGER.info("Successfully updated skill with id: {}" , skill.getId());
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
        }

        LOGGER.info("Adding skill with id: {}", skill.getId());

        return executeQuery(ctx -> {
            Skill s = Skill.SKILL;

            int count = ctx.insertInto(s)
                    .set(s.NAME, skill.getName())
                    .set(s.PARENT_ID, skill.getParentId())
                    .execute();

            LOGGER.info("Successfully added skill with id: {}", skill.getId());
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
        }

        LOGGER.info("Deleting skill with id: {}", id);

        return executeQuery(ctx -> {
            ctx.transaction(nested -> DSL.using(nested).delete(SKILL).where(SKILL.ID.eq(id)).execute());

            LOGGER.info("Skill with id: {} deleted successfully", id);
            return true;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll() {
        return false;
    }
}
