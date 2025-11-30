package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.listener.FaithListener;
import com.github.koloss.ascension.listener.ItemListener;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.mapper.impl.FaithMapperImpl;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.repository.FaithRepository;
import com.github.koloss.ascension.repository.impl.FaithRepositoryImpl;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.service.impl.FaithServiceImpl;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import com.github.koloss.ascension.sidebar.FaithSidebar;
import com.github.koloss.ascension.utils.InventoryService;
import com.github.koloss.ascension.utils.LevelService;
import com.github.koloss.ascension.utils.impl.InventoryServiceImpl;
import com.github.koloss.ascension.utils.impl.LevelServiceImpl;
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
        createDatabaseManager();

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(createItemListener(), this);

        createFaithHandlers(manager);
    }

    private void createDatabaseManager() {
        File file = new File(getDataFolder(), DB_CONFIG_FILE);
        if (!file.exists()) {
            saveResource(DB_CONFIG_FILE, false);
        }

        YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(file);

        DatabaseConfiguration config = ConfigurationFactory.getDatabaseConfiguration(yamlConfig);
        DatabaseManager manager = new DatabaseManagerImpl(config);
        manager.migrate(this.getClassLoader());

        this.databaseManager = manager;
    }

    private void createFaithHandlers(PluginManager manager) {
        LevelService levelService = new LevelServiceImpl();
        ModelMapper<Faith> faithMapper = new FaithMapperImpl();

        FaithRepository faithRepository = new FaithRepositoryImpl(databaseManager, faithMapper);
        FaithService faithService = new FaithServiceImpl(faithRepository, this, levelService);
        FaithSidebar faithSidebar = new FaithSidebar(this, faithService);

        manager.registerEvents(new FaithListener(faithService, faithSidebar), this);
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
