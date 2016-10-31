package com.app.camel.dao;

import com.app.camel.model.tables.records.UserRecord;

import java.sql.SQLException;

/**
 * Interface which execute simple CRUD operations.
 * Extends GenericRepository and specify ID type as Integer
 * and map return type of CRUD operations as ProjectRecord entity
 *
 * Inherit from GenericRepository the following methods:
 *
 * Optional<UserRecord> get(Integer id);
 * Collection<UserRecord> getAll();
 * boolean update(UserRecord entity);
 * boolean insert(UserRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */
public interface UserRepository extends GenericRepository<Integer, UserRecord>{

}
