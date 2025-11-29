package com.github.koloss.ascension.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

@Getter
public abstract class DatabaseConfiguration {
    public static final String PREFIX = "database";

    protected final String host;
    protected final int port;
    protected final String name;

    protected final String username;
    protected final String password;

    protected final String changelogPath;
    protected final int maxPoolSize;
    protected final long connectionTimeout;

    public DatabaseConfiguration(FileConfiguration config) {
        this.host = config.getString(PREFIX + ".host", "localhost");
        this.port = config.getInt(PREFIX + ".port", 3306);

        this.name = config.getString(PREFIX + ".name");
        this.username = config.getString(PREFIX + ".username");
        this.password = config.getString(PREFIX + ".password");

        this.changelogPath = config.getString(PREFIX + ".changelog.path", "db/changelog.yml");
        this.maxPoolSize = config.getInt(PREFIX + ".maxPoolSize", 12);
        this.connectionTimeout = config.getLong(PREFIX + ".connectionTimeout", 30000L);
    }

    public abstract String getConnectionString();

    public abstract String getDriverClassName();
}
