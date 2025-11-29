package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
public class Faith {
    private UUID id;

    private UUID userId;

    private DivineAspect aspect;

    private AtomicLong count;
}
