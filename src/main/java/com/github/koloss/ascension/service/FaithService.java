package com.github.koloss.ascension.service;

import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;

import java.util.List;
import java.util.UUID;

public interface FaithService {
    List<Faith> findAllByUserId(UUID userId);

    Faith findByUserIdAndAspect(UUID userId, DivineAspect aspect);

    Faith create(UUID userId, DivineAspect aspect);

    Faith update(Faith faith);

    boolean hasOpenedNextLevel(UUID userId, DivineAspect aspect);

    void save(Faith faith);
}
