package com.github.koloss.ascension.utils;

import com.github.koloss.ascension.common.AscensionMaterial;
import com.github.koloss.ascension.common.AscensionParams;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryUtils {
    public static boolean contains(Inventory inventory, AscensionMaterial material) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || !item.hasItemMeta()) {
                continue;
            }

            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            String materialName = container.get(AscensionParams.MATERIAL_KEY, PersistentDataType.STRING);

            if (material == AscensionMaterial.valueOf(materialName)) {
                return true;
            }
        }

        return false;
    }
}
