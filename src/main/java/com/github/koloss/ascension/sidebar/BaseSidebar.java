package com.github.koloss.ascension.sidebar;

import fr.mrmicky.fastboard.FastBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public abstract class BaseSidebar {
    protected static final Map<UUID, SidebarData> boards = new ConcurrentHashMap<>();

    protected Plugin plugin;

    protected void display(FastBoard board, BukkitRunnable runnable, long refreshInterval) {
        Player player = board.getPlayer();
        UUID userId = player.getUniqueId();

        if (boards.containsKey(userId))
            close(player);

        boards.put(userId, new SidebarData(board, runnable));
        if (runnable != null)
            runnable.runTaskTimer(plugin, 0L, refreshInterval);
    }

    public void close(Player player) {
        UUID userId = player.getUniqueId();
        SidebarData data = boards.remove(userId);

        if (data == null)
            return;

        if (data.getBoard() != null)
            data.getBoard().delete();

        if (data.getRunnable() != null)
            data.getRunnable().cancel();
    }

    @Data
    @AllArgsConstructor
    public static class SidebarData {
        private FastBoard board;
        private BukkitRunnable runnable;
    }
}
