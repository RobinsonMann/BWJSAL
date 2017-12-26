package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitShow events.
 * Any classes that are interested in monitoring onUnitShow events should implement this interface.
 */
public interface OnUnitShowListener extends GameEventListener {
    /**
     * Called when a previously invisible unit becomes visible.
     * Note: This function EXCLUDES the state of Flag::CompleteMapInformation.
     * @param unit The Unit interface object representing the unit that has just become visible.
     */
    void onUnitShow(Unit unit);
}
