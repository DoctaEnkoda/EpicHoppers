package com.songoda.epichoppers.utils;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public enum HopperDirection {

    DOWN(0, 8, 0, -1, 0),
    NORTH(2, 10, 0, 0, -1),
    SOUTH(3, 11, 0, 0, 1),
    WEST(4, 12, -1, 0, 0),
    EAST(5, 13, 1, 0, 0);

    private int unpowered;
    private int powered;

    private int x;
    private int y;
    private int z;

    HopperDirection(int unpowered, int powered, int x, int y, int z) {
        this.unpowered = unpowered;
        this.powered = powered;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static HopperDirection getDirection(int value) {
        for (HopperDirection hopperDirection : HopperDirection.values()) {
            if (hopperDirection.getPowered() == value
                    || hopperDirection.getUnpowered() == value) return hopperDirection;
        }
        return null;
    }

    public Location getLocation(Location location) {
        return location.clone().add(getX(), getY(), getZ());
    }

    public BlockFace getDirection() {
        switch (this) {
            case DOWN:
                return BlockFace.DOWN;
            case NORTH:
                return BlockFace.NORTH;
            case SOUTH:
                return BlockFace.SOUTH;
            case WEST:
                return BlockFace.WEST;
            case EAST:
                return BlockFace.EAST;
        }
        // really shouldn't happen, tbh
        return BlockFace.DOWN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getUnpowered() {
        return unpowered;
    }

    public int getPowered() {
        return powered;
    }
}