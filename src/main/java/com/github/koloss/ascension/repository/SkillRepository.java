package com.github.koloss.ascension.repository;

import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SkillRepository extends DataRepository<Skill, UUID> {
    Optional<Skill> findByUserIdAndType(UUID userId, SkillType type);

    List<Skill> findAllByUserId(UUID userId);
}
