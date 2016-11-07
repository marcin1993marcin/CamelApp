package com.app.camel.dao;

import com.app.camel.model.tables.records.UserSkillRecord;

import java.util.Collection;

public interface UserSkillRepository {
    Collection<UserSkillRecord> getAllUserSkillsForUser(Integer userId);
    boolean insertUserSkills(Collection<UserSkillRecord> userSkills);
    boolean deleteUserSkills(Integer userId, Collection<Integer> skillIds);
    boolean deleteAllUserSkills();
}
