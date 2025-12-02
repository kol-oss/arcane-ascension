package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.common.LevelParams;

public class LevelUtils {
    public static long getProgressOfNextLevel(long experience) {
        return (long) getLevelFromProgress(experience) * LevelParams.XP_PR_LEVEL + LevelParams.XP_PR_LEVEL;
    }

    public static int getLevelFromProgress(long experience) {
        return (int) (experience / LevelParams.XP_PR_LEVEL);
    }

    public static boolean isLevelOpen(int level, int userLevel, long experience) {
        int expectedLevel = LevelUtils.getLevelFromProgress(experience);

        return (userLevel + 1) == level && expectedLevel > userLevel;
    }

    public static int getBuffForLevel(int level) {
        return level * LevelParams.BUFF_PER_LEVEL;
    }
}