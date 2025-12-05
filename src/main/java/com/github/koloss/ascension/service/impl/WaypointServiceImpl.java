package com.github.koloss.ascension.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.repository.WaypointRepository;
import com.github.koloss.ascension.service.WaypointService;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class WaypointServiceImpl extends BaseService implements WaypointService {
    private final WaypointRepository waypointRepository;
    private final Cache<UUID, Set<Waypoint>> cacheByUserId;

    public WaypointServiceImpl(WaypointRepository waypointRepository, Plugin plugin) {
        super(plugin);

        this.waypointRepository = waypointRepository;
        this.cacheByUserId = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .removalListener((UUID _, Set<Waypoint> value, RemovalCause cause) -> {
                    if (cause == RemovalCause.EXPIRED && value != null) {
                        value.forEach(this::save);
                    }
                })
                .build();
    }

    private void refreshCache(UUID userId, Waypoint waypoint) {
        Set<Waypoint> cached = cacheByUserId
                .asMap()
                .computeIfAbsent(userId, _ -> new HashSet<>());

        cached.add(waypoint);
    }

    @Override
    public List<Waypoint> findAllByUserId(UUID userId) {
        Set<Waypoint> cached = cacheByUserId.getIfPresent(userId);
        if (cached != null) {
            return new ArrayList<>(cached);
        }

        List<Waypoint> waypoints = waypointRepository.findAllByUserId(userId);
        waypoints.forEach(waypoint -> refreshCache(userId, waypoint));

        return waypoints;
    }

    @Override
    public Waypoint create(UUID userId, String name, Location location) {
        Waypoint waypoint = Waypoint.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .name(name)
                .x(location.getBlockX())
                .y(location.getBlockY())
                .z(location.getBlockZ())
                .teleportAt(Long.MIN_VALUE)
                .build();

        refreshCache(userId, waypoint);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> waypointRepository.insert(waypoint));

        return waypoint;
    }

    @Override
    public Waypoint update(Waypoint waypoint) {
        UUID userId = waypoint.getUserId();

        refreshCache(userId, waypoint);
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> waypointRepository.update(waypoint.getId(), waypoint));

        return waypoint;
    }

    @Override
    public void save(Waypoint waypoint) {
        cacheByUserId.invalidate(waypoint.getUserId());
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> waypointRepository.update(waypoint.getId(), waypoint));
    }
}
