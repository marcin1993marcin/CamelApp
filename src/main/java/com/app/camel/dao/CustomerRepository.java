package com.app.camel.dao;

import com.app.camel.model.tables.records.CustomerRecord;

/**
 * Interface extends GenericRepository and specify ID type as Integer
 *
 * Inherits methods:
 *
 * Optional<CustomerRecord> get(Integer id);
 * Collection<CustomerRecord> getAll();
 * boolean update(CustomerRecord entity);
 * boolean insert(CustomerRecord entity);
 * boolean delete(Integer id);
 * boolean deleteAll();
 */

public interface CustomerRepository extends GenericRepository<Integer, CustomerRecord>{


}
