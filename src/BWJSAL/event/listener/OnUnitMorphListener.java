package BWJSAL.event.listener;

import bwapi.Unit;

/**
 * The listener interface for receiving notification of game onUnitMorph events.
 * Any classes that are interested in monitoring onUnitMorph events should implement this interface.
 */
public interface OnUnitMorphListener extends GameEventListener {
    /**
     * Called when a unit changes its UnitType. For example, when a Drone transforms into a Hatchery,
     * a Siege Tank uses Siege Mode, or a Vespene Geyser receives a Refinery.
     * This is NOT called if the unit type changes to or from UnitTypes::Unknown
     * @param unit Unit object representing the unit that had its UnitType change
     */
    void onUnitMorph(Unit unit);
}
