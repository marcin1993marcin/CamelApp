package com.app.camel.dao;

import com.app.camel.model.tables.records.SalaryRecord;

/**
 * Interface which execute simple CRUD operations.
 * Extends GenericRepository and specify ID type as Integer
 * and map return type of CRUD operations as ProjectRecord entity
 *
 * Inherit from GenericRepository the following methods:
 *
 * Optional<SalaryRecord> get(Integer id);
 * Collection<SalaryRecord> getAll();
 * boolean update(SalaryRecord entity);
 * boolean insert(SalaryRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */
public interface SalaryRepository extends GenericRepository<Integer, SalaryRecord> {

}
