package org.example.service;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BaseService<T> {
    List<T> readAll();

    T readById(Long id) throws Exception;

    T create(T createRequest);

    T update(T updateRequest);

    boolean deleteById(Long id) throws Exception;
}
