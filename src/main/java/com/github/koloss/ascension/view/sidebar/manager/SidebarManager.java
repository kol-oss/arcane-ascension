package com.github.koloss.ascension.view.sidebar.manager;

import com.github.koloss.ascension.view.sidebar.Sidebar;
import fr.mrmicky.fastboard.FastBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SidebarManager {
    private static final Map<UUID, BoardData> boardByUserId = new ConcurrentHashMap<>();
    private static SidebarManager manager;

    private final Plugin plugin;

    private SidebarManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public static SidebarManager of(Plugin plugin) {
        if (manager == null) {
            manager = new SidebarManager(plugin);
        }

        return manager;
    }

    public void display(Sidebar sidebar, Long refreshInterval) {
        Player player = sidebar.getPlayer();
        UUID userId = player.getUniqueId();
        if (boardByUserId.containsKey(userId)) {
            close(player);
        }

        String title = sidebar.getTitle();
        List<String> lines = sidebar.getLines();

        FastBoard board = new FastBoard(player);
        board.updateTitle(title);
        board.updateLines(lines);

        RefreshTimer refreshTimer = null;
        if (refreshInterval != null) {
            refreshTimer = new RefreshTimer(sidebar, board);
            refreshTimer.runTaskTimer(plugin, 0L, refreshInterval);
        }

        boardByUserId.put(userId, new BoardData(board, refreshTimer));
    }

    public void close(Player player) {
        UUID userId = player.getUniqueId();
        BoardData boardData = boardByUserId.remove(userId);

        if (boardData == null)
            return;

        boardData.getBoard().delete();
        RefreshTimer refreshTimer = boardData.getTimer();
        if (refreshTimer != null) {
            refreshTimer.cancel();
        }
    }

    @AllArgsConstructor
    @Getter
    private static class BoardData {
        private FastBoard board;
        private RefreshTimer timer;
    }
}
