package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.constant.WaypointConstants;
import com.github.koloss.ascension.controller.event.CreateWaypointEvent;
import com.github.koloss.ascension.controller.event.DisplayWaypointsMenuEvent;
import com.github.koloss.ascension.controller.event.WaypointClickEvent;
import com.github.koloss.ascension.controller.menu.Menu;
import com.github.koloss.ascension.controller.menu.WaypointMenu;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.controller.particle.ParticleManager;
import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.service.WaypointService;
import lombok.AllArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

@AllArgsConstructor
public class WaypointListener implements Listener {
    private WaypointService waypointService;
    private SkillService skillService;

    private Plugin plugin;
    private MenuManager menuManager;
    private ParticleManager particleManager;

    @EventHandler
    public void onDisplayMenu(DisplayWaypointsMenuEvent event) {
        Player player = event.getPlayer();

        Menu menu = new WaypointMenu(skillService, waypointService, plugin, player);
        menuManager.display(menu, player);
    }

    @EventHandler
    public void onWaypointClick(WaypointClickEvent event) {
        Player player = event.getPlayer();
        Waypoint waypoint = event.getWaypoint();

        long currentTime = System.currentTimeMillis();
        long lastTeleport = waypoint.getTeleportAt();

        long timeDiff = currentTime - lastTeleport;
        if (lastTeleport != Long.MIN_VALUE && timeDiff < WaypointConstants.TELEPORT_COOLDOWN) {
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

    @EventHandler
    public void onCreateWaypoint(CreateWaypointEvent event) {
        Player player = event.getPlayer();
        String name = event.getName();

        // Save waypoint
        UUID userId = player.getUniqueId();
        Location location = player.getLocation();

        waypointService.create(userId, name, location);

        // Create waypoint name display
        TextDisplay text = (TextDisplay) player.getWorld().spawnEntity(location.add(0, 2, 0), EntityType.TEXT_DISPLAY);
        text.text(Component.text(name, NamedTextColor.GOLD)
                .append(Component.text("\n"))
                .append(Component.text("Owner: " + player.getName(), NamedTextColor.GRAY))
        );

        text.setBillboard(Display.Billboard.CENTER);

        // Output particles
        particleManager.displayRing(player, 2, NamedTextColor.GOLD);

        // Output waypoint creation in chat
        String locationString = "(x=" + location.getBlockX() + ", y=" + location.getBlockY() + ", z=" + location.getBlockZ() + ")";
        player.sendMessage(Component
                .text("Waypoint " + name + " created at", NamedTextColor.GRAY)
                .appendSpace()
                .append(Component.text(locationString, NamedTextColor.LIGHT_PURPLE))
        );
    }
}
