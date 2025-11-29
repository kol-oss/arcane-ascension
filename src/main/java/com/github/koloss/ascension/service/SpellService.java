package com.github.koloss.ascension.service;

import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.model.SpellLevel;
import com.github.koloss.ascension.model.SpellType;

import java.util.List;
import java.util.UUID;

public interface SpellService {
    Spell findById(UUID id);

    List<Spell> findAllByUserId(UUID userId);

    Spell create(UUID userId, SpellType type, SpellLevel level);

    Spell updateLevelById(UUID id, SpellLevel level);
}
