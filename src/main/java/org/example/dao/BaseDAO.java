package org.example.dao;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseDAO<T> {
    List<T> readAll();

    T readById(Long id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(Long id);

    boolean existById(Long id);

}
