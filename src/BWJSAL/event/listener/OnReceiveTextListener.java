package BWJSAL.event.listener;

import bwapi.Player;

/**
 * The listener interface for receiving notification of game onReceiveText events.
 * Any classes that are interested in monitoring onReceiveText events should implement this interface.
 */
public interface OnReceiveTextListener extends GameEventListener {
    /**
     * Called when the client receives a message from another Player.
     * This function can be used to retrieve information from allies in team games,
     * or just to respond to other players.
     * Note: Messages sent by the current player will never invoke this function
     * @param player The Player interface object representing the owner of the text message.
     * @param text The text message that the player sent.
     */
    void onReceiveText(Player player, String text);
}
