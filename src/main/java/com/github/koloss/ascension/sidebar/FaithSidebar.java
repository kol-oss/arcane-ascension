package com.github.koloss.ascension.sidebar;

import com.github.koloss.ascension.common.AspectParams;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.service.FaithService;
import fr.mrmicky.fastboard.FastBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class FaithSidebar extends BaseSidebar {
    private static final long UPDATE_INTERVAL = 20L;

    private final FaithService faithService;

    public FaithSidebar(Plugin plugin, FaithService faithService) {
        super(plugin);

        this.faithService = faithService;
    }

    public void display(Player player, DivineAspect aspect) {
        UUID userId = player.getUniqueId();
        Faith faith = faithService.findByUserIdAndAspect(userId, aspect);

        FastBoard board = new FastBoard(player);
        updateBoard(board, faith);

        UpdateTimer updateTimer = new UpdateTimer(player, aspect);
        super.display(board, updateTimer, UPDATE_INTERVAL);
    }

    private void updateBoard(FastBoard board, Faith faith) {
        DivineAspect aspect = faith.getAspect();
        LegacyComponentSerializer serializer = LegacyComponentSerializer.legacySection();

        NamedTextColor titleColour = AspectParams.getTextColor(aspect);
        Component titleComponent = Component.text(aspect.name())
                .color(titleColour)
                .decorate(TextDecoration.BOLD);

        String title = serializer.serialize(titleComponent);
        board.updateTitle(title);

        boolean hasNextLevel = faithService.hasOpenedNextLevel(faith.getUserId(), faith.getAspect());
        long reputation = faith.getCount().get();
        int level = faith.getLevel().get();

        board.updateLines(
                "§7=============",
                "§6Reputation: §b" + reputation,
                "§6Level: §b" + level + (hasNextLevel ? " §e(!)" : ""),
                "§7============="
        );
    }

    @Getter
    @AllArgsConstructor
    private class UpdateTimer extends BukkitRunnable {
        private Player player;
        private DivineAspect aspect;

        @Override
        public void run() {
            UUID userId = player.getUniqueId();
            if (!player.isOnline() || !boards.containsKey(userId)) {
                cancel();
                return;
            }

            Faith faith = faithService.findByUserIdAndAspect(userId, aspect);

            FastBoard board = boards.get(userId).getBoard();
            updateBoard(board, faith);
        }
    }
}
