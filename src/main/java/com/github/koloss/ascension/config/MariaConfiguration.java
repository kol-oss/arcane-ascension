package com.github.koloss.ascension.config;

import org.bukkit.configuration.file.FileConfiguration;

public class MariaConfiguration extends DatabaseConfiguration {
    public MariaConfiguration(FileConfiguration config) {
        super(config);
    }

    public String getConnectionString() {
        return "jdbc:mariadb://" + host + ":" + port + "/" + name;
    }

    @Override
    public String getDriverClassName() {
        return "org.mariadb.jdbc.Driver";
    }
}
