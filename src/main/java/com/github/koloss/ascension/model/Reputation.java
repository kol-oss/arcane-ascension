package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
public class Reputation {
    private UUID id;

    private UUID userId;

    private DivineBranch branch;

    private AtomicLong reputation;
}
