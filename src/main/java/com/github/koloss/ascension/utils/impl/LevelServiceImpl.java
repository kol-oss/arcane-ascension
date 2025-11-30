package com.github.koloss.ascension.utils.impl;

import com.github.koloss.ascension.utils.LevelService;

public class LevelServiceImpl implements LevelService {
    private static final int REPUTATION_PER_LEVEL = 50;

    @Override
    public int getLevel(long reputation) {
        return Math.max(1, (int) (reputation / REPUTATION_PER_LEVEL) + 1);
    }
}
