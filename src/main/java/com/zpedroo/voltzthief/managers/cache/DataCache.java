package com.zpedroo.voltzthief.managers.cache;

import com.zpedroo.voltzthief.managers.DataManager;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DataCache {

    private List<ItemStack> thiefItems;
    private List<Location> thiefLocations;

    public DataCache() {
        this.thiefItems = DataManager.getInstance().loadThiefItems();
        this.thiefLocations = DataManager.getInstance().loadThiefLocations();
    }

    public List<ItemStack> getThiefItems() {
        return thiefItems;
    }

    public List<Location> getThiefLocations() {
        return thiefLocations;
    }

    public void setThiefItems(List<ItemStack> thiefItems) {
        this.thiefItems = thiefItems;
    }
}