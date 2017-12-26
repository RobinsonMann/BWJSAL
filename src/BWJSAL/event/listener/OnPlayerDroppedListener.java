package BWJSAL.event.listener;

/**
 * The listener interface for receiving notification of game onPlayerDropped events.
 * Any classes that are interested in monitoring onPlayerDropped events should implement this interface.
 */
public interface OnPlayerDroppedListener extends GameEventListener {
    void onPlayerDropped();
}
