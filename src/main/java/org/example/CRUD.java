package org.example;

import java.util.List;

public interface CRUD {
    <T> void save(T entity);
    <T> void update(T entity);
    <T> void delete(T entity);
    <T> List<T> findAll(Class<T> entityClass);
    <T> T findById(Class<T> entityClass, Object id);
}
