package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.constant.LevelConstants;

public final class LevelUtils {
    public static long getProgressOfNextLevel(long progress) {
        return (long) getLevelFromProgress(progress) * LevelConstants.XP_PR_LEVEL + LevelConstants.XP_PR_LEVEL;
    }

    public static int getLevelFromProgress(long progress) {
        return (int) (progress / LevelConstants.XP_PR_LEVEL);
    }

    public static boolean isLevelOpen(int level, int currLevel, long progress) {
        int expectedLevel = LevelUtils.getLevelFromProgress(progress);

        return (currLevel + 1) == level && expectedLevel > currLevel;
    }

    public static int getBuffForLevel(int level) {
        return level * LevelConstants.BUFF_PER_LEVEL;
    }
}