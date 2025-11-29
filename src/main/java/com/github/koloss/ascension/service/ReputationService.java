package com.github.koloss.ascension.service;

import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;

import java.util.List;
import java.util.UUID;

public interface ReputationService {
    Reputation findById(UUID id);

    List<Reputation> findAllByUserId(UUID userId);

    Reputation findByUserIdAndBranch(UUID userId, DivineBranch branch);

    Reputation create(UUID userId, DivineBranch branch);

    Reputation addById(UUID id, long added);
}
