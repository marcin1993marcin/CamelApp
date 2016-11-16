package com.app.camel.dao;

import com.app.camel.model.tables.records.UserSkillRecord;

import java.util.Collection;

/**
 * Interface which execute simple CRUD operations.
 */
public interface UserSkillRepository {
    /**
     * Gets all user skill records assigned to given user id
     * @param userId user id
     * @return collection of user skills
     */
    Collection<UserSkillRecord> getAll(Integer userId);

    /**
     * Inserts new user skills into database table
     * @param userSkills collection of user skills
     * @return true if records was added successfully otherwise false
     */
    boolean insert(Collection<UserSkillRecord> userSkills);

    /**
     * Updates user skills by values of given user skills entity properties
     * @param userSkills user skills which skill ids and user ids must match with
     *                   skill ids and user ids of records you want to update
     * @return true if all records was updated successfully otherwise false
     */
    boolean update(Collection<UserSkillRecord> userSkills);

    /**
     * Removes user skill with given skill id and assigned to given user id
     * @param userId user id
     * @param skillId collection of skill id
     * @return true if record was deleted successfully otherwise false
     */
    boolean delete(Integer userId, Integer skillId);

    /**
     * Removes all user skills assigned to given user id
     * @param userId user id
     * @return true if all records was deleted successfully otherwise false
     */
    boolean deleteAll(Integer userId);
}
