package com.app.camel.dao;

import com.app.camel.model.tables.records.UserSkillRecord;

import java.util.Collection;

public interface UserSkillRepository {
    Collection<UserSkillRecord> getAll(Integer userId);
    boolean insert(Collection<UserSkillRecord> userSkills);
    boolean delete(Integer userId, Collection<Integer> skillIds);
    boolean deleteAll(Integer userId);
}
