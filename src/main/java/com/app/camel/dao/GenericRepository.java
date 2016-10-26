package com.app.camel.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<ID, T> {

    Optional<T> get(ID id);
    Collection<T> getAll() throws SQLException;
    boolean update(T entity);
    boolean insert(T entity);
    boolean delete(ID id);
    boolean deleteAll();
}
