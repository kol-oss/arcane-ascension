package com.github.koloss.ascension.controller.event;

import com.github.koloss.ascension.model.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class WaypointClickEvent extends BaseEvent {
    private Player player;
    private Waypoint waypoint;
}
