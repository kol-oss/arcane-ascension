package com.github.koloss.ascension.repository.base;

import java.util.Optional;

public interface DataRepository<T, K> {
    Optional<T> findById(K id);

    void insert(T value);

    void update(K id, T value);

    void deleteById(K id);
}
