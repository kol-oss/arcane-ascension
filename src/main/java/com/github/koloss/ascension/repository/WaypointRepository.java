package com.github.koloss.ascension.repository;

import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.repository.base.DataRepository;

import java.util.List;
import java.util.UUID;

public interface WaypointRepository extends DataRepository<Waypoint, UUID> {
    List<Waypoint> findAllByUserId(UUID userId);
}
