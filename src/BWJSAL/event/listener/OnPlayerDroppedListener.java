package BWJSAL.event.listener;

import bwapi.Player;

/**
 * The listener interface for receiving notification of game onPlayerDropped events.
 * Any classes that are interested in monitoring onPlayerDropped events should implement this interface.
 */
public interface OnPlayerDroppedListener extends GameEventListener {
    /**
     * Called when a Player is dropped from the game.
     * @param player The Player interface object representing the player that dropped from the game.
     */
    void onPlayerDropped(Player player);
}
