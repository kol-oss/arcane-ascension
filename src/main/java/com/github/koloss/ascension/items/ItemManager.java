package com.github.koloss.ascension.items;

import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.items.impl.TomeOfAscension;
import com.github.koloss.ascension.model.AscensionMaterial;
import com.github.koloss.ascension.utils.InventoryUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
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

    public ItemStack getHelpItem(Player player) {
        Inventory inventory = player.getInventory();
        AscensionMaterial material = AscensionMaterial.TOME_OF_ASCENSION;

        boolean contains = InventoryUtils.contains(inventory, material);
        if (contains) {
            return null;
        }

        return create(material);
    }

    public ItemStack create(AscensionMaterial material) {
        ItemHandler item = itemByMaterial.get(material);
        if (item == null) {
            throw new IllegalArgumentException("Invalid item material: " + material);
        }

        ItemStack created = item.create();
        ItemMeta createdMeta = created.getItemMeta();

        PersistentDataContainer container = createdMeta.getPersistentDataContainer();
        container.set(KeyConstants.MATERIAL_KEY, PersistentDataType.STRING, material.name());

        created.setItemMeta(createdMeta);
        return created;
    }

    public void process(PlayerInteractEvent event, ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        String materialName = container.get(KeyConstants.MATERIAL_KEY, PersistentDataType.STRING);
        if (materialName == null)
            return;

        AscensionMaterial material = AscensionMaterial.valueOf(materialName);

        ItemHandler item = itemByMaterial.get(material);
        if (item != null)
            item.onRightClick(event);
    }
}
