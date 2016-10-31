package com.app.camel.dao;

import com.app.camel.model.tables.records.ProjectRecord;

/**
 * Interface which execute simple CRUD operations.
 * Extends GenericRepository and specify ID type as Integer
 * and map return type of CRUD operations as ProjectRecord entity
 *
 * Inherit from GenericRepository the following methods:
 *
 * Optional<ProjectRecord> get(Integer id);
 * Collection<ProjectRecord> getAll();
 * boolean update(ProjectRecord entity);
 * boolean insert(ProjectRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */
public interface ProjectRepository extends GenericRepository<Integer, ProjectRecord> {

}
