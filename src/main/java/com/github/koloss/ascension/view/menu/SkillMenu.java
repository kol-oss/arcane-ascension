package com.github.koloss.ascension.view.menu;

import com.github.koloss.ascension.constant.KeyConstants;
import com.github.koloss.ascension.constant.LevelConstants;
import com.github.koloss.ascension.constant.MenuConstants;
import com.github.koloss.ascension.event.CloseProgressSidebarEvent;
import com.github.koloss.ascension.event.DisplayGeneralMenuEvent;
import com.github.koloss.ascension.event.DisplayProgressSidebarEvent;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.LevelUtils;
import com.github.koloss.ascension.utils.SkillTypeUtils;
import com.github.koloss.ascension.view.icons.IconsFactory;
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
public class SkillMenu implements Menu {
    private static final int MENU_WIDTH = 9;
    private static final int MENU_HEIGHT = 5;

    private SkillService skillService;

    private Player player;
    private SkillType skillType;

    @Override
    public ChestGui create() {
        String menuName = SkillTypeUtils.toString(skillType);
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

        return IconsFactory.createLevelIcon(skillType, material, level);
    }

    private void updateLevel(ChestGui gui, StaticPane pane, Skill skill) {
        skill.getLevelCount().incrementAndGet();
        skillService.update(skill);

        updateLevelsPane(gui, pane, skill);
        gui.update();
    }

    private GuiItem createLevelItem(ChestGui gui, StaticPane pane, int level, Skill skill) {
        int userLevel = skill.getLevel();
        boolean isOpened = LevelUtils.isLevelOpen(level, userLevel, skill.getProgress());

        ItemStack levelItemStack = createLevelIcon(level, userLevel, isOpened);
        return new GuiItem(levelItemStack, isOpened ? _ -> updateLevel(gui, pane, skill) : null);
    }

    private void updateLevelsPane(ChestGui gui, StaticPane pane, Skill skill) {
        for (int i = LevelConstants.MIN_LEVEL; i <= LevelConstants.MAX_LEVEL; i++) {
            Slot levelSlot = MenuConstants.getSlot(i);
            GuiItem levelItem = createLevelItem(gui, pane, i, skill);

            int x = levelSlot.getX(MENU_WIDTH);
            int y = levelSlot.getY(MENU_HEIGHT);

            pane.removeItem(x, y);
            pane.addItem(levelItem, levelSlot.getX(MENU_WIDTH), levelSlot.getY(MENU_WIDTH));
        }
    }

    private Pane getLevelsPane(ChestGui gui) {
        UUID userId = player.getUniqueId();
        Skill skill = skillService.findByUserIdAndType(userId, skillType);

        StaticPane levelsPane = new StaticPane(0, 0, MENU_WIDTH, MENU_HEIGHT - 1);

        long currExp = skill.getProgress();
        ItemStack skillTypeItem = IconsFactory.createSkillTypeIcon(skillType, currExp);
        levelsPane.addItem(new GuiItem(skillTypeItem), 0, 0);

        updateLevelsPane(gui, levelsPane, skill);
        return levelsPane;
    }

    private void updateHelpersPane(ChestGui gui, StaticPane pane) {
        PersistentDataContainer dataContainer = player.getPersistentDataContainer();
        SkillType displayedType = SkillTypeUtils.fromContainer(dataContainer);

        boolean isFollowing = displayedType != null && displayedType == skillType;

        ItemStack followItemStack = IconsFactory.createFollowIcon(isFollowing);
        GuiItem followGuiItem = new GuiItem(followItemStack, _ -> {
            Event followEvent;
            if (isFollowing) {
                followEvent = new CloseProgressSidebarEvent(player);
                dataContainer.remove(KeyConstants.SIDEBAR_KEY);
            } else {
                followEvent = new DisplayProgressSidebarEvent(player, skillType);
                dataContainer.set(KeyConstants.SIDEBAR_KEY, PersistentDataType.STRING, skillType.name());
            }

            Bukkit.getPluginManager().callEvent(followEvent);

            updateHelpersPane(gui, pane);
            gui.update();
        });

        int x = MENU_WIDTH / 2 - 1;

        pane.removeItem(x, 0);
        pane.addItem(followGuiItem, x, 0);
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
