package com.songoda.epichoppers.hopper;

import com.songoda.epichoppers.EpicHoppers;
import com.songoda.epichoppers.hopper.levels.Level;
import com.songoda.epichoppers.hopper.levels.modules.Module;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class HopperManager {

    private final Map<Location, Hopper> registeredHoppers = new HashMap<>();

    public Hopper addHopper(Hopper hopper) {
        registeredHoppers.put(roundLocation(hopper.getLocation()), hopper);
        return hopper;
    }

    @Deprecated
    public void addHopper(Location location, Hopper hopper) {
        registeredHoppers.put(roundLocation(location), hopper);
    }

    public void addHoppers(Collection<Hopper> hoppers) {
        for (Hopper hopper : hoppers)
            registeredHoppers.put(hopper.getLocation(), hopper);
    }

    /**
     * Removes a hopper and unlinks it from any other hoppers
     *
     * @param location The location of the hopper to remove
     * @return The removed hopper, or null if none was removed
     */
    public Hopper removeHopper(Location location) {
        Hopper removed = this.registeredHoppers.remove(location);

        for (Hopper hopper : this.registeredHoppers.values())
            hopper.removeLinkedBlock(location);

        for (Level level : EpicHoppers.getInstance().getLevelManager().getLevels().values())
            for (Module module : level.getRegisteredModules())
                module.clearData(removed);

        return removed;
    }


    public Hopper getHopper(Location location) {
        if (!registeredHoppers.containsKey(location = roundLocation(location))) {
            Hopper hopper = addHopper(new Hopper(location));
            EpicHoppers.getInstance().getDataManager().createHopper(hopper);
        }
        return registeredHoppers.get(location);
    }


    public Hopper getHopper(Block block) {
        return getHopper(block.getLocation());
    }


    public boolean isHopper(Location location) {
        return registeredHoppers.containsKey(roundLocation(location));
    }


    public Map<Location, Hopper> getHoppers() {
        return Collections.unmodifiableMap(registeredHoppers);
    }


    public Hopper getHopperFromPlayer(Player player) {
        for (Hopper hopper : registeredHoppers.values()) {
            if (hopper.getLastPlayerOpened() == player.getUniqueId()) {
                return hopper;
            }
        }
        return null;
    }


    private Location roundLocation(Location location) {
        location = location.clone();
        location.setX(location.getBlockX());
        location.setY(location.getBlockY());
        location.setZ(location.getBlockZ());
        return location;
    }
}
