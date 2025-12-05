package com.github.koloss.ascension.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Builder
public class Waypoint {
    private UUID id;
    private UUID userId;
    private String name;
    private double x;
    private double y;
    private double z;

    @EqualsAndHashCode.Exclude
    private long teleportAt;
}
