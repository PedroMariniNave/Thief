package com.zpedroo.voltzthief.listeners;

import com.zpedroo.voltzthief.VoltzThief;
import com.zpedroo.voltzthief.managers.DataManager;
import com.zpedroo.voltzthief.managers.ThiefManager;
import com.zpedroo.voltzthief.utils.config.Settings;
import net.minecraft.server.v1_8_R3.DamageSource;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class PlayerGeneralListeners implements Listener {

    private static Set<Player> editingItems = new HashSet<>(1);

    @EventHandler(priority = EventPriority.MONITOR)
    public void onClose(InventoryCloseEvent event) {
        if (!editingItems.contains(event.getPlayer())) return;

        Player player = (Player) event.getPlayer();
        editingItems.remove(player);
        List<ItemStack> items = Arrays.stream(event.getInventory().getContents()).filter(item -> item != null && item.getType() != Material.AIR)
                .collect(Collectors.toList());
        DataManager.getInstance().getCache().setThiefItems(items);
        player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
    }
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!event.getEntity().hasMetadata("Thief")) return;
        if (!(event.getDamager() instanceof Player)) return;

        event.setDamage(0);

        LivingEntity entity = (LivingEntity) event.getEntity();
        if (entity.getNoDamageTicks() > 0) return;

        List<ItemStack> items = DataManager.getInstance().getCache().getThiefItems();
        if (items == null || items.isEmpty()) return;

        ItemStack item = items.get(new Random().nextInt(items.size()));
        if (item == null || item.getType().equals(Material.AIR)) return;

        int droppedItemsAmount = entity.getMetadata("DroppedItemsAmount").get(0).asInt();
        entity.setMetadata("DroppedItemsAmount", new FixedMetadataValue(VoltzThief.get(), ++droppedItemsAmount));
        entity.setNoDamageTicks(40);
        entity.getWorld().dropItemNaturally(entity.getLocation().clone().add(0D, 1.5D, 0D), item);
        entity.getWorld().playSound(entity.getLocation(), Sound.HORSE_SADDLE, 0.2f, 10f);
        entity.getWorld().playEffect(entity.getLocation().clone().add(
                ThreadLocalRandom.current().nextDouble(0, 1),
                ThreadLocalRandom.current().nextDouble(1, 1.25),
                ThreadLocalRandom.current().nextDouble(0, 1)
                ), Effect.VILLAGER_THUNDERCLOUD, 0);

        if (droppedItemsAmount >= Settings.MAX_ITEMS) {
            ThiefManager.getInstance().despawnThief(entity);
            entity.getWorld().playSound(entity.getLocation(), Sound.EXPLODE, 0.2f, 10f);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onOpen(InventoryOpenEvent event) {
        if (!event.getInventory().getType().equals(InventoryType.MERCHANT) && !event.getInventory().getTitle()
                .equals(Settings.THIEF_NAME)) return;

        event.setCancelled(true);
    }

    public static Set<Player> getEditingItems() {
        return editingItems;
    }
}