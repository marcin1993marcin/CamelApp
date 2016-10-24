package com.app.camel.dao;

import java.util.Collection;

public interface GenericRepository<ID, T> {

    T get(ID id);
    Collection<T> getAll();
    void update(T entity);
    void insert(T entity);
    void delete(ID id);
}
