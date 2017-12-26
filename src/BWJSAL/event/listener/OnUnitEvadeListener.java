package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitEvade events.
 * Any classes that are interested in monitoring onUnitEvade events should implement this interface.
 */
public interface OnUnitEvadeListener extends GameEventListener {
    /**
     * Called when a Unit becomes inaccessible.
     * Note: This function INCLUDES the state of Flag::CompleteMapInformation.
     * @param unit The Unit interface object representing the unit that has just become inaccessible.
     */
    void onUnitEvade(Unit unit);
}
