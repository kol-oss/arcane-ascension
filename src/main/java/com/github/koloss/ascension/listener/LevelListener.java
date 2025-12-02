package com.github.koloss.ascension.listener;

import com.github.koloss.ascension.common.AscensionParams;
import com.github.koloss.ascension.event.CloseProgressSidebarEvent;
import com.github.koloss.ascension.event.DisplayProgressMenuEvent;
import com.github.koloss.ascension.event.DisplayProgressSidebarEvent;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.view.menu.LevelMenu;
import com.github.koloss.ascension.view.menu.Menu;
import com.github.koloss.ascension.view.menu.manager.MenuManager;
import com.github.koloss.ascension.view.sidebar.Sidebar;
import com.github.koloss.ascension.view.sidebar.impl.ProgressSidebar;
import com.github.koloss.ascension.view.sidebar.manager.SidebarManager;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor
public class LevelListener implements Listener {
    private static final long REFRESH_SIDEBAR_INTERVAL = 10L;

    private SidebarManager sidebarManager;
    private MenuManager menuManager;
    private FaithService faithService;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();

        String activeSidebar = dataContainer.get(AscensionParams.SIDEBAR_KEY, PersistentDataType.STRING);
        DivineAspect aspect = activeSidebar != null ? DivineAspect.valueOf(activeSidebar) : null;

        if (aspect != null) {
            Sidebar sidebar = new ProgressSidebar(faithService, player, aspect);
            sidebarManager.display(sidebar, REFRESH_SIDEBAR_INTERVAL);
        }
    }

    @EventHandler
    public void onDisplayProgressSidebar(DisplayProgressSidebarEvent event) {
        Player player = event.getPlayer();
        DivineAspect aspect = event.getAspect();

        Sidebar sidebar = new ProgressSidebar(faithService, player, aspect);
        sidebarManager.display(sidebar, REFRESH_SIDEBAR_INTERVAL);
    }

    @EventHandler
    public void onDisplayProgressMenu(DisplayProgressMenuEvent event) {
        Player player = event.getPlayer();
        DivineAspect aspect = event.getAspect();

        Menu menu = new LevelMenu(faithService, player, aspect);
        menuManager.display(menu, player);
    }

    @EventHandler
    public void onCloseProgressSidebar(CloseProgressSidebarEvent event) {
        Player player = event.getPlayer();
        sidebarManager.close(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        sidebarManager.close(player);
    }
}
