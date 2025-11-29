package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Spell {
    private UUID id;

    private UUID userId;

    private SpellType type;

    private SpellLevel level;
}
