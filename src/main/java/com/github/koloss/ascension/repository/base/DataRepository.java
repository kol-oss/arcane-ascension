package com.github.koloss.ascension.repository.base;

import java.util.List;
import java.util.Optional;

public interface DataRepository<T, K> {
    Optional<T> findById(K id);

    List<T> findAll();

    void insert(T value);

    void update(K id, T value);

    void deleteById(K id);
}
