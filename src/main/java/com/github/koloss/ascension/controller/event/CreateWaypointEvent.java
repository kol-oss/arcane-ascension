package com.github.koloss.ascension.controller.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class CreateWaypointEvent extends BaseEvent {
    private Player player;
    private String name;
}
