package BWJSAL.event.listener;

/**
 * The listener interface for receiving notification of game onEnd events.
 * Any classes that are interested in monitoring onEnd events should implement this interface.
 */
public interface OnEndListener extends GameEventListener {
    /**
     * Called once at the end of a game.
     * @param isWinner A boolean value to determine if the current player has won the match.
     *                 This value will be true if the current player has won, and false if
     *                 either the player has lost or the game is actually a replay.
     */
    void onEnd(boolean isWinner);
}
