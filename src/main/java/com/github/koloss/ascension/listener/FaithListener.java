package com.github.koloss.ascension.listener;

import com.github.koloss.ascension.event.IncrementLevelEvent;
import com.github.koloss.ascension.model.DivineAspect;
import com.github.koloss.ascension.model.Faith;
import com.github.koloss.ascension.service.FaithService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class FaithListener implements Listener {
    private FaithService faithService;

    @EventHandler
    public void onIncrementLevel(IncrementLevelEvent event) {
        Player player = event.getPlayer();
        DivineAspect aspect = event.getAspect();

        Faith faith = faithService.findByUserIdAndAspect(player.getUniqueId(), aspect);
        faith.getLevel().incrementAndGet();

        faithService.update(faith);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID userId = player.getUniqueId();

        List<Faith> faiths = faithService.findAllByUserId(userId);
        if (faiths.isEmpty()) {
            for (DivineAspect aspect : DivineAspect.values()) {
                Faith created = faithService.create(userId, aspect);
                faiths.add(created);
            }
        }

        player.sendMessage("Welcome back!\nYour faiths:");
        for (Faith faith : faiths) {
            player.sendMessage(faith.getAspect() + ": " + faith.getCount());
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }

        UUID userId = player.getUniqueId();

        Faith wardenFaith = faithService.findByUserIdAndAspect(userId, DivineAspect.WARDEN);
        wardenFaith.getCount().addAndGet(5);

        faithService.update(wardenFaith);

        Faith smithFaith = faithService.findByUserIdAndAspect(userId, DivineAspect.SMITH);
        smithFaith.getCount().addAndGet(2);

        faithService.update(smithFaith);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID userId = player.getUniqueId();

        List<Faith> faiths = faithService.findAllByUserId(userId);
        for (Faith faith : faiths) {
            faithService.save(faith);
        }
    }
}
