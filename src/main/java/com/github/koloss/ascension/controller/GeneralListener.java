package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.controller.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.controller.menu.GeneralMenu;
import com.github.koloss.ascension.controller.menu.Menu;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.service.SkillService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class GeneralListener implements Listener {
    private MenuManager menuManager;
    private SkillService skillService;

    @EventHandler
    public void onDisplayGeneralMenu(DisplayGeneralMenuEvent event) {
        Player player = event.getPlayer();

        Menu menu = new GeneralMenu(skillService, player);
        menuManager.display(menu, player);
    }
}
