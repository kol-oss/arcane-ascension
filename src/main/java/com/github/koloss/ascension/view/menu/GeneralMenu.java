package com.github.koloss.ascension.view.menu;

import com.github.koloss.ascension.event.BaseEvent;
import com.github.koloss.ascension.event.DisplayProgressMenuEvent;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.view.menu.icons.IconsFactory;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GeneralMenu implements Menu {
    private static final String MENU_NAME = "Tome of Ascension";

    private static final int MENU_WIDTH = 9;
    private static final int MENU_HEIGHT = 5;

    @Override
    public ChestGui create() {
        return new ChestGui(MENU_HEIGHT, MENU_NAME);
    }

    private Pane getTopPane() {
        StaticPane topPane = new StaticPane(0, 1, MENU_WIDTH, 1);
        ItemStack spellsIcon = IconsFactory.createSpellsListIcon();

        topPane.addItem(new GuiItem(spellsIcon), MENU_WIDTH / 2, 0);
        return topPane;
    }

    private Pane getCenterPane() {
        StaticPane centerPane = new StaticPane(0, 2, MENU_WIDTH, 1);

        int index = 3;
        for (DivineAspect aspect : DivineAspect.values()) {
            ItemStack itemStack = IconsFactory.createAspectIcon(aspect, null);

            centerPane.addItem(new GuiItem(itemStack, event -> {
                Player player = (Player) event.getWhoClicked();
                BaseEvent displayEvent = new DisplayProgressMenuEvent(player, aspect);

                Bukkit.getPluginManager().callEvent(displayEvent);
            }), index++, 0);
        }

        return centerPane;
    }

    private Pane getBottomPane() {
        StaticPane bottomPane = new StaticPane(0, 3, MENU_WIDTH, 1);
        ItemStack villagesIcon = IconsFactory.createVillagesListIcon();

        bottomPane.addItem(new GuiItem(villagesIcon), MENU_WIDTH / 2, 0);
        return bottomPane;
    }

    @Override
    public List<Pane> getContent(ChestGui gui) {
        // Spells
        Pane topPane = getTopPane();

        // Progress in Aspects
        Pane centerPane = getCenterPane();

        // Villages
        Pane bottomPane = getBottomPane();

        return List.of(topPane, centerPane, bottomPane);
    }
}
