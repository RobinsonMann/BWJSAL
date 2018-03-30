package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving bot.notification of game onUnitRenegade events.
 * Any classes that are interested in monitoring onUnitRenegade events should implement this interface.
 */
public interface OnUnitRenegadeListener extends GameEventListener {
    /**
     * Called when a unit changes ownership.
     * This occurs when the Protoss ability Mind Control is used, or if a unit changes ownership in Use Map Settings .
     * @param unit Unit interface object pertaining to the unit that has just changed ownership.
     */
    void onUnitRenegade(Unit unit);
}
