package com.app.camel.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<ID, T> {

    Optional<T> get(ID id) throws SQLException;
    Collection<T> getAll() throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean insert(T entity) throws SQLException;
    boolean delete(ID id) throws SQLException;
    boolean deleteAll() throws SQLException;
}
