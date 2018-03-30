package BWJSAL.EnhancedUi.debug;

import BWJSAL.allocation.mutex.UnitsMutex;
import BWJSAL.event.listener.OnFrameListener;
import BWJSAL.event.listener.OnStartListener;
import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UnitsMutexUI implements OnStartListener, OnFrameListener {
    private final Game game;
    private final UnitsMutex unitsMutex;
    private Player self = null;

    @Inject
    public UnitsMutexUI(final Game game, final UnitsMutex unitsMutex) {
        this.game = game;
        this.unitsMutex = unitsMutex;
    }

    @Override
    public void onStart() {
        this.self = this.game.self();
    }

    @Override
    public void onFrame() {
        for (final Unit myUnit: this.self.getUnits()) {
            //this.game.drawTextMap(myUnit.getPosition(), "Locked: " + this.unitsMutex.isUnitLocked(myUnit));
        }
    }
}
