package com.songoda.epichoppers.hopper.levels;

import com.songoda.core.nms.NmsManager;
import com.songoda.core.nms.nbt.NBTCore;
import com.songoda.core.nms.nbt.NBTItem;
import com.songoda.epichoppers.hopper.levels.modules.Module;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LevelManager {

    private final NavigableMap<Integer, Level> registeredLevels = new TreeMap<>();


    public void addLevel(int level, int costExperience, int costEconomy, int range, int amount, boolean filter, boolean teleport, int linkAmount, ArrayList<Module> modules) {
        registeredLevels.put(level, new Level(level, costExperience, costEconomy, range, amount, filter, teleport, linkAmount, modules));
    }


    public Level getLevel(int level) {
        return registeredLevels.get(level);
    }

    public Level getLevel(ItemStack item) {
        NBTCore nbt = NmsManager.getNbt();
        NBTItem nbtItem = nbt.of(item);

        if (nbtItem.has("level"))
            return getLevel(nbtItem.getNBTObject("level").asInt());

        // Legacy trash.
        if (item.hasItemMeta() && item.getItemMeta().getDisplayName().contains(":")) {
            String arr[] = item.getItemMeta().getDisplayName().replace(String.valueOf(ChatColor.COLOR_CHAR), "").split(":");
            return getLevel(Integer.parseInt(arr[0]));
        }

        return getLowestLevel();
    }

    public boolean isEpicHopper(ItemStack item) {
        NBTCore nbt = NmsManager.getNbt();

        if (nbt.of(item).has("level"))
            return true;

        return item.hasItemMeta()
                // Legacy Trash.
                && item.getItemMeta().getDisplayName().contains(":");
    }


    public Level getLowestLevel() {
        return registeredLevels.firstEntry().getValue();
    }


    public Level getHighestLevel() {
        return registeredLevels.lastEntry().getValue();
    }


    public boolean isLevel(int level) {
        return registeredLevels.containsKey(level);
    }


    public Map<Integer, Level> getLevels() {
        return Collections.unmodifiableMap(registeredLevels);
    }

    public void clear() {
        registeredLevels.clear();
    }
}
