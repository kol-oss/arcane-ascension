package com.github.koloss.ascension.view.menu;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.Pane;

import java.util.List;

public interface Menu {
    ChestGui create();

    List<Pane> getContent(ChestGui gui);
}
