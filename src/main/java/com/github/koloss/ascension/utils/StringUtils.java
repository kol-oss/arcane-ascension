package com.github.koloss.ascension.utils;

public final class StringUtils {
    public static String getPart(String base, double percent) {
        int count = (int) (base.length() * percent);
        return base.substring(0, count);
    }
}
