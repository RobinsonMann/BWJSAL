package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitDestroy events.
 * Any classes that are interested in monitoring onUnitDestroy events should implement this interface.
 */
public interface OnUnitDestroyListener extends GameEventListener {
    /**
     * Called when a unit is removed from the game either through death or other means.
     * When a Drone morphs into an Extractor, the Drone is removed from the game and the Vespene Geyser
     * morphs into an Extractor. If a unit is visible and destroyed, then onUnitHide is called just before this.
     * @param unit Unit object representing the unit that has just been destroyed or otherwise
     *             completely removed from the game.
     */
    void onUnitDestroy(Unit unit);
}
