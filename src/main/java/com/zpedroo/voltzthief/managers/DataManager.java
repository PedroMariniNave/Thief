package com.zpedroo.voltzthief.managers;

import com.zpedroo.voltzthief.managers.cache.DataCache;
import com.zpedroo.voltzthief.utils.FileUtils;
import com.zpedroo.voltzthief.utils.serialization.BukkitSerialization;
import com.zpedroo.voltzthief.utils.serialization.LocationSerialization;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

    private DataCache dataCache;

    public DataManager() {
        instance = this;
        this.dataCache = new DataCache();
    }

    public List<ItemStack> loadThiefItems() {
        FileUtils.Files file = FileUtils.Files.CONFIG;
        String serializedItems = FileUtils.get().getString(file, "Items");

        List<ItemStack> ret = null;
        try {
            ret = Arrays.asList(BukkitSerialization.itemStackArrayFromBase64(serializedItems));
        } catch (Exception ex) {
            ret = new ArrayList<>(4);
        }

        return ret;
    }

    public List<Location> loadThiefLocations() {
        FileUtils.Files file = FileUtils.Files.CONFIG;
        List<String> serializedLocations = FileUtils.get().getStringList(file, "Locations");

        List<Location> ret = new ArrayList<>(serializedLocations.size());

        for (String serializedLocation : serializedLocations) {
            ret.add(LocationSerialization.deserialize(serializedLocation));
        }

        return ret;
    }

    public void saveThiefItems() {
        FileUtils.Files file = FileUtils.Files.CONFIG;

        List<ItemStack> items = dataCache.getThiefItems();
        String serializedItems = BukkitSerialization.itemStackArrayToBase64(items.toArray(new ItemStack[items.size()]));

        FileUtils.FileManager fileManager = FileUtils.get().getFile(file);
        fileManager.get().set("Items", serializedItems);
        fileManager.save();
    }

    public void saveThiefLocations() {
        FileUtils.Files file = FileUtils.Files.CONFIG;

        List<Location> locations = dataCache.getThiefLocations();
        List<String> serializedLocations = new ArrayList<>(locations.size());

        for (Location location : locations) {
            serializedLocations.add(LocationSerialization.serialize(location));
        }

        FileUtils.FileManager fileManager = FileUtils.get().getFile(file);
        fileManager.get().set("Locations", serializedLocations);
        fileManager.save();
    }

    public DataCache getCache() {
        return dataCache;
    }
}