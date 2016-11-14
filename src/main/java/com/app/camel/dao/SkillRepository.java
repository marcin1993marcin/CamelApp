package com.app.camel.dao;

import com.app.camel.model.tables.records.SkillRecord;

/**
 * Interface which execute simple CRUD operations.
 * Extends GenericRepository and specify ID type as Integer
 * and map return type of CRUD operations as SkillRecord entity
 *
 * Inherit from GenericRepository the following methods:
 *
 * Optional<SkillRecord> get(Integer id);
 * Collection<SkillRecord> getAll();
 * boolean update(SkillRecord entity);
 * boolean insert(SkillRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */
public interface SkillRepository extends GenericRepository<Integer, SkillRecord> {
}
