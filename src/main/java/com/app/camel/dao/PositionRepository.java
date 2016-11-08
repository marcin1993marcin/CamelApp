package com.app.camel.dao;

import com.app.camel.model.tables.records.PositionRecord;

/**
 * Interface which execute simple CRUD operations.
 * Extends GenericRepository and specify ID type as Integer
 * and map return type of CRUD operations as ProjectRecord entity
 *
 * Inherit from GenericRepository the following methods:
 *
 * Optional<PositionRecord> get(Integer id);
 * Collection<PositionRecord> getAll();
 * boolean update(PositionRecord entity);
 * boolean insert(PositionRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */
public interface PositionRepository extends GenericRepository<Integer, PositionRecord> {
}
