package com.zpedroo.voltzthief.commands;

import com.zpedroo.voltzthief.listeners.PlayerGeneralListeners;
import com.zpedroo.voltzthief.managers.DataManager;
import com.zpedroo.voltzthief.managers.ThiefManager;
import com.zpedroo.voltzthief.utils.config.Messages;
import com.zpedroo.voltzthief.utils.config.Settings;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ThiefCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (args.length <= 0) {
            sender.sendMessage(StringUtils.replaceEach(Messages.COMMAND_USAGE, new String[]{
                    "{command}"
            }, new String[]{
                    label
            }));
            return true;
        }

        switch (args[0].toUpperCase()) {
            case "SPAWN":
                ThiefManager.getInstance().spawnThief();
                return true;
            case "ITEMS":
                if (player == null) return true;

                Inventory inventory = Bukkit.createInventory(null, 9*6, Settings.ITEM_EDITOR_TITLE);
                DataManager.getInstance().getCache().getThiefItems().forEach(inventory::addItem);

                PlayerGeneralListeners.getEditingItems().add(player);
                player.openInventory(inventory);
                return true;
            case "CREATELOC":
                if (player == null) return true;

                DataManager.getInstance().getCache().getThiefLocations().add(player.getLocation());
                player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1f, 1f);
                return true;
        }

        return false;
    }
}