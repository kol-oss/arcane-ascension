package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.common.AscensionMaterial;
import org.bukkit.inventory.Inventory;

public interface InventoryService {
    boolean contains(Inventory inventory, AscensionMaterial material);
}
