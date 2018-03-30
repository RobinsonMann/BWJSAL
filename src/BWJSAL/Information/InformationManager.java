package BWJSAL.Information;

import BWJSAL.bwta.BwtaWrapper;
import BWJSAL.event.listener.OnFrameListener;
import BWJSAL.event.listener.OnStartListener;
import BWJSAL.event.listener.OnUnitDestroyListener;
import BWJSAL.event.listener.OnUnitDiscoverListener;
import bwapi.Game;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BaseLocation;

import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

/**
 * Information Manager is the class that provides access to the Information debug functionality.
 *
 * Responsible for tracking simple information that is observed on every frame.
 *
 * This includes:
 * - Tracked information about a unit:
 * - Last Position
 * - Last Seen Frame Count
 * - Last UnitType (Helpful for units that can morph)
 * - Last unit existence state
 * - Last set Player
 *
 * - First time when enemy unit type has been spotted
 *
 * - Locations of enemy bases
 *
 * onStart() MUST be called for this class at the start of the game.
 */
public class InformationManager implements
        OnStartListener, OnFrameListener, OnUnitDiscoverListener, OnUnitDestroyListener {

    private final UnitTracker unitTracker;
    private final BuildTimeTracker buildTimeTracker;
    private final EnemyBaseTracker enemyBaseTracker;
    private final Game game;

    @Inject
    public InformationManager(final Game game, final BwtaWrapper bwta) {
        this.unitTracker = new UnitTracker(game);
        this.buildTimeTracker = new BuildTimeTracker(game);
        this.enemyBaseTracker = new EnemyBaseTracker(game, bwta);
        this.game = game;
    }

    public InformationManager(final Game game) {
        this(game, new BwtaWrapper());
    }

    /**
     * Gets the last known position of the unit.
     */
    public Position getLastPosition(final Unit unit) {
        return this.unitTracker.getLastPosition(unit);
    }

    /**
     * Gets the last known player of the unit.
     */
    public Player getPlayer(final Unit unit) {
        return this.unitTracker.getPlayer(unit);
    }

    /**
     * Gets the last known UnitType of the unit.
     */
    public UnitType getUnitType(final Unit unit) {
        return this.unitTracker.getUnitType(unit);
    }

    /**
     * Gets the last known status about the units existence.
     */
    public boolean doesUnitExist(final Unit unit) {
        return this.unitTracker.doesUnitExist(unit);
    }

    /**
     * Gets the last known status about the units existence.
     */
    public Optional<Integer> getLastSeenFrameCount(final Unit unit) {
        return this.unitTracker.getLastSeenTime(unit);
    }

    public Set<BaseLocation> getEnemyBases() {
        return this.enemyBaseTracker.getEnemyBases();
    }

    /**
     * Retrieve if the information manager has observed that an enemy has built this unit type, or can infer such from that player's tech tree.
     */
    public boolean enemyHasBuilt(final UnitType unitType) {
        return this.buildTimeTracker.enemyHasBuilt(unitType);
    }

    /**
     * Set the baselocation as empty, as another module (such as the ScoutManager) has determined that this base definitely does not contain an enemy base.
     */
    public void setBaseEmpty(final BaseLocation baseLocation) {
        this.enemyBaseTracker.setBaseEmpty(baseLocation);
    }

    /**
     * Set the baselocation as empty, as another module (such as the ScoutManager) has determined that this base definitely does not contain an enemy base.
     * Same as setBaseEmpty.
     */
    public void setBaseAsEmpty(final BaseLocation baseLocation) {
        setBaseEmpty(baseLocation);
    }

    /**
     * Set the baselocation as an enemy base, as another module (such as the ScoutManager) has determined that this base definitely does contain an enemy base
     */
    public void setBaseAsEnemy(final BaseLocation baseLocation) {
        this.enemyBaseTracker.markBaseLocationAsEnemyBase(baseLocation);
    }

    /**
     * Set the baselocation as an enemy base, as another module (such as the ScoutManager) has determined that this base definitely does contain an enemy base
     * SAme as setBaseAsEnemy.
     */
    public void setBaseLocationAsEnemyBase(final BaseLocation baseLocation) {
        setBaseAsEnemy(baseLocation);
    }

    /**
     * For all tracked units that still exist, update UnitTrackingData to contain latest data.
     */
    @Override
    public void onFrame() {
        final int time = this.game.getFrameCount();
        this.unitTracker.onFrame(time);
    }

    @Override
    public void onUnitDiscover(final Unit unit) {
        final int time = this.game.getFrameCount();
        this.unitTracker.onUnitDiscover(unit, time);
        this.buildTimeTracker.onUnitDiscover(unit, time);
        this.enemyBaseTracker.onUnitDiscover(unit);
    }

    @Override
    public void onUnitDestroy(final Unit unit) {
        final int time = this.game.getFrameCount();
        this.unitTracker.onUnitDestroy(unit, time);
        this.enemyBaseTracker.onUnitDestroy(unit);
    }

    @Override
    public void onStart() {
        this.buildTimeTracker.onStart();
        this.enemyBaseTracker.onStart();
    }
}
