package com.github.koloss.ascension.common;

import com.github.stefvanschie.inventoryframework.pane.util.Slot;

import java.util.Map;

public final class MenuParams {
    /**
     * Represents the slots for 23 levels menu with corresponding structure
     *
     * <pre>
     * -   + + +   + + +
     * +   +   +   +   +
     * +   +   +   +   +
     * + + +   + + +   +
     * </pre>
     */
    private static final Map<Integer, Slot> LEVEL_MENU_SLOTS = Map.ofEntries(
            // Left side vertical column (X=0)
            Map.entry(1, Slot.fromXY(0, 1)),
            Map.entry(2, Slot.fromXY(0, 2)),
            Map.entry(3, Slot.fromXY(0, 3)),

            // Inner column 1 (X=1)
            Map.entry(4, Slot.fromXY(1, 3)),

            // Inner column 2 (X=2)
            Map.entry(5, Slot.fromXY(2, 3)),
            Map.entry(6, Slot.fromXY(2, 2)),
            Map.entry(7, Slot.fromXY(2, 1)),
            Map.entry(8, Slot.fromXY(2, 0)),

            // Center column 1 (X=3)
            Map.entry(9, Slot.fromXY(3, 0)),

            // Center column 2 (X=4)
            Map.entry(10, Slot.fromXY(4, 0)),
            Map.entry(11, Slot.fromXY(4, 1)),
            Map.entry(12, Slot.fromXY(4, 2)),
            Map.entry(13, Slot.fromXY(4, 3)),

            // Inner column 3 (X=5)
            Map.entry(14, Slot.fromXY(5, 3)),

            // Inner column 4 (X=6)
            Map.entry(15, Slot.fromXY(6, 3)),
            Map.entry(16, Slot.fromXY(6, 2)),
            Map.entry(17, Slot.fromXY(6, 1)),
            Map.entry(18, Slot.fromXY(6, 0)),

            // Right side column 1 (X=7)
            Map.entry(19, Slot.fromXY(7, 0)),

            // Right side vertical column (X=8)
            Map.entry(20, Slot.fromXY(8, 0)),
            Map.entry(21, Slot.fromXY(8, 1)),
            Map.entry(22, Slot.fromXY(8, 2)),
            Map.entry(23, Slot.fromXY(8, 3))
    );

    public static Slot getSlot(int level) {
        return LEVEL_MENU_SLOTS.get(level);
    }
}
