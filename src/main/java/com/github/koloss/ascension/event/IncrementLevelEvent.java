package com.github.koloss.ascension.event;

import com.github.koloss.ascension.model.DivineAspect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class IncrementLevelEvent extends BaseEvent {
    private Player player;
    private DivineAspect aspect;
}
