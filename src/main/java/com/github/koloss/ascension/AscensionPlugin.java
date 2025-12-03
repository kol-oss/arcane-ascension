package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.controller.GeneralListener;
import com.github.koloss.ascension.controller.ItemListener;
import com.github.koloss.ascension.controller.SkillListener;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.controller.modifier.ModifierManager;
import com.github.koloss.ascension.controller.modifier.impl.ModifierManagerImpl;
import com.github.koloss.ascension.controller.sidebar.manager.SidebarManager;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.items.ItemManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.mapper.impl.SkillMapperImpl;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.repository.SkillRepository;
import com.github.koloss.ascension.repository.impl.SkillRepositoryImpl;
import com.github.koloss.ascension.service.ItemService;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.service.impl.ItemServiceImpl;
import com.github.koloss.ascension.service.impl.SkillServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AscensionPlugin extends JavaPlugin {
    private static final String DB_CONFIG_FILE = "database.yml";

    private final MenuManager menuManager = MenuManager.of(this);
    private final SidebarManager sidebarManager = SidebarManager.of(this);

    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        this.databaseManager = createDatabaseManager();

        // Item listener
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(createItemListener(), this);

        // Skill listener
        Listener skillListener = createSkillListener();
        manager.registerEvents(skillListener, this);

        // General listener
        GeneralListener generalListener = new GeneralListener(menuManager);
        manager.registerEvents(generalListener, this);
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

    private Listener createSkillListener() {
        ModelMapper<Skill> skillMapper = new SkillMapperImpl();

        SkillRepository skillRepository = new SkillRepositoryImpl(databaseManager, skillMapper);
        SkillService skillService = new SkillServiceImpl(skillRepository, this);

        ModifierManager modifierManager = new ModifierManagerImpl(skillService);
        return new SkillListener(skillService, menuManager, sidebarManager, modifierManager);
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
