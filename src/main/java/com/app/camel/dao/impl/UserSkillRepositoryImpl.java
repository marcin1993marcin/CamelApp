package com.app.camel.dao.impl;

import com.app.camel.dao.UserSkillRepository;
import com.app.camel.model.tables.records.UserRecord;
import com.app.camel.model.tables.records.UserSkillRecord;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.jooq.Record;
import org.jooq.Result;

import java.util.Collection;
import java.util.Optional;

import static com.app.camel.model.Tables.USER_SKILL;

public class UserSkillRepositoryImpl extends GenericRepository implements UserSkillRepository {

    private static final Logger LOGGER = Logger.getLogger(UserSkillRepositoryImpl.class);

    @Override
    public Collection<UserSkillRecord> getAllUserSkillsForUser(Integer userId) {

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
    public boolean insertUserSkills(Collection<UserSkillRecord> userSkills) {
        return false;
    }

    @Override
    public boolean deleteUserSkills(Collection<UserSkillRecord> userSkills) {
        return false;
    }

    @Override
    public boolean deleteAllUserSkills() {
        return false;
    }
}
