package com.app.camel.dao;

import com.app.camel.model.tables.records.UserSkillRecord;

import java.util.Collection;

public interface UserSkillRepository {
    /**
     * Gets all user skill records assigned to given user id
     * @param userId user id
     * @return collection of user skills
     */
    Collection<UserSkillRecord> getAll(Integer userId);

    /**
     * Insert new user skills into database table
     * @param userSkills collection of user skills
     * @return true if records was added successfully otherwise false
     */
    boolean insert(Collection<UserSkillRecord> userSkills);

    /**
     * Removes user skills with given skill id and assigned to given user id
     * @param userId user id
     * @param skillIds collection of skill ids
     * @return true if records was deleted successfully otherwise false
     */
    boolean delete(Integer userId, Collection<Integer> skillIds);

    /**
     * Removes all user skills assigned to given user id
     * @param userId user id
     * @return true if all records was deleted successfully otherwise false
     */
    boolean deleteAll(Integer userId);
}
