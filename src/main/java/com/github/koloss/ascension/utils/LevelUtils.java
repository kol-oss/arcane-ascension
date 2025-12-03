package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.constant.LevelConstants;

public final class LevelUtils {
    public static long getProgressOfNextLevel(long progress) {
        return (long) getLevelFromProgress(progress) * LevelConstants.PROGRESS_PER_LEVEL + LevelConstants.PROGRESS_PER_LEVEL;
    }

    public static int getLevelFromProgress(long progress) {
        return (int) (progress / LevelConstants.PROGRESS_PER_LEVEL);
    }

    public static boolean isLevelOpen(int level, int currLevel, long progress) {
        int expectedLevel = LevelUtils.getLevelFromProgress(progress);

        return (currLevel + 1) == level && expectedLevel > currLevel;
    }
}