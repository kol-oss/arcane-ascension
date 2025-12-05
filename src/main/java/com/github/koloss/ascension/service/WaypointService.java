package com.github.koloss.ascension.service;

import com.github.koloss.ascension.model.Waypoint;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public interface WaypointService {
    List<Waypoint> findAllByUserId(UUID userId);

    Waypoint create(UUID userId, String name, Location location);

    Waypoint update(Waypoint waypoint);

    void save(Waypoint waypoint);
}
