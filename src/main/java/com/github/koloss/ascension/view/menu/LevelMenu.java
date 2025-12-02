package com.github.koloss.ascension.view.menu;

import com.github.koloss.ascension.common.AscensionParams;
import com.github.koloss.ascension.common.AspectParams;
import com.github.koloss.ascension.common.LevelParams;
import com.github.koloss.ascension.common.MenuParams;
import com.github.koloss.ascension.event.CloseProgressSidebarEvent;
import com.github.koloss.ascension.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.event.DisplayProgressSidebarEvent;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.service.FaithService;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.view.menu.icons.IconsFactory;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.github.stefvanschie.inventoryframework.pane.util.Slot;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class LevelMenu implements Menu {
    private static final int MENU_WIDTH = 9;
    private static final int MENU_HEIGHT = 5;

    private FaithService faithService;
    private Player player;
    private DivineAspect aspect;

    @Override
    public ChestGui create() {
        String menuName = AspectParams.toString(aspect);
        return new ChestGui(MENU_HEIGHT, menuName);
    }

    private ItemStack createLevelIcon(int level, int userLevel, boolean isOpened) {
        Material material;
        if (isOpened) {
            material = Material.YELLOW_WOOL;
        } else if (userLevel < level)
            material = Material.WHITE_WOOL;
        else
            material = Material.GREEN_WOOL;

        return IconsFactory.createLevelIcon(aspect, material, level);
    }

    private void updateLevel(ChestGui gui, StaticPane pane, Faith faith) {
        faith.getLevel().incrementAndGet();
        faithService.update(faith);

        updateLevelsPane(gui, pane, faith);
        gui.update();
    }

    private GuiItem createLevelItem(ChestGui gui, StaticPane pane, int level, Faith faith) {
        int userLevel = faith.getLevel().get();
        boolean isOpened = LevelUtils.isLevelOpen(level, userLevel, faith.getCount().get());
        ItemStack levelItemStack = createLevelIcon(level, userLevel, isOpened);

        return new GuiItem(levelItemStack, isOpened ? _ -> updateLevel(gui, pane, faith) : null);
    }

    private void updateLevelsPane(ChestGui gui, StaticPane pane, Faith faith) {
        for (int i = LevelParams.MIN_LEVEL; i <= LevelParams.MAX_LEVEL; i++) {
            Slot levelSlot = MenuParams.getSlot(i);
            GuiItem levelItem = createLevelItem(gui, pane, i, faith);

            int x = levelSlot.getX(MENU_WIDTH);
            int y = levelSlot.getY(MENU_HEIGHT);

            pane.removeItem(x, y);
            pane.addItem(levelItem, levelSlot.getX(MENU_WIDTH), levelSlot.getY(MENU_WIDTH));
        }
    }

    private Pane getLevelsPane(ChestGui gui) {
        UUID userId = player.getUniqueId();
        Faith faith = faithService.findByUserIdAndAspect(userId, aspect);

        StaticPane levelsPane = new StaticPane(0, 0, MENU_WIDTH, MENU_HEIGHT - 1);

        long currExp = faith.getCount().get();
        ItemStack aspectItem = IconsFactory.createAspectIcon(aspect, currExp);
        levelsPane.addItem(new GuiItem(aspectItem), 0, 0);

        updateLevelsPane(gui, levelsPane, faith);
        return levelsPane;
    }

    private void updateHelpersPane(ChestGui gui, StaticPane pane) {
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        String sidebarValue = dataContainer.get(AscensionParams.SIDEBAR_KEY, PersistentDataType.STRING);

        DivineAspect sidebarAspect = sidebarValue != null ? DivineAspect.valueOf(sidebarValue) : null;
        boolean isFollowing = sidebarAspect != null && sidebarAspect == aspect;

        ItemStack followItemStack = IconsFactory.createFollowIcon(isFollowing);
        GuiItem followGuiItem = new GuiItem(followItemStack, _ -> {
            Event followEvent;
            if (isFollowing) {
                followEvent = new CloseProgressSidebarEvent(player);
                dataContainer.remove(AscensionParams.SIDEBAR_KEY);
            } else {
                followEvent = new DisplayProgressSidebarEvent(player, aspect);
                dataContainer.set(AscensionParams.SIDEBAR_KEY, PersistentDataType.STRING, aspect.name());
            }

            Bukkit.getPluginManager().callEvent(followEvent);

            updateHelpersPane(gui, pane);
            gui.update();
        });

        pane.removeItem(MENU_WIDTH / 2 - 1, 0);
        pane.addItem(followGuiItem, MENU_WIDTH / 2 - 1, 0);
    }

    private Pane getHelpersPane(ChestGui gui) {
        StaticPane helpersPane = new StaticPane(0, MENU_HEIGHT - 1, MENU_WIDTH, 1);

        ItemStack returnItemStack = IconsFactory.createReturnIcon();
        GuiItem returnGuiItem = new GuiItem(returnItemStack, _ -> {
            DisplayGeneralMenuEvent returnEvent = new DisplayGeneralMenuEvent(player);
            Bukkit.getPluginManager().callEvent(returnEvent);
        });

        helpersPane.addItem(returnGuiItem, MENU_WIDTH / 2, 0);
        updateHelpersPane(gui, helpersPane);

        return helpersPane;
    }

    @Override
    public List<Pane> getContent(ChestGui gui) {
        // Level button from 1 to 23
        Pane levelsPane = getLevelsPane(gui);

        // Follow and return buttons
        Pane helpersPane = getHelpersPane(gui);

        return List.of(levelsPane, helpersPane);
    }
}
