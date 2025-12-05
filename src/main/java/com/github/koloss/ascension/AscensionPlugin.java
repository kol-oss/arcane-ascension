package com.github.koloss.ascension;

import com.github.koloss.ascension.config.ConfigurationFactory;
import com.github.koloss.ascension.config.DatabaseConfiguration;
import com.github.koloss.ascension.controller.GeneralListener;
import com.github.koloss.ascension.controller.ItemListener;
import com.github.koloss.ascension.controller.ProgressListener;
import com.github.koloss.ascension.controller.SkillListener;
import com.github.koloss.ascension.controller.command.HelpCommand;
import com.github.koloss.ascension.controller.command.SkillCommand;
import com.github.koloss.ascension.controller.command.SkillsCommand;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.controller.modifier.manager.ModifierManager;
import com.github.koloss.ascension.controller.particle.ParticleManager;
import com.github.koloss.ascension.controller.sidebar.manager.SidebarManager;
import com.github.koloss.ascension.database.DatabaseManager;
import com.github.koloss.ascension.database.impl.DatabaseManagerImpl;
import com.github.koloss.ascension.controller.items.ItemManager;
import com.github.koloss.ascension.mapper.ModelMapper;
import com.github.koloss.ascension.mapper.impl.SkillMapperImpl;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.repository.SkillRepository;
import com.github.koloss.ascension.repository.impl.SkillRepositoryImpl;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.service.impl.SkillServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class AscensionPlugin extends JavaPlugin {
    private static final String DB_CONFIG_FILE = "database.yml";

    private final MenuManager menuManager = MenuManager.of(this);
    private final SidebarManager sidebarManager = SidebarManager.of(this);
    private final ParticleManager particleManager = ParticleManager.of(this);

    private DatabaseManager databaseManager;

    private SkillService skillService;

    @Override
    public void onEnable() {
        this.databaseManager = createDatabaseManager();

        // Item listener
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(createItemListener(), this);

        // Skill listener
        Listener skillListener = createSkillListener();
        manager.registerEvents(skillListener, this);

        // Progress listener
        Listener progressListener = new ProgressListener(skillService);
        manager.registerEvents(progressListener, this);

        // General listener
        GeneralListener generalListener = new GeneralListener(menuManager, skillService);
        manager.registerEvents(generalListener, this);

        Objects.requireNonNull(getCommand("help")).setExecutor(new HelpCommand());
        Objects.requireNonNull(getCommand("skills")).setExecutor(new SkillsCommand(skillService));
        Objects.requireNonNull(getCommand("skill")).setExecutor(new SkillCommand(skillService));
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
        this.skillService = new SkillServiceImpl(skillRepository, this);

        ModifierManager modifierManager = ModifierManager.of(skillService);
        return new SkillListener(skillService, menuManager, sidebarManager, modifierManager, particleManager);
    }

    private Listener createItemListener() {
        ItemManager itemManager = ItemManager.of(this);

        return new ItemListener(itemManager);
    }

    @Override
    public void onDisable() {
        if (databaseManager != null) {
            databaseManager.stop();
        }
    }
}
