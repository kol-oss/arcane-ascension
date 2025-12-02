package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.items.ItemManager;
import com.github.koloss.ascension.listener.FaithListener;
import com.github.koloss.ascension.listener.GeneralListener;
import com.github.koloss.ascension.listener.ItemListener;
import com.github.koloss.ascension.listener.LevelListener;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.mapper.impl.FaithMapperImpl;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.repository.FaithRepository;
import com.github.koloss.ascension.repository.impl.FaithRepositoryImpl;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.service.impl.FaithServiceImpl;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import com.github.koloss.ascension.view.menu.manager.MenuManager;
import com.github.koloss.ascension.view.sidebar.manager.SidebarManager;
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
        ModelMapper<Faith> faithMapper = new FaithMapperImpl();

        FaithRepository faithRepository = new FaithRepositoryImpl(databaseManager, faithMapper);
        FaithService faithService = new FaithServiceImpl(faithRepository, this);

        manager.registerEvents(new FaithListener(faithService), this);

        SidebarManager sidebarManager = SidebarManager.of(this);
        MenuManager menuManager = MenuManager.of(this);

        LevelListener levelListener = new LevelListener(sidebarManager, menuManager, faithService);
        manager.registerEvents(levelListener, this);

        GeneralListener generalListener = new GeneralListener(menuManager);
        manager.registerEvents(generalListener, this);
    }

    private Listener createItemListener() {
        ItemManager itemManager = ItemManager.of(this);

        ItemService itemService = new ItemServiceImpl(this, itemManager);
        return new ItemListener(itemService);
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.stop();
        }
    }
}
