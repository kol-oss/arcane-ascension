package com.github.koloss.ascension.controller.event;

import com.github.koloss.ascension.model.SkillType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class DisplayProgressMenuEvent extends BaseEvent {
    private Player player;
    private SkillType skillType;
}
