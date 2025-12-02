package com.github.koloss.ascension.view.sidebar.manager;

import com.github.koloss.ascension.view.sidebar.Sidebar;
import fr.mrmicky.fastboard.FastBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

@Getter
@AllArgsConstructor
public class RefreshTimer extends BukkitRunnable {
    private Sidebar builder;
    private FastBoard board;

    @Override
    public void run() {
        String title = builder.getTitle();
        List<String> lines = builder.getLines();

        board.updateTitle(title);
        board.updateLines(lines);
    }
}
