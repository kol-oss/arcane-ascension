package com.github.koloss.ascension.controller.menu;

import com.github.koloss.ascension.controller.event.BaseEvent;
import com.github.koloss.ascension.controller.event.DisplayProgressMenuEvent;
import com.github.koloss.ascension.controller.menu.icons.CommonIconFactory;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
public class GeneralMenu implements Menu {
    private static final String MENU_NAME = "Tome of Ascension";

    private static final int MENU_WIDTH = 9;
    private static final int MENU_HEIGHT = 4;

    private SkillService skillService;

    private Player player;

    @Override
    public ChestGui create() {
        return new ChestGui(MENU_HEIGHT, MENU_NAME);
    }

    private Pane getCenterPane() {
        StaticPane centerPane = new StaticPane(0, 2, MENU_WIDTH, 1);

        int index = 3;
        for (SkillType type : SkillType.values()) {
            Skill skill = skillService.findByUserIdAndType(player.getUniqueId(), type);
            ItemStack itemStack = CommonIconFactory.createSkillTypeIcon(skill);

            centerPane.addItem(new GuiItem(itemStack, event -> {
                Player player = (Player) event.getWhoClicked();
                BaseEvent displayEvent = new DisplayProgressMenuEvent(player, type);

                Bukkit.getPluginManager().callEvent(displayEvent);
            }), index++, 0);
        }

        return centerPane;
    }

    private Pane getBottomPane() {
        StaticPane bottomPane = new StaticPane(0, 3, MENU_WIDTH, 1);
        ItemStack villagesIcon = CommonIconFactory.createVillagesListIcon();

        bottomPane.addItem(new GuiItem(villagesIcon), MENU_WIDTH / 2, 0);
        return bottomPane;
    }

    @Override
    public List<Pane> getContent(ChestGui gui) {
        // Progress in Skills
        Pane centerPane = getCenterPane();

        // Villages
        Pane bottomPane = getBottomPane();

        return List.of(centerPane, bottomPane);
    }
}
