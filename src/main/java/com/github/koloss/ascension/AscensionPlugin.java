package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.listener.ItemListener;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.mapper.impl.ReputationMapperImpl;
import com.github.koloss.ascension.mapper.impl.SpellMapperImpl;
import com.github.koloss.ascension.model.Reputation;
import com.github.koloss.ascension.model.Spell;
import com.github.koloss.ascension.repository.ReputationRepository;
import com.github.koloss.ascension.repository.SpellRepository;
import com.github.koloss.ascension.repository.impl.ReputationRepositoryImpl;
import com.github.koloss.ascension.repository.impl.SpellRepositoryImpl;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.service.ReputationService;
import com.github.koloss.ascension.service.SpellService;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import com.github.koloss.ascension.service.impl.ReputationServiceImpl;
import com.github.koloss.ascension.service.impl.SpellServiceImpl;
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

    private SpellService spellService;
    private ReputationService reputationService;

    @Override
    public void onEnable() {
        createDatabaseManager();
        createServices();

        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(createItemListener(), this);
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

    private void createServices() {
        ModelMapper<Spell> spellMapper = new SpellMapperImpl();
        SpellRepository spellRepository = new SpellRepositoryImpl(databaseManager, spellMapper);
        this.spellService = new SpellServiceImpl(spellRepository, this);

        ModelMapper<Reputation> reputationMapper = new ReputationMapperImpl();
        ReputationRepository reputationRepository = new ReputationRepositoryImpl(databaseManager, reputationMapper);
        this.reputationService = new ReputationServiceImpl(reputationRepository, this);
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
