package BWJSAL.event.dispatch;

import BWJSAL.event.listener.GameEventListener;
import BWJSAL.event.listener.OnFrameListener;
import BWJSAL.event.listener.OnUnitDiscoverListener;
import bwapi.BWEventListener;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Game event dispatch interface
 */
public class InsertionOrderGameEventDispatcher implements GameEventDispatcher, BWEventListener {
    private final List<OnFrameListener> onFrameListeners;
    private final List<OnUnitDiscoverListener> onUnitDiscoverListeners;

    public InsertionOrderGameEventDispatcher() {
        this.onFrameListeners = new ArrayList<>();
        this.onUnitDiscoverListeners = new ArrayList<>();
    }

    @Override
    public void addListener(final GameEventListener gameEventListener) {
        if (gameEventListener instanceof OnFrameListener) {
            this.onFrameListeners.add((OnFrameListener) gameEventListener);
        }

        if (gameEventListener instanceof OnUnitDiscoverListener) {
            this.onUnitDiscoverListeners.add((OnUnitDiscoverListener) gameEventListener);
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onEnd(boolean b) {

    }

    public void onFrame() {
        this.onFrameListeners.forEach(OnFrameListener::onFrame);
    }

    @Override
    public void onSendText(String s) {

    }

    @Override
    public void onReceiveText(Player player, String s) {

    }

    @Override
    public void onPlayerLeft(Player player) {

    }

    @Override
    public void onNukeDetect(Position position) {

    }

    public void onUnitDiscover(final Unit unit) {
        this.onUnitDiscoverListeners.forEach(listener -> listener.onUnitDiscover(unit));
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
