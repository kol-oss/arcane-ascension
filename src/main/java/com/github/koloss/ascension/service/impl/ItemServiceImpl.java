package com.github.koloss.ascension.service.impl;

import com.github.koloss.ascension.model.AscensionMaterial;
import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.items.ItemHandler;
import com.github.koloss.ascension.items.ItemManager;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.utils.InventoryUtils;
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
import org.bukkit.plugin.Plugin;

public class ItemServiceImpl extends BaseService implements ItemService {
    private final ItemManager itemManager;

    public ItemServiceImpl(Plugin plugin, ItemManager itemManager) {
        super(plugin);

        this.itemManager = itemManager;
    }

    private void giveTome(Player player) {
        Inventory inventory = player.getInventory();
        AscensionMaterial material = AscensionMaterial.TOME_OF_ASCENSION;

        boolean contains = InventoryUtils.contains(inventory, material);
        if (contains) {
            return;
        }

        ItemStack tome = itemManager.create(material);
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

        String materialName = container.get(KeyConstants.MATERIAL_KEY, PersistentDataType.STRING);
        if (materialName == null)
            return;

        AscensionMaterial material = AscensionMaterial.valueOf(materialName);

        event.setCancelled(true);
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            ItemHandler item = itemManager.getHandler(material);
            item.onRightClick(event);
        }
    }

    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        giveTome(player);
    }
}
