package com.github.koloss.ascension.service.impl;

import com.github.koloss.ascension.model.DivineBranch;
import com.github.koloss.ascension.model.Reputation;
import com.github.koloss.ascension.repository.ReputationRepository;
import com.github.koloss.ascension.service.ReputationService;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ReputationServiceImpl extends CachedService<Reputation, UUID> implements ReputationService {
    private final ReputationRepository reputationRepository;

    public ReputationServiceImpl(ReputationRepository reputationRepository, JavaPlugin plugin) {
        super(10, 30);

        this.reputationRepository = reputationRepository;
        super.start(plugin, reputationRepository);
    }

    @Override
    protected void load() {
        List<Reputation> reputations = reputationRepository.findAll();

        for (Reputation reputation : reputations) {
            refreshCache(reputation.getId(), reputation.getUserId(), reputation);
        }
    }

    @Override
    public Reputation findById(UUID id) {
        if (cacheById.containsKey(id)) {
            return cacheById.get(id);
        }

        Optional<Reputation> optional = reputationRepository.findById(id);
        if (optional.isPresent()) {
            Reputation result = optional.get();
            refreshCache(id, result.getUserId(), result);
        }

        return optional.orElse(null);
    }

    @Override
    public List<Reputation> findAllByUserId(UUID userId) {
        if (cacheByUserId.containsKey(userId)) {
            return cacheByUserId.get(userId);
        }

        List<Reputation> result = reputationRepository.findAllByUserId(userId);
        for (Reputation reputation : result) {
            refreshCache(reputation.getUserId(), userId, reputation);
        }

        return result;
    }

    @Override
    public Reputation findByUserIdAndBranch(UUID userId, DivineBranch branch) {
        if (cacheByUserId.containsKey(userId)) {
            return cacheByUserId.get(userId).stream()
                    .filter(r -> r.getBranch() == branch)
                    .findFirst()
                    .orElse(null);
        }

        Optional<Reputation> optional = reputationRepository.findByUserIdAndBranch(userId, branch);
        if (optional.isPresent()) {
            Reputation result = optional.get();
            refreshCache(result.getId(), result.getUserId(), result);
        }

        return optional.orElse(null);
    }

    @Override
    public Reputation create(UUID userId, DivineBranch branch) {
        Reputation reputation = Reputation.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .branch(branch)
                .reputation(new AtomicLong(0))
                .build();

        refreshCache(reputation.getId(), reputation.getUserId(), reputation);
        reputationRepository.insert(reputation);
        return reputation;
    }

    @Override
    public Reputation addById(UUID id, long added) {
        Reputation reputation = findById(id);
        reputation.getReputation().addAndGet(added);

        refreshCache(id, reputation.getUserId(), reputation);
        updateCache.add(id);

        return reputation;
    }
}
