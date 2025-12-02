package com.github.koloss.ascension.service;

import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.model.Skill;

import java.util.List;
import java.util.UUID;

public interface SkillService {
    List<Skill> findAllByUserId(UUID userId);

    Skill findByUserIdAndType(UUID userId, SkillType type);

    Skill create(UUID userId, SkillType type);

    Skill update(Skill skill);

    boolean hasOpenedNextLevel(UUID userId, SkillType type);

    void save(Skill skill);
}
