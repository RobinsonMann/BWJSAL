package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving bot.notification of game onUnitDiscover events.
 * Any classes that are interested in monitoring onUnitDiscover events should implement this interface.
 */
public interface OnUnitDiscoverListener extends GameEventListener {
    /**
     * Called when a Unit becomes accessible.
     * Note: This function INCLUDES the state of Flag::CompleteMapInformation.
     * @param unit The Unit interface object representing the unit that has just become accessible.
     */
    void onUnitDiscover(Unit unit);
}
