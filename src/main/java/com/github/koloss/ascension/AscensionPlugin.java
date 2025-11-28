package com.github.koloss.ascension;

import com.github.koloss.ascension.listener.ItemListener;
import com.github.koloss.ascension.utils.InventoryService;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.utils.impl.InventoryServiceImpl;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class AscensionPlugin extends JavaPlugin {
    private Listener createItemListener() {
        InventoryService inventoryService = new InventoryServiceImpl();
        ItemService itemService = new ItemServiceImpl(inventoryService);

        return new ItemListener(itemService);
    }

    @Override
    public void onEnable() {
        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(createItemListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
