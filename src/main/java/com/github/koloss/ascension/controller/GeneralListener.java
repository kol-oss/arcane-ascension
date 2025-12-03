package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.controller.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.controller.menu.GeneralMenu;
import com.github.koloss.ascension.controller.menu.Menu;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class GeneralListener implements Listener {
    private MenuManager menuManager;

    @EventHandler
    public void onDisplayGeneralMenu(DisplayGeneralMenuEvent event) {
        Player player = event.getPlayer();

        Menu menu = new GeneralMenu();
        menuManager.display(menu, player);
    }
}
