package com.github.koloss.ascension.repository;

import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.util.List;
import java.util.UUID;

public interface SpellRepository extends DataRepository<Spell, UUID> {
    List<Spell> findAllByUserId(UUID userId);
}
