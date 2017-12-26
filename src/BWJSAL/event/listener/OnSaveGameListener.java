package BWJSAL.event.listener;

/**
 * The listener interface for receiving notification of game onSaveGame events.
 * Any classes that are interested in monitoring onSaveGame events should implement this interface.
 */
public interface OnSaveGameListener extends GameEventListener {
    /**
     * Called when the state of the Broodwar game is saved to file.
     * @param gameName 	A String object containing the file name that the game was saved as.
     */
    void onSaveGame(String gameName);
}
