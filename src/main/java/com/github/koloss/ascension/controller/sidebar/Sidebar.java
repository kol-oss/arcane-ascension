package com.github.koloss.ascension.controller.sidebar;

import org.bukkit.entity.Player;

import java.util.List;

public interface Sidebar {
    Player getPlayer();

    String getTitle();

    List<String> getLines();
}
