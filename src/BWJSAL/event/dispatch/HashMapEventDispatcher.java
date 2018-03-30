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
import bwapi.BWEventListener;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import sun.util.resources.cldr.en.CalendarData_en_US_POSIX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapEventDispatcher implements GameEventDispatcher {
    private GameEventListenerMap listeners;

    public HashMapEventDispatcher() {
        this.listeners = new GameEventListenerMap();
    }

    /**
     * The GameEventListener interface will be notified for all game events it is interested in
     * once this class is notified of an event.
     */
    @Override
    public void addListener(final GameEventListener gameEventListener) {
        this.listeners.addListener(gameEventListener);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd(final boolean b) {

    }

    @Override
    public void onFrame() {
        this.listeners.getListeners(OnFrameListener.class).forEach(OnFrameListener::onFrame);
    }

    @Override
    public void onSendText(String s) {

    }

    @Override
    public void onReceiveText(final Player player, final String s) {

    }

    @Override
    public void onPlayerLeft(final Player player) {

    }

    @Override
    public void onNukeDetect(final Position position) {
        this.listeners.getListeners(OnNukeDetectListener.class).forEach(listener -> listener.onNukeDetect(position));
    }

    @Override
    public void onUnitDiscover(final Unit unit) {
        this.listeners.getListeners(OnUnitDiscoverListener.class).forEach(listener -> listener.onUnitDiscover(unit));
    }

    @Override
    public void onUnitEvade(Unit unit) {

    }

    @Override
    public void onUnitShow(Unit unit) {

    }

    @Override
    public void onUnitHide(Unit unit) {

    }

    @Override
    public void onUnitCreate(Unit unit) {

    }

    @Override
    public void onUnitDestroy(Unit unit) {

    }

    @Override
    public void onUnitMorph(Unit unit) {

    }

    @Override
    public void onUnitRenegade(Unit unit) {

    }

    @Override
    public void onSaveGame(String s) {

    }

    @Override
    public void onUnitComplete(Unit unit) {

    }

    @Override
    public void onPlayerDropped(Player player) {

    }
}
