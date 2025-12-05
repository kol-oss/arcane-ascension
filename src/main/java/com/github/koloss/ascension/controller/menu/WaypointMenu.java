package com.github.koloss.ascension.controller.menu;

import com.github.koloss.ascension.constant.WaypointConstants;
import com.github.koloss.ascension.controller.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.controller.event.WaypointClickEvent;
import com.github.koloss.ascension.controller.menu.icons.CommonIconFactory;
import com.github.koloss.ascension.controller.menu.icons.WaypointIconFactory;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.Waypoint;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.service.WaypointService;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class WaypointMenu implements Menu {
    private static final String MENU_NAME = "Waypoints";

    private static final int MENU_WIDTH = 9;
    private static final int MENU_HEIGHT = 5;

    private SkillService skillService;
    private WaypointService waypointService;
    private Player player;

    @Override
    public ChestGui create() {
        return new ChestGui(MENU_HEIGHT, MENU_NAME);
    }

    private PaginatedPane getWaypointsListPane() {
        PaginatedPane waypointsPane = new PaginatedPane(0, 0, MENU_WIDTH, MENU_HEIGHT - 1);
        UUID userId = player.getUniqueId();

        List<Waypoint> waypoints = waypointService.findAllByUserId(userId);
        int maxWaypoints = WaypointConstants.MAX_WAYPOINTS;

        int totalLevel = skillService.findAllByUserId(userId)
                .stream()
                .mapToInt(Skill::getLevel)
                .sum();

        List<GuiItem> items = new ArrayList<>();
        for (int i = 0; i < maxWaypoints; i++) {
            GuiItem item;

            if (i < waypoints.size()) {
                Waypoint waypoint = waypoints.get(i);
                ItemStack itemStack = WaypointIconFactory.createUsedWaypoint(waypoint);

                item = new GuiItem(itemStack, _ -> {
                    player.closeInventory();

                    WaypointClickEvent event = new WaypointClickEvent(player, waypoint);
                    Bukkit.getPluginManager().callEvent(event);
                });
            } else {
                ItemStack itemStack;
                int index = i + 1;

                if (totalLevel >= index * WaypointConstants.LEVEL_FOR_WAYPOINT) {
                    itemStack = WaypointIconFactory.createOpenWaypoint(index, totalLevel);
                } else {
                    itemStack = WaypointIconFactory.createLockedWaypoint(index, totalLevel);
                }

                item = new GuiItem(itemStack);
            }

            items.add(item);
        }

        waypointsPane.populateWithGuiItems(items);
        return waypointsPane;
    }

    private Pane getHelpersPane(PaginatedPane pane, ChestGui gui) {
        StaticPane helpersPane = new StaticPane(0, MENU_HEIGHT - 1, MENU_WIDTH, 1);

        // Return button
        ItemStack returnItemStack = CommonIconFactory.createReturnIcon();
        GuiItem returnGuiItem = new GuiItem(returnItemStack, _ -> {
            DisplayGeneralMenuEvent returnEvent = new DisplayGeneralMenuEvent(player);
            Bukkit.getPluginManager().callEvent(returnEvent);
        });

        helpersPane.addItem(returnGuiItem, MENU_WIDTH / 2, 0);

        // Prev page button
        ItemStack prevPageIcon = CommonIconFactory.createPrevPageIcon();
        GuiItem prevPageItem = new GuiItem(prevPageIcon, _ -> {
            if (pane.getPage() > 0) {
                pane.setPage(pane.getPage() - 1);
                gui.update();
            }
        });

        // Next page button
        ItemStack nextPageIcon = CommonIconFactory.createNextPageIcon();
        GuiItem nextPageItem = new GuiItem(nextPageIcon, _ -> {
            if (pane.getPage() < pane.getPages() - 1) {
                pane.setPage(pane.getPage() + 1);
                gui.update();
            }
        });

        helpersPane.addItem(prevPageItem, MENU_WIDTH / 2 - 1, 0);
        helpersPane.addItem(nextPageItem, MENU_WIDTH / 2 + 1, 0);

        return helpersPane;
    }

    @Override
    public List<Pane> getContent(ChestGui gui) {
        // Waypoint buttons
        PaginatedPane waypointsPane = getWaypointsListPane();

        // Return button and update page buttons
        Pane helpersPane = getHelpersPane(waypointsPane, gui);

        return List.of(waypointsPane, helpersPane);
    }
}
