package com.github.koloss.ascension.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class DisplayGeneralMenuEvent extends BaseEvent {
    private Player player;
}
