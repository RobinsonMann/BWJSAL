package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitHide events.
 * Any classes that are interested in monitoring onUnitHide events should implement this interface.
 */
public interface OnUnitHideListener extends GameEventListener {
    /**
     * Called just as a visible unit is becoming invisible.
     * This function EXCLUDES the state of Flag::CompleteMapInformation
     * @param unit The Unit interface object representing the unit that is about to go out of scope.
     */
    void onUnitHide(Unit unit);
}
