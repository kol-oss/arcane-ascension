package com.github.koloss.ascension.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.repository.FaithRepository;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.utils.LevelService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FaithServiceImpl implements FaithService {
    private final FaithRepository faithRepository;
    private final Cache<UUID, Map<DivineAspect, Faith>> cacheByUserId;

    private final Plugin plugin;
    private final LevelService levelService;

    public FaithServiceImpl(FaithRepository faithRepository, Plugin plugin, LevelService levelService) {
        this.faithRepository = faithRepository;
        this.plugin = plugin;
        this.levelService = levelService;

        this.cacheByUserId = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .removalListener((UUID _, Map<DivineAspect, Faith> value, RemovalCause cause) -> {
                    if (cause == RemovalCause.EXPIRED && value != null) {
                        value.forEach((_, faith) -> save(faith));
                    }
                })
                .build();
    }

    private void refreshCache(UUID userId, Faith faith) {
        Map<DivineAspect, Faith> cached = cacheByUserId
                .asMap()
                .computeIfAbsent(userId, _ -> new HashMap<>());

        cached.put(faith.getAspect(), faith);
    }

    @Override
    public List<Faith> findAllByUserId(UUID userId) {
        Map<DivineAspect, Faith> cached = cacheByUserId.getIfPresent(userId);
        if (cached != null) {
            return new ArrayList<>(cached.values());
        }

        List<Faith> faiths = faithRepository.findAllByUserId(userId);
        faiths.forEach(faith -> refreshCache(userId, faith));

        return faiths;
    }

    @Override
    public Faith findByUserIdAndAspect(UUID userId, DivineAspect aspect) {
        Map<DivineAspect, Faith> cached = cacheByUserId.getIfPresent(userId);
        if (cached != null && cached.containsKey(aspect)) {
            return cached.get(aspect);
        }

        Optional<Faith> faith = faithRepository
                .findByUserIdAndAspect(userId, aspect);

        faith.ifPresent(value -> refreshCache(userId, value));
        return faith.orElse(null);
    }

    @Override
    public Faith create(UUID userId, DivineAspect aspect) {
        Faith existing = findByUserIdAndAspect(userId, aspect);
        if (existing != null) {
            return existing;
        }

        Faith faith = Faith.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .aspect(aspect)
                .level(new AtomicInteger(1))
                .count(new AtomicLong(0))
                .build();

        refreshCache(userId, faith);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> faithRepository.insert(faith));

        return faith;
    }

    @Override
    public Faith update(Faith faith) {
        UUID userId = faith.getUserId();

        refreshCache(userId, faith);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> faithRepository.update(faith.getId(), faith));

        return faith;
    }

    @Override
    public boolean hasOpenedNextLevel(UUID userId, DivineAspect aspect) {
        Faith faith = findByUserIdAndAspect(userId, aspect);

        int currLevel = faith.getLevel().get();
        int expectedLevel = levelService.getLevel(faith.getCount().get());

        return currLevel < expectedLevel;
    }

    @Override
    public void save(Faith faith) {
        cacheByUserId.invalidate(faith.getUserId());
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> faithRepository.update(faith.getId(), faith));
    }
}
