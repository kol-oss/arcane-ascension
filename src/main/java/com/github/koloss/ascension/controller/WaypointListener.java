package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.constant.WaypointConstants;
import com.github.koloss.ascension.controller.event.DisplayWaypointsMenuEvent;
import com.github.koloss.ascension.controller.event.WaypointClickEvent;
import com.github.koloss.ascension.controller.menu.Menu;
import com.github.koloss.ascension.controller.menu.WaypointMenu;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.service.WaypointService;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

@AllArgsConstructor
public class WaypointListener implements Listener {
    private WaypointService waypointService;
    private SkillService skillService;

    private MenuManager menuManager;

    @EventHandler
    public void onDisplayMenu(DisplayWaypointsMenuEvent event) {
        Player player = event.getPlayer();

        Menu menu = new WaypointMenu(skillService, waypointService, player);
        menuManager.display(menu, player);
    }

    @EventHandler
    public void onWaypointClick(WaypointClickEvent event) {
        Player player = event.getPlayer();
        Waypoint waypoint = event.getWaypoint();

        long currentTime = System.currentTimeMillis();
        long lastTeleport = waypoint.getTeleportAt();

        long timeDiff = currentTime - lastTeleport;
        if (timeDiff < WaypointConstants.TELEPORT_COOLDOWN) {
            long leftSeconds = (WaypointConstants.TELEPORT_COOLDOWN - timeDiff) / 1000;

            player.sendMessage(Component.text("Can not teleport to waypoint " + waypoint.getName() + ", left " + leftSeconds + " seconds", NamedTextColor.RED));
            return;
        }

        waypoint.setTeleportAt(currentTime);
        waypointService.update(waypoint);

        Location location = new Location(player.getWorld(), waypoint.getX(), waypoint.getY(), waypoint.getZ());
        player.teleport(location);

        player.sendMessage(Component.text("Teleported to waypoint " + waypoint.getName(), NamedTextColor.GRAY));
    }
}
