package com.github.koloss.ascension.controller.progress.impl;

import com.github.koloss.ascension.constant.ProgressConstants;
import com.github.koloss.ascension.controller.progress.ProgressHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class FarmingProgressHandler implements ProgressHandler {
    private long processHarvest(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!(block.getBlockData() instanceof Ageable ageable)) {
            return 0;
        }

        if (ageable.getAge() != ageable.getMaximumAge()) {
            return 0;
        }

        return 1;
    }

    private long processPlant(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Material seed = item.getType();

        return ProgressConstants.PROGRESS_BY_SEED.getOrDefault(seed, 0L);
    }

    @Override
    public long getProgress(Event event) {
        if (event instanceof BlockBreakEvent) {
            return processHarvest((BlockBreakEvent) event);
        } else if (event instanceof BlockPlaceEvent) {
            return processPlant((BlockPlaceEvent) event);
        }

        return 0;
    }
}
