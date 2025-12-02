package com.github.koloss.ascension.view.menu.manager;

import com.github.koloss.ascension.view.menu.Menu;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MenuManager {
    private static final Material BACKGROUND_MATERIAL = Material.GRAY_STAINED_GLASS_PANE;
    private static MenuManager manager;

    public static MenuManager of(Plugin plugin) {
        if (manager == null) {
            manager = new MenuManager();
        }

        return manager;
    }

    private Pane createBackground(String title, int rows) {
        OutlinePane background = new OutlinePane(0, 0, 9, rows);

        ItemStack glass = new ItemStack(BACKGROUND_MATERIAL);
        ItemMeta meta = glass.getItemMeta();
        meta.displayName(Component.text("§c" + title));
        glass.setItemMeta(meta);

        background.addItem(new GuiItem(glass, _ -> {
        }));
        background.setRepeat(true);
        background.setPriority(Pane.Priority.LOW);

        return background;
    }

    public void display(Menu menu, Player player) {
        ChestGui gui = menu.create();
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        Pane background = createBackground(gui.getTitle(), gui.getRows());
        gui.addPane(background);

        List<Pane> content = menu.getContent(gui);
        for (Pane pane : content) {
            gui.addPane(pane);
        }

        gui.show(player);
    }
}
