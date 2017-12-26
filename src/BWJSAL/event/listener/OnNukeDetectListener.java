package BWJSAL.event.listener;

import bwapi.Position;

/**
 * The listener interface for receiving notification of game onNukeDetect events.
 * Any classes that are interested in monitoring onNukeDetect events should implement this interface.
 */
public interface OnNukeDetectListener extends GameEventListener {
    /**
     * Called when a Nuke has been launched somewhere on the map.
     * @param position A Position object containing the target location of the Nuke.
     *                 If the target location is not visible and Flag::CompleteMapInformation
     *                 is disabled, then target will be Positions::Unknown.
     */
    void onNukeDetect(final Position position);
}
