package BWJSAL.event.dispatch;

import BWJSAL.event.listener.GameEventListener;
import BWJSAL.event.listener.OnEndListener;
import BWJSAL.event.listener.OnFrameListener;
import BWJSAL.event.listener.OnNukeDetectListener;
import BWJSAL.event.listener.OnPlayerDroppedListener;
import BWJSAL.event.listener.OnPlayerLeftListener;
import BWJSAL.event.listener.OnReceiveTextListener;
import BWJSAL.event.listener.OnSaveGameListener;
import BWJSAL.event.listener.OnSendTextListener;
import BWJSAL.event.listener.OnStartListener;
import BWJSAL.event.listener.OnUnitCompleteListener;
import BWJSAL.event.listener.OnUnitCreateListener;
import BWJSAL.event.listener.OnUnitDestroyListener;
import BWJSAL.event.listener.OnUnitDiscoverListener;
import BWJSAL.event.listener.OnUnitEvadeListener;
import BWJSAL.event.listener.OnUnitHideListener;
import BWJSAL.event.listener.OnUnitMorphListener;
import BWJSAL.event.listener.OnUnitRenegadeListener;
import BWJSAL.event.listener.OnUnitShowListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a Map of GameEventListener interfaces classes to implementations.
 *
 * Any GameEventListener that is added can be retrieved by any of the GameEventListener interfaces classes that it
 * implements.
 */
class GameEventListenerMap {
    /**
     * Internal mapping from GameEventListener interface to tracked implementations.
     */
    private final Map<Class<? extends GameEventListener>, List<GameEventListener>> listeners;

    public GameEventListenerMap() {
        this.listeners = new HashMap<>(18);
        setupSupportedListenerTypes();
    }

    /**
     * Sets up the internal map between GameEventListener interfaces to implementations.
     */
    private void setupSupportedListenerTypes() {
        supportListenerType(OnEndListener.class);
        supportListenerType(OnFrameListener.class);
        supportListenerType(OnNukeDetectListener.class);
        supportListenerType(OnPlayerDroppedListener.class);
        supportListenerType(OnPlayerLeftListener.class);
        supportListenerType(OnReceiveTextListener.class);
        supportListenerType(OnSaveGameListener.class);
        supportListenerType(OnSendTextListener.class);
        supportListenerType(OnStartListener.class);
        supportListenerType(OnUnitCompleteListener.class);
        supportListenerType(OnUnitCreateListener.class);
        supportListenerType(OnUnitDestroyListener.class);
        supportListenerType(OnUnitDiscoverListener.class);
        supportListenerType(OnUnitEvadeListener.class);
        supportListenerType(OnUnitHideListener.class);
        supportListenerType(OnUnitMorphListener.class);
        supportListenerType(OnUnitRenegadeListener.class);
        supportListenerType(OnUnitShowListener.class);
    }

    /**
     * Creates a map entry for clazzToMap. If a GameEventListener given to addListener
     * implements this interface it will be added to the map.
     */
    private void supportListenerType(final Class<? extends GameEventListener> clazzToMap) {
        this.listeners.put(clazzToMap, new ArrayList<>());
    }

    /**
     * Adds GameEventListener to internal mappings..
     */
    public void addListener(final GameEventListener gameEventListener) {
        this.listeners.entrySet().forEach(listenersEntry -> {
            if (listenersEntry.getKey().isInstance(gameEventListener)) {
                listenersEntry.getValue().add(gameEventListener);
            }
        });
    }

    /**
     * Returns all listeners that implement the given interface.
     */
    public <T extends GameEventListener> List<T> getListeners(final Class<T> tClass) {
        return (List<T>) this.listeners.get(tClass);
    }
}
