package com.zpedroo.voltzthief.utils.config;

import com.zpedroo.voltzthief.utils.FileUtils;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public static final String COMMAND_USAGE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.command-usage"));

    public static final List<String> THIEF_SPAWN = getColored(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Messages.thief-spawn"));

    public static final List<String> THIEF_DESPAWN = getColored(FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Messages.thief-despawn"));

    private static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    private static List<String> getColored(List<String> list) {
        List<String> colored = new ArrayList<>(list.size());
        for (String str : list) {
            colored.add(getColored(str));
        }

        return colored;
    }
}