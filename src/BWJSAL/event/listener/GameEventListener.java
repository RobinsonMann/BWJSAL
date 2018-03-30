package BWJSAL.event.listener;

/**
 * Parent interface of all listener interfaces for receiving bot.notification of game events.
 * Any classes that are interested in monitoring game events should implement the appropriate child interface.
 * No classes should implement directly from this interface.
 *
 * The BWEventListener that is registered with the Mirror's AIModule is responsible for calling all instances of listeners.
 */
public interface GameEventListener {
}
