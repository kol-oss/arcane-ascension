package com.github.koloss.ascension.repository;

import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReputationRepository extends DataRepository<Reputation, UUID> {
    Optional<Reputation> findByUserIdAndBranch(UUID userId, DivineBranch branch);

    List<Reputation> findAllByUserId(UUID userId);
}
