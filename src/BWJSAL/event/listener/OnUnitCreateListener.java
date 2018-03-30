package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving bot.notification of game onUnitCreate events.
 * Any classes that are interested in monitoring onUnitCreate events should implement this interface.
 */
public interface OnUnitCreateListener extends GameEventListener {
    /**
     * Called when any unit is created. Due to the internal workings of Broodwar,
     * this function excludes Zerg morphing and the construction of structures over a Vespene Geyser.
     * @param unit The Unit interface object representing the unit that has just been created.
     */
    void onUnitCreate(Unit unit);
}
