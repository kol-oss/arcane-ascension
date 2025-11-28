package com.github.koloss.ascension.service.impl;

import com.github.koloss.ascension.common.AscensionMaterial;
import com.github.koloss.ascension.items.ItemFactory;
import com.github.koloss.ascension.items.ItemHandler;
import com.github.koloss.ascension.common.AscensionParams;
import com.github.koloss.ascension.utils.InventoryService;
import com.github.koloss.ascension.service.ItemService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private InventoryService inventoryService;

    private void giveTome(Player player) {
        Inventory inventory = player.getInventory();
        AscensionMaterial material = AscensionMaterial.TOME_OF_ASCENSION;

        boolean contains = inventoryService.contains(inventory, material);
        if (contains) {
            return;
        }

        ItemStack tome = ItemFactory.create(material);
        inventory.addItem(tome);
    }

    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            return;
        }

        giveTome(player);
    }

    public void onInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null || !itemStack.hasItemMeta())
            return;

        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        String materialName = container.get(AscensionParams.MATERIAL_KEY, PersistentDataType.STRING);
        if (materialName == null)
            return;

        AscensionMaterial material = AscensionMaterial.valueOf(materialName);

        event.setCancelled(true);
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemHandler item = ItemFactory.getHandler(material);
            item.onRightClick(event);
        }
    }

    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        giveTome(player);
    }
}
