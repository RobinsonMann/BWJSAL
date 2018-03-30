package BWJSAL.event.listener;

/**
 * The listener interface for receiving bot.notification of game onFrame events.
 * Any classes that are interested in monitoring onFrame events should implement this interface.
 */
public interface OnFrameListener extends GameEventListener {
    /**
     * Called once for every execution of a logical frame in Broodwar.
     * Users will generally put most of their code in this function.
     */
    void onFrame();
}
