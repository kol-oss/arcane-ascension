package com.github.koloss.ascension.utils.converter;

import java.util.TreeMap;

public final class NumberConverter {
    private final static TreeMap<Integer, String> ROMAN_MAP = new TreeMap<>();

    static {
        ROMAN_MAP.put(10, "X");
        ROMAN_MAP.put(9, "IX");
        ROMAN_MAP.put(5, "V");
        ROMAN_MAP.put(4, "IV");
        ROMAN_MAP.put(1, "I");
    }

    public static String toRoman(int number) {
        if (number <= 0)
            return "";

        int largestValue = ROMAN_MAP.floorKey(number);
        if (number == largestValue) {
            return ROMAN_MAP.get(number);
        }

        return ROMAN_MAP.get(largestValue) + toRoman(number - largestValue);
    }

    public static double toScale(int number) {
        return (double) number / 100;
    }

    public static int toPercent(double scale) {
        return (int) (scale * 100);
    }
}
