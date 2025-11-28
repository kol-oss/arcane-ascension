package com.github.koloss.ascension.items;

import com.github.koloss.ascension.common.AscensionMaterial;
import com.github.koloss.ascension.common.AscensionParams;
import com.github.koloss.ascension.items.impl.TomeOfAscension;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public final class ItemFactory {
    private static final Map<AscensionMaterial, ItemHandler> itemByMaterial = new HashMap<>();

    static {
        itemByMaterial.put(AscensionMaterial.TOME_OF_ASCENSION, new TomeOfAscension());
    }

    public static ItemHandler getHandler(AscensionMaterial material) {
        return itemByMaterial.get(material);
    }

    public static ItemStack create(AscensionMaterial material) {
        ItemHandler item = getHandler(material);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item material: " + material);
        }

        ItemStack created = item.create();
        ItemMeta createdMeta = created.getItemMeta();

        PersistentDataContainer container = createdMeta.getPersistentDataContainer();
        container.set(AscensionParams.MATERIAL_KEY, PersistentDataType.STRING, material.name());

        created.setItemMeta(createdMeta);
        return created;
    }
}
