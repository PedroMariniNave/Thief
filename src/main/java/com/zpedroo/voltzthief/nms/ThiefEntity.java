package com.zpedroo.voltzthief.nms;

import com.zpedroo.voltzthief.VoltzThief;
import com.zpedroo.voltzthief.utils.config.Settings;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.metadata.FixedMetadataValue;

public class ThiefEntity extends EntityVillager {

    public ThiefEntity(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setProfession(3);
        this.setCustomName(Settings.THIEF_NAME);
        this.setCustomNameVisible(true);
        this.getBukkitEntity().setMetadata("Thief", new FixedMetadataValue(VoltzThief.get(), true));
        this.getBukkitEntity().setMetadata("DroppedItemsAmount", new FixedMetadataValue(VoltzThief.get(), 0));
        this.getAttributeInstance(GenericAttributes.c).setValue(9999);

        this.goalSelector.a(0, new PathfinderGoalAvoidTarget<>(this, EntityHuman.class, 17, 1D, 1D));
        this.goalSelector.a(1, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(2, new PathfinderGoalRandomLookaround(this));
        this.goalSelector.a(3, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
        this.goalSelector.a(4, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(5, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
    }
}