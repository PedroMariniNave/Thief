package com.zpedroo.voltzthief.tasks;

import com.zpedroo.voltzthief.managers.ThiefManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static com.zpedroo.voltzthief.utils.config.Settings.*;

public class ThiefSpawnTask extends BukkitRunnable {

    public ThiefSpawnTask(Plugin plugin) {
        this.runTaskTimer(plugin, SPAWN_DELAY * 20, SPAWN_DELAY * 20);
    }

    @Override
    public void run() {
        ThiefManager.getInstance().spawnThief();
    }
}