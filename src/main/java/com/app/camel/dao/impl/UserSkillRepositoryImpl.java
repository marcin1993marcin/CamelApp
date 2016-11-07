package com.app.camel.dao.impl;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.model.tables.UserSkill;
import com.app.camel.model.tables.records.UserSkillRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;

import static com.app.camel.model.Tables.USER_SKILL;

public class UserSkillRepositoryImpl extends GenericRepository implements UserSkillRepository {

    private static final Logger LOGGER = Logger.getLogger(UserSkillRepositoryImpl.class);

    @Override
    public Collection<UserSkillRecord> getAll(Integer userId) {

        try {
            Preconditions.checkNotNull(userId);
        } catch (NullPointerException ex) {
            LOGGER.error("Getting user skills failed! User id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Getting user skills with user id: " + userId);

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

            if (userSkills.size() > 0) {
                LOGGER.info("Successfully fetched user skills for requested user from database");
            } else {
                LOGGER.info("Cannot get requested user skills. No user skills with given user id found in database");
            }

            return userSkills;
        });
    }

    @Override
    public boolean insert(Collection<UserSkillRecord> userSkills) {

        try {
            Preconditions.checkNotNull(userSkills);
        } catch (NullPointerException ex) {
            LOGGER.error("Adding user skills failed! No user skills to add");
            ex.printStackTrace();
        }

        LOGGER.info("Adding " + userSkills.size() + " user skills");

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

            int failed = userSkills.size() - count;
            if (failed == 0) {
                LOGGER.info("Successfully added " + count + " user skills");
            } else {
                LOGGER.info("Failed to add " + failed + " user skills" );
            }

            return failed == 0;
        });
    }

    @Override
    public boolean delete(Integer userId, Collection<Integer> skillIds) {
        try {
            Preconditions.checkNotNull(userId);
            Preconditions.checkNotNull(skillIds);
        } catch (NullPointerException ex) {
            LOGGER.error("Deleting user skills failed! No user skills to delete or no user id");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting " + skillIds.size() + " user skills");

        return executeQuery(ctx -> {
            int count = ctx.delete(USER_SKILL)
                    .where(USER_SKILL.SKILL_ID.in(skillIds))
                    .and(USER_SKILL.USER_ID.eq(userId))
                    .execute();

            int failed = skillIds.size() - count;
            if (failed == 0) {
                LOGGER.info("Deleted " + count + " user skills");
            } else {
                LOGGER.info("Failed to delete " + failed + " user skills");
            }

            return failed == 0;
        });
    }

    @Override
    public boolean deleteAll(Integer userId) {
        try {
            Preconditions.checkNotNull(userId);
        } catch (NullPointerException ex) {
            LOGGER.info("Deleting user skills failed! User id cannot be null");
            ex.printStackTrace();
        }

        LOGGER.info("Deleting all user skills for user with id: " + userId);

        return executeQuery(ctx -> {
            int count = ctx.delete(USER_SKILL).where(USER_SKILL.USER_ID.eq(userId)).execute();

            if (count > 0) {
                LOGGER.info("User skills for user with id: " + userId + " deleted successfully");
            } else {
                LOGGER.info("User skills not found! Cannot delete user skills for user with id: " + userId);
            }

            return count > 0;
        });
    }
}
