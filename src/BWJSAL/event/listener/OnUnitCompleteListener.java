package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitComplete events.
 * Any classes that are interested in monitoring onUnitComplete events should implement this interface.
 */
public interface OnUnitCompleteListener extends GameEventListener {
    /**
     * Called when the state of a unit changes from incomplete to complete.
     * @param unit The Unit object representing the Unit that has just finished training or constructing
     */
    void onUnitComplete(Unit unit);
}
