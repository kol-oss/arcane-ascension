package com.github.koloss.ascension.service.impl;

import com.github.koloss.ascension.repository.base.DataRepository;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CachedService<T, K> {
    protected final Map<K, T> cacheById = new ConcurrentHashMap<>();
    protected final Map<UUID, List<T>> cacheByUserId = new ConcurrentHashMap<>();
    protected final Set<K> updateCache = ConcurrentHashMap.newKeySet();

    private final int startTimeout;
    private final int refreshTimeout;

    public CachedService(int startTimeout, int updateTimeout) {
        this.startTimeout = startTimeout;
        this.refreshTimeout = updateTimeout;
    }

    protected abstract void load();

    protected void refreshCache(K id, UUID userId, T value) {
        cacheById.put(id, value);

        cacheByUserId
                .computeIfAbsent(userId, _ -> new ArrayList<>())
                .add(value);
    }

    protected void start(JavaPlugin plugin, DataRepository<T, K> repository) {
        try {
            load();
        } catch (Exception ex) {
            plugin.getLogger().warning("Failed to load cache for service " + this.getClass().getSimpleName() + ": " + ex.getMessage());
        }

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            // Uploading cache to database
            if (updateCache.isEmpty()) return;

            Set<K> updated = new HashSet<>(updateCache);
            updateCache.clear();

            for (K id : updated) {
                T spell = cacheById.get(id);
                if (spell != null) {
                    repository.update(id, spell);
                }
            }

            // Refreshing cache
            cacheById.clear();
            cacheByUserId.clear();
            load();
        }, 20L * startTimeout, 20L * refreshTimeout);
    }
}
