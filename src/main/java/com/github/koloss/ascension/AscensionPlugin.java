package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.listener.ItemListener;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import com.github.koloss.ascension.utils.InventoryService;
import com.github.koloss.ascension.utils.impl.InventoryServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AscensionPlugin extends JavaPlugin {
    private static final String DB_CONFIG_FILE = "database.yml";

    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        this.databaseManager = createDatabaseManager();

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(createItemListener(), this);
    }

    private DatabaseManager createDatabaseManager() {
        File file = new File(getDataFolder(), DB_CONFIG_FILE);
        if (!file.exists()) {
            saveResource(DB_CONFIG_FILE, false);
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(file);

        DatabaseConfiguration config = ConfigurationFactory.getDatabaseConfiguration(yamlConfig);
        DatabaseManager manager = new DatabaseManagerImpl(config);
        manager.migrate(this.getClassLoader());

        return manager;
    }

    private Listener createItemListener() {
        InventoryService inventoryService = new InventoryServiceImpl();
        ItemService itemService = new ItemServiceImpl(inventoryService);

        return new ItemListener(itemService);
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.stop();
        }
    }
}
