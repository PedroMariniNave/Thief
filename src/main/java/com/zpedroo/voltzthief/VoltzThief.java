package com.zpedroo.voltzthief;

import com.zpedroo.voltzthief.commands.ThiefCmd;
import com.zpedroo.voltzthief.listeners.PlayerGeneralListeners;
import com.zpedroo.voltzthief.managers.DataManager;
import com.zpedroo.voltzthief.managers.ThiefManager;
import com.zpedroo.voltzthief.nms.CustomEntityRegistry;
import com.zpedroo.voltzthief.nms.ThiefEntity;
import com.zpedroo.voltzthief.tasks.ThiefSpawnTask;
import com.zpedroo.voltzthief.utils.FileUtils;
import net.minecraft.server.v1_8_R3.EntityVillager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

import static com.zpedroo.voltzthief.utils.config.Settings.*;

public class VoltzThief extends JavaPlugin {

    private static VoltzThief instance;
    public static VoltzThief get() { return instance; }

    public void onEnable() {
        instance = this;

        CustomEntityRegistry.registerEntity("", 120, EntityVillager.class, ThiefEntity.class);

        new FileUtils(this);
        new DataManager();
        new ThiefManager();
        new ThiefSpawnTask(this);

        registerListeners();
        registerCommand(COMMAND, ALIASES, PERMISSION, PERMISSION_MESSAGE, new ThiefCmd());
    }

    public void onDisable() {
        DataManager.getInstance().saveThiefItems();
        DataManager.getInstance().saveThiefLocations();
    }

    private void registerCommand(String command, List<String> aliases, String permission, String permissionMessage, CommandExecutor executor) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCmd = constructor.newInstance(command, this);
            pluginCmd.setAliases(aliases);
            pluginCmd.setExecutor(executor);
            pluginCmd.setPermission(permission);
            pluginCmd.setPermissionMessage(permissionMessage);

            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(getName().toLowerCase(), pluginCmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerGeneralListeners(), this);
    }
}