package com.github.koloss.ascension.controller.particle;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleManager {
    private static ParticleManager manager;
    private final Plugin plugin;

    private ParticleManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public static ParticleManager of(Plugin plugin) {
        if (manager == null) {
            manager = new ParticleManager(plugin);
        }

        return manager;
    }

    public void displayRing(Player player, NamedTextColor color) {
        ParticleData particleData = ParticleData.builder()
                .radius(1.2)
                .startHeight(2)
                .endHeight(0.2)
                .speed(0.1)
                .points(30)
                .size(1.3f)
                .build();
        Color rgbColor = Color.fromRGB(color.red(), color.green(), color.blue());

        BukkitRunnable ringTask = new DisplayRingTask(player, particleData, rgbColor);
        ringTask.runTaskTimer(plugin, 0, 1);
    }

    private static class DisplayRingTask extends BukkitRunnable {
        private final Player player;
        private final ParticleData particleData;

        private final Color color;
        private double y;

        public DisplayRingTask(Player player, ParticleData particleData, Color color) {
            this.player = player;
            this.particleData = particleData;
            this.color = color;
            this.y = particleData.getStartHeight();
        }

        @Override
        public void run() {
            if (!player.isOnline()) {
                cancel();
                return;
            }

            if (y <= particleData.getEndHeight()) {
                cancel();
                return;
            }

            World world = player.getWorld();
            Location base = player.getLocation();

            Particle.DustOptions dustOptions = new Particle.DustOptions(color, particleData.getSize());

            for (int i = 0; i < particleData.getPoints(); i++) {
                double angle = 2 * Math.PI * i / particleData.getPoints();
                double x = Math.cos(angle) * particleData.getRadius();
                double z = Math.sin(angle) * particleData.getRadius();

                Location loc = base.clone().add(x, y, z);
                world.spawnParticle(
                        Particle.DUST, loc, 1,
                        0, 0, 0,
                        0, dustOptions
                );
            }

            y -= particleData.getSpeed();
        }
    }
}
