package com.github.koloss.ascension.controller.progress.impl;

import com.github.koloss.ascension.constant.ProgressConstants;
import com.github.koloss.ascension.controller.progress.ProgressHandler;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningProgressHandler implements ProgressHandler {
    private long processOreBreak(BlockBreakEvent event) {
        Material block = event.getBlock().getType();
        return ProgressConstants.PROGRESS_BY_ORE.getOrDefault(block, 0L);
    }

    @Override
    public long getProgress(Event event) {
        if (event instanceof BlockBreakEvent) {
            return processOreBreak((BlockBreakEvent) event);
        }

        return 0;
    }
}
