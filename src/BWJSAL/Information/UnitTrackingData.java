package BWJSAL.Information;

import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

/**
 * Simple class used to track the latest information related to a unit.
 */
class UnitTrackingData {
    /**
     * Last position of unit.
     */
    private Position position;

    /**
     * Type of unit.
     */
    private UnitType type;

    /**
     * Player that owns unit.
     */
    private Player player;

    /**
     * Does the unit currently exist.
     */
    private boolean exists;

    /**
     * Frame count from when this unit was last seen.
     */
    private Integer lastSeenTime;

    /**
     * Update to latest information about unit.
     *
     * @param unit Unit object
     * @param lastSeenFrameCount Current frame
     */
    public void update(final Unit unit, final int lastSeenFrameCount) {
        this.position = unit.getPosition();
        this.type = unit.getType();
        this.player = unit.getPlayer();
        this.exists = unit.exists();
        this.lastSeenTime = lastSeenFrameCount;
    }

    public void unitDestroyed(final Unit unit, final int lastSeenFrameCount) {
        update(unit, lastSeenFrameCount);
        this.exists = false;
    }

    public Position getPosition() {
        return position;
    }

    public UnitType getType() {
        return type;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getLastSeenTime() {
        return lastSeenTime;
    }

    public boolean doesUnitExist() {
        return exists;
    }
}
