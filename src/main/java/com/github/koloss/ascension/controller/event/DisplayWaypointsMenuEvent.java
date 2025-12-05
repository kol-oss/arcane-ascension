package com.github.koloss.ascension.controller.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class DisplayWaypointsMenuEvent extends BaseEvent {
    private Player player;
}
