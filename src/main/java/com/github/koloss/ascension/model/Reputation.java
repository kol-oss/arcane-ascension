package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Reputation {
    private UUID id;

    private UUID userId;

    private DivineBranch branch;

    private long reputation;
}
