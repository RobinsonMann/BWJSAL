package BWJSAL.event.listener;

import bwapi.Player;

/**
 * The listener interface for receiving bot.notification of game onPlayerLeft events.
 * Any classes that are interested in monitoring onPlayerLeft events should implement this interface.
 */
public interface OnPlayerLeftListener extends GameEventListener {
    /**
     * Called when a Player leaves the game.
     * All of their units are automatically given to the neutral player with their colour and alliance parameters preserved.
     * @param player The Player interface object representing the player that left the game.
     */
    void onPlayerLeft(Player player);
}
