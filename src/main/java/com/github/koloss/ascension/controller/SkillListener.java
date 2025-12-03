package com.github.koloss.ascension.controller;

import com.github.koloss.ascension.controller.event.CloseProgressSidebarEvent;
import com.github.koloss.ascension.controller.event.DisplayProgressMenuEvent;
import com.github.koloss.ascension.controller.event.DisplayProgressSidebarEvent;
import com.github.koloss.ascension.controller.event.IncrementLevelEvent;
import com.github.koloss.ascension.controller.menu.Menu;
import com.github.koloss.ascension.controller.menu.SkillMenu;
import com.github.koloss.ascension.controller.menu.manager.MenuManager;
import com.github.koloss.ascension.controller.modifier.ModifierManager;
import com.github.koloss.ascension.controller.sidebar.ProgressSidebar;
import com.github.koloss.ascension.controller.sidebar.Sidebar;
import com.github.koloss.ascension.controller.sidebar.manager.SidebarManager;
import com.github.koloss.ascension.model.Skill;
import com.github.koloss.ascension.model.SkillType;
import com.github.koloss.ascension.service.SkillService;
import com.github.koloss.ascension.utils.SkillTypeUtils;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SkillListener implements Listener {
    private static final long REFRESH_SIDEBAR_INTERVAL = 10L;

    private SkillService skillService;

    private MenuManager menuManager;
    private SidebarManager sidebarManager;
    private ModifierManager modifierManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID userId = player.getUniqueId();

        SkillType[] skillTypes = SkillType.values();

        List<Skill> skills = skillService.findAllByUserId(userId);
        if (skills.isEmpty()) {
            for (SkillType type : skillTypes) {
                Skill created = skillService.create(userId, type);
                skills.add(created);
            }
        } else {
            PersistentDataContainer dataContainer = player.getPersistentDataContainer();
            SkillType type = SkillTypeUtils.fromContainer(dataContainer);

            if (type != null) {
                Sidebar sidebar = new ProgressSidebar(skillService, player, type);
                sidebarManager.display(sidebar, REFRESH_SIDEBAR_INTERVAL);
            }
        }

        for (SkillType type : skillTypes) {
            modifierManager.apply(player, type);
        }
    }

    @EventHandler
    public void onIncrementLevel(IncrementLevelEvent event) {
        Player player = event.getPlayer();
        SkillType type = event.getSkillType();

        Skill skill = skillService.findByUserIdAndType(player.getUniqueId(), type);

        skill.getLevelCount().incrementAndGet();
        skillService.update(skill);

        modifierManager.apply(player, type);
    }

    @EventHandler
    public void onDisplayProgressSidebar(DisplayProgressSidebarEvent event) {
        Player player = event.getPlayer();
        SkillType type = event.getSkillType();

        Sidebar sidebar = new ProgressSidebar(skillService, player, type);
        sidebarManager.display(sidebar, REFRESH_SIDEBAR_INTERVAL);
    }

    @EventHandler
    public void onDisplayProgressMenu(DisplayProgressMenuEvent event) {
        Player player = event.getPlayer();
        SkillType type = event.getSkillType();

        Menu menu = new SkillMenu(skillService, player, type);
        menuManager.display(menu, player);
    }

    @EventHandler
    public void onCloseProgressSidebar(CloseProgressSidebarEvent event) {
        Player player = event.getPlayer();
        sidebarManager.close(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID userId = player.getUniqueId();

        List<Skill> skills = skillService.findAllByUserId(userId);
        for (Skill skill : skills) {
            skillService.save(skill);
        }

        sidebarManager.close(player);
    }
}
