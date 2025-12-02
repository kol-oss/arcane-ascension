package com.github.koloss.ascension.listener;

import com.github.koloss.ascension.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.view.menu.GeneralMenu;
import com.github.koloss.ascension.view.menu.Menu;
import com.github.koloss.ascension.view.menu.manager.MenuManager;
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
