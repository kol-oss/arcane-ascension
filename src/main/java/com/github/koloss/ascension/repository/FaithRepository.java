package com.github.koloss.ascension.repository;

import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FaithRepository extends DataRepository<Faith, UUID> {
    Optional<Faith> findByUserIdAndAspect(UUID userId, DivineAspect aspect);

    List<Faith> findAllByUserId(UUID userId);
}
