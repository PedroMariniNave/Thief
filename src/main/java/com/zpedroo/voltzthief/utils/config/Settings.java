package com.zpedroo.voltzthief.utils.config;

import com.zpedroo.voltzthief.utils.FileUtils;
import org.bukkit.ChatColor;

import java.util.List;

public class Settings {

    public static final String COMMAND = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.command");

    public static final List<String> ALIASES = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.aliases");

    public static final String PERMISSION = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission");

    public static final String PERMISSION_MESSAGE = ChatColor.translateAlternateColorCodes('&',
            FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission-message"));

    public static final String ITEM_EDITOR_TITLE = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.item-editor-title");

    public static final String THIEF_NAME = ChatColor.translateAlternateColorCodes('&',
            FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.thief-name"));

    public static final long SPAWN_DELAY = FileUtils.get().getLong(FileUtils.Files.CONFIG, "Settings.spawn-delay");

    public static final long MAX_TIME = FileUtils.get().getInt(FileUtils.Files.CONFIG, "Settings.max-time");

    public static final int MAX_ITEMS = FileUtils.get().getInt(FileUtils.Files.CONFIG, "Settings.max-items");
}