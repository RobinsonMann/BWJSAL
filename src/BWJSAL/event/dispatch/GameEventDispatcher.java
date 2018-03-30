package BWJSAL.event.dispatch;

import BWJSAL.event.listener.GameEventListener;
import bwapi.BWEventListener;

public interface GameEventDispatcher extends BWEventListener {
    void addListener(GameEventListener gameEventListener);
}
