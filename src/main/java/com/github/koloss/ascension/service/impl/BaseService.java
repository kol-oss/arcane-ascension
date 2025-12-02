package com.github.koloss.ascension.service.impl;

import org.bukkit.plugin.Plugin;

public abstract class BaseService {
    protected Plugin plugin;

    public BaseService(Plugin plugin) {
        this.plugin = plugin;
    }
}
