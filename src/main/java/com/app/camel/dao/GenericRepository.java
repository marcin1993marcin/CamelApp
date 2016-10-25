package com.app.camel.dao;

import java.util.Collection;

public interface GenericRepository<ID, T> {

    T get(ID id);
    Collection<T> getAll();
    boolean update(T entity);
    boolean insert(T entity);
    void delete(ID id);
}
