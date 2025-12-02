package com.github.koloss.ascension.items;

import com.github.koloss.ascension.common.AscensionMaterial;
import com.github.koloss.ascension.common.AscensionParams;
import com.github.koloss.ascension.items.impl.TomeOfAscension;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    private static final Map<AscensionMaterial, ItemHandler> itemByMaterial = new HashMap<>();
    private static ItemManager manager;

    private final Plugin plugin;

    private ItemManager(Plugin plugin) {
        this.plugin = plugin;

        itemByMaterial.put(AscensionMaterial.TOME_OF_ASCENSION, new TomeOfAscension(plugin));
    }

    public static ItemManager of(Plugin plugin) {
        if (manager == null) {
            manager = new ItemManager(plugin);
        }

        return manager;
    }

    public ItemHandler getHandler(AscensionMaterial material) {
        return itemByMaterial.get(material);
    }

    public ItemStack create(AscensionMaterial material) {
        ItemHandler item = itemByMaterial.get(material);
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
