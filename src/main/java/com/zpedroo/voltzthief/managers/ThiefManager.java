package com.zpedroo.voltzthief.managers;

import com.zpedroo.voltzthief.VoltzThief;
import com.zpedroo.voltzthief.nms.ThiefEntity;
import com.zpedroo.voltzthief.utils.config.Messages;
import com.zpedroo.voltzthief.utils.config.Settings;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class ThiefManager {

    private static ThiefManager instance;
    public static ThiefManager getInstance() { return instance; }

    public ThiefManager() {
        instance = this;
    }

    public void spawnThief() {
        List<Location> locations = DataManager.getInstance().getCache().getThiefLocations();
        if (locations == null || locations.isEmpty()) return;

        Location location = locations.get(new Random().nextInt(locations.size()));
        ThiefEntity thiefEntity = new ThiefEntity(location);
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();
        worldServer.addEntity(thiefEntity);

        for (String msg : Messages.THIEF_SPAWN) {
            Bukkit.broadcastMessage(msg);
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                Entity entity = thiefEntity.getBukkitEntity();
                if (entity == null || entity.isDead()) return;

                despawnThief(entity);
            }
        }.runTaskLater(VoltzThief.get(), Settings.MAX_TIME * 20);
    }

    public void despawnThief(Entity entity) {
        if (entity == null || entity.isDead()) return;

        int droppedItemsAmount = entity.getMetadata("DroppedItemsAmount").get(0).asInt();

        entity.remove();

        for (String msg : Messages.THIEF_DESPAWN) {
            Bukkit.broadcastMessage(StringUtils.replaceEach(msg, new String[]{
                    "{items}"
            }, new String[]{
                    String.valueOf(droppedItemsAmount)
            }));
        }
    }
}