package com.github.koloss.ascension.controller.particle;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticleData {
    private double radius;
    private double startHeight;
    private double endHeight;
    private double speed;
    private int points;
    private float size;
}
