package com.app.camel.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<ID, T> {

    /**
     * Gets one record from database table with given id
     *
     * @param id Primary key of table record
     * @return If row with given id not found return Optional.empty
     * else return row from database table mapped to dto
     */
    Optional<T> get(ID id);

    /**
     * Gets all database table records
     *
     * @return Collection of database table rows if any found or else return empty Collection
     */
    Collection<T> getAll();

    /**
     * Update database row by values of given entity properties
     *
     * @param entity Entity which id must matched with primary key of table record which you want to update
     * @return True if record was updated successfully otherwise return false
     */
    boolean update(T entity);

    /**
     * Insert new record to database table
     *
     * @param entity Entity which will be inserted to database table
     * @return True if row was added successfully otherwise return false
     */
    boolean insert(T entity);

    /**
     * Removes one table record with given id
     *
     * @param id Primary key of table record
     * @return True if record was deleted successfully otherwise return false
     */
    boolean delete(ID id);

    /**
     * Removes all database table records
     *
     * @return True if all records was deleted successfully otherwise return false
     */
    boolean deleteAll();
}
