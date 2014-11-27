package com.vko.core.dao;

import java.io.Serializable;

/**
 * Define some generate CRUD operations.
 * 
 * @author darkwing
 * 
 * @param <T> Domain type.
 */
public interface GenericDao<T> {

    /**
     * Query object by specified id.
     */
    T query(Serializable id);

    /**
     * Create an domain object.
     */
    void create(T t);

    /**
     * Delete an object.
     */
    void delete(T t);

    /**
     * Update an object.
     */
    void update(T t);

}
