package BWJSAL.event.listener;

/**
 * The listener interface for receiving notification of game onStart events.
 * Any classes that are interested in monitoring onStart events should implement this interface.
 */
public interface OnStartListener extends GameEventListener {
    /**
     * Called only once at the beginning of a game. It is intended that the AI module
     * do any data initialization in this function. Using the Broodwar interface before
     * this function is called can produce undefined behaviour and crash your bot.
     * (During static initialization of a class for example)
     */
    void onStart();
}
