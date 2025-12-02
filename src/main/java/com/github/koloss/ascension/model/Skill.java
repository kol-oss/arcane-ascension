package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Builder
public class Skill {
    private UUID id;

    private UUID userId;

    private SkillType type;

    private AtomicInteger levelCount;

    private AtomicLong progressCount;

    public int getLevel() {
        return levelCount != null ? levelCount.get() : 0;
    }

    public long getProgress() {
        return progressCount != null ? progressCount.get() : 0;
    }
}
