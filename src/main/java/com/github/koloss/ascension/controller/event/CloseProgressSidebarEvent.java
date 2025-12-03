package com.github.koloss.ascension.controller.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class CloseProgressSidebarEvent extends BaseEvent {
    private Player player;
}
