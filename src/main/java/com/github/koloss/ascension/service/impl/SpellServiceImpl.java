package com.github.koloss.ascension.service.impl;

import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.model.SpellLevel;
import com.github.koloss.ascension.model.SpellType;
import com.github.koloss.ascension.repository.SpellRepository;
import com.github.koloss.ascension.service.SpellService;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpellServiceImpl extends CachedService<Spell, UUID> implements SpellService {
    private final SpellRepository spellRepository;

    public SpellServiceImpl(SpellRepository spellRepository, JavaPlugin plugin) {
        super(10, 60);

        this.spellRepository = spellRepository;
        super.start(plugin, spellRepository);
    }

    @Override
    protected void load() {
        List<Spell> spells = spellRepository.findAll();

        for (Spell spell : spells) {
            refreshCache(spell.getId(), spell.getUserId(), spell);
        }
    }

    @Override
    public Spell findById(UUID id) {
        if (cacheById.containsKey(id)) {
            return cacheById.get(id);
        }

        Optional<Spell> optional = spellRepository.findById(id);
        if (optional.isPresent()) {
            Spell result = optional.get();
            refreshCache(id, result.getUserId(), result);
        }

        return optional.orElse(null);
    }

    @Override
    public List<Spell> findAllByUserId(UUID userId) {
        if (cacheByUserId.containsKey(userId)) {
            return cacheByUserId.get(userId);
        }

        List<Spell> result = spellRepository.findAllByUserId(userId);
        for (Spell spell : result) {
            refreshCache(spell.getUserId(), userId, spell);
        }

        return result;
    }

    @Override
    public Spell create(UUID userId, SpellType type, SpellLevel level) {
        Spell spell = Spell.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .type(type)
                .level(level)
                .build();

        refreshCache(spell.getId(), spell.getUserId(), spell);
        spellRepository.insert(spell);
        return spell;
    }

    @Override
    public Spell updateLevelById(UUID id, SpellLevel level) {
        Spell spell = findById(id);
        spell.setLevel(level);

        refreshCache(id, spell.getUserId(), spell);
        updateCache.add(id);

        return spell;
    }
}
