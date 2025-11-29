package com.github.koloss.ascension.config;

import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigurationFactory {
    private static final String MARIA_PROPERTY_VALUE = "maria";

    public static DatabaseConfiguration getDatabaseConfiguration(YamlConfiguration config) {
        String value = config.getString(DatabaseConfiguration.PREFIX + ".provider");
        if (value == null) {
            throw new IllegalArgumentException("Database provider is not specified");
        }

        String provider = value.toLowerCase();
        if (provider.equals(MARIA_PROPERTY_VALUE)) {
            return new MariaConfiguration(config);
        } else {
            throw new IllegalArgumentException("Database provider " + provider + " is not supported");
        }
    }
}
