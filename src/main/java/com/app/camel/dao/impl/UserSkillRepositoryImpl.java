package com.app.camel.dao.impl;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.model.tables.UserSkill;
import com.app.camel.model.tables.records.UserSkillRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.app.camel.model.Tables.USER_SKILL;

public class UserSkillRepositoryImpl extends GenericRepository implements UserSkillRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSkillRepositoryImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<UserSkillRecord> getAll(Integer userId) {
        try {
            Preconditions.checkNotNull(userId);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting user skills failed! User id cannot be null");
        }

        LOGGER.info("Getting user skills with user id: {}", userId);

        return executeQuery(ctx -> {
            Result<Record> result = ctx.select().from(USER_SKILL)
                    .where(USER_SKILL.USER_ID.eq(userId))
                    .fetch();

            Collection<UserSkillRecord> userSkills = Lists.newArrayList();

            result.forEach(r -> {
                UserSkillRecord userSkill = new UserSkillRecord(
                        r.getValue(USER_SKILL.SKILL_ID),
                        r.getValue(USER_SKILL.USER_ID),
                        r.getValue(USER_SKILL.LEVEL),
                        r.getValue(USER_SKILL.NOTE)
                );

                userSkills.add(userSkill);
            });

            LOGGER.info("Successfully fetched user skills for requested user from database");
            return userSkills;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Collection<UserSkillRecord> userSkills) {
        try {
            Preconditions.checkNotNull(userSkills);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding user skills failed! No user skills to add");
        }

        LOGGER.info("Adding {} user skills", userSkills.size());

        return executeQuery(ctx -> {
            UserSkill userSkill = UserSkill.USER_SKILL;

            int count = 0;
            for (UserSkillRecord userSkillRecord: userSkills) {
                count += ctx.insertInto(userSkill)
                        .set(userSkill.SKILL_ID, userSkillRecord.getSkillId())
                        .set(userSkill.USER_ID, userSkillRecord.getUserId())
                        .set(userSkill.LEVEL, userSkillRecord.getLevel())
                        .set(userSkill.NOTE, userSkillRecord.getNote())
                        .execute();
            }

            LOGGER.info("Successfully added {} user skills", count);
            return count > 0;
        });
    }

    @Override
    public boolean update(Collection<UserSkillRecord> userSkills) {
        try {
            Preconditions.checkNotNull(userSkills);
        } catch (NullPointerException ex) {
            LOGGER.error("Updating user skill records failed! No user records to update");
        }

        LOGGER.info("Updating {} user skills", userSkills.size());

        return executeQuery(ctx -> {
            int count = 0;
            for (UserSkillRecord userSkill: userSkills) {
                try {
                    Preconditions.checkNotNull(userSkill);
                    Preconditions.checkNotNull(userSkill.getSkillId());
                    Preconditions.checkNotNull(userSkill.getUserId());
                } catch (NullPointerException ex) {
                    LOGGER.error("Updating user skill record failed! UserSkillRecord, UserSkillRecord skill id or UserSkillRecord user id cannot be null");
                }
                count += ctx.update(USER_SKILL)
                        .set(USER_SKILL.LEVEL, userSkill.getLevel())
                        .set(USER_SKILL.NOTE, userSkill.getNote())
                        .where(USER_SKILL.SKILL_ID.eq(userSkill.getSkillId()))
                        .and(USER_SKILL.USER_ID.eq(userSkill.getUserId()))
                        .execute();
            }

            LOGGER.info("Successfully updated {} user skills", count);
            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Integer userId, Integer skillId) {
        try {
            Preconditions.checkNotNull(userId);
            Preconditions.checkNotNull(skillId);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting user skill failed! No skill id or no user id");
        }

        LOGGER.info("Deleting user skill with skill id: {}; user id: {}", skillId, userId);

        return executeQuery(ctx -> {
            int count = ctx.delete(USER_SKILL)
                    .where(USER_SKILL.SKILL_ID.eq(skillId))
                    .and(USER_SKILL.USER_ID.eq(userId))
                    .execute();

            LOGGER.info("Deleted {} user skills", count);
            return count > 0;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteAll(Integer userId) {
        try {
            Preconditions.checkNotNull(userId);
        } catch (NullPointerException ex) {
            LOGGER.info("Deleting user skills failed! User id cannot be null");
        }

        LOGGER.info("Deleting all user skills for user with id: {}", userId);

        return executeQuery(ctx -> {
            int count = ctx.delete(USER_SKILL).where(USER_SKILL.USER_ID.eq(userId)).execute();

            LOGGER.info("User skills for user with id: {} deleted successfully", userId);
            return count > 0;
        });
    }
}
