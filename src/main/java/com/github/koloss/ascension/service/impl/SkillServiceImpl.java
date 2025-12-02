package com.github.koloss.ascension.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.repository.SkillRepository;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.LevelUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SkillServiceImpl extends BaseService implements SkillService {
    private final SkillRepository skillRepository;
    private final Cache<UUID, Map<SkillType, Skill>> cacheByUserId;

    public SkillServiceImpl(SkillRepository skillRepository, Plugin plugin) {
        super(plugin);

        this.skillRepository = skillRepository;

        this.cacheByUserId = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .removalListener((UUID _, Map<SkillType, Skill> value, RemovalCause cause) -> {
                    if (cause == RemovalCause.EXPIRED && value != null) {
                        value.forEach((_, skill) -> save(skill));
                    }
                })
                .build();
    }

    private void refreshCache(UUID userId, Skill skill) {
        Map<SkillType, Skill> cached = cacheByUserId
                .asMap()
                .computeIfAbsent(userId, _ -> new HashMap<>());

        cached.put(skill.getType(), skill);
    }

    @Override
    public List<Skill> findAllByUserId(UUID userId) {
        Map<SkillType, Skill> cached = cacheByUserId.getIfPresent(userId);
        if (cached != null) {
            return new ArrayList<>(cached.values());
        }

        List<Skill> skills = skillRepository.findAllByUserId(userId);
        skills.forEach(skill -> refreshCache(userId, skill));

        return skills;
    }

    @Override
    public Skill findByUserIdAndType(UUID userId, SkillType type) {
        Map<SkillType, Skill> cached = cacheByUserId.getIfPresent(userId);
        if (cached != null && cached.containsKey(type)) {
            return cached.get(type);
        }

        Optional<Skill> skill = skillRepository.findByUserIdAndType(userId, type);
        skill.ifPresent(value -> refreshCache(userId, value));

        return skill.orElse(null);
    }

    @Override
    public Skill create(UUID userId, SkillType type) {
        Skill existing = findByUserIdAndType(userId, type);
        if (existing != null) {
            return existing;
        }

        Skill skill = Skill.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .type(type)
                .levelCount(new AtomicInteger(0))
                .progressCount(new AtomicLong(0))
                .build();

        refreshCache(userId, skill);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> skillRepository.insert(skill));

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        UUID userId = skill.getUserId();

        refreshCache(userId, skill);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> skillRepository.update(skill.getId(), skill));

        return skill;
    }

    @Override
    public boolean hasOpenedNextLevel(UUID userId, SkillType type) {
        Skill skill = findByUserIdAndType(userId, type);

        int currLevel = skill.getLevel();
        int expectedLevel = LevelUtils.getLevelFromProgress(skill.getProgress());

        return currLevel < expectedLevel;
    }

    @Override
    public void save(Skill skill) {
        cacheByUserId.invalidate(skill.getUserId());
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> skillRepository.update(skill.getId(), skill));
    }
}
