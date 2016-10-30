package com.app.camel.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericRepository<ID, T> {
    // review - brak javadoc
    Optional<T> get(ID id);
    Collection<T> getAll();
    boolean update(T entity);
    boolean insert(T entity);
    boolean delete(ID id);
    boolean deleteAll();
}
