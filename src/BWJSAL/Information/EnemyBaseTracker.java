package BWJSAL.Information;

import BWJSAL.bwta.BwtaWrapper;
import BWJSAL.event.listener.OnUnitDestroyListener;
import BWJSAL.event.listener.OnUnitDiscoverListener;
import bwapi.Game;
import bwapi.Unit;
import bwta.BaseLocation;
import bwta.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Tracks observed information related to the starting base of the enemy.
 */
public class EnemyBaseTracker implements OnUnitDiscoverListener, OnUnitDestroyListener {
    private final Game game;
    private final BwtaWrapper bwta;
    final private Set<BaseLocation> enemyBaseLocations;
    final private Map<BaseLocation, List<Unit>> baseLocationToCenter;
    final private Set<BaseLocation> startLocationsThatCouldContainEnemy;

    public EnemyBaseTracker(final Game game, final BwtaWrapper bwta) {
        this.game = game;
        this.bwta = bwta;
        this.enemyBaseLocations = new HashSet<>();
        this.baseLocationToCenter = new HashMap<>();
        this.startLocationsThatCouldContainEnemy = new HashSet<>();
    }

    public EnemyBaseTracker(final Game game) {
        this(game, new BwtaWrapper());
    }

    private void markBaseLocationAsNotEnemySpawnLocation(final BaseLocation baseLocation) {
        this.startLocationsThatCouldContainEnemy.remove(baseLocation);
    }

    public void setBaseEmpty(final BaseLocation baseLocation) {
        this.enemyBaseLocations.remove(baseLocation);
        this.baseLocationToCenter.remove(baseLocation);
        this.startLocationsThatCouldContainEnemy.remove(baseLocation);
        determineEnemyStartLocationByProcessOfElimination();
    }

    public void markBaseLocationAsEnemyBase(final BaseLocation baseLocation) {
        this.enemyBaseLocations.add(baseLocation);
    }

    public void markBaseLocationAsEnemyBase(final BaseLocation baseLocation, final Unit base) {
        markBaseLocationAsEnemyBase(baseLocation);

        if (this.baseLocationToCenter.containsKey(baseLocation) == false) {
            this.baseLocationToCenter.put(baseLocation, new ArrayList<>());
        }

        this.baseLocationToCenter.get(baseLocation)
                                 .add(base);
    }

    public Set<BaseLocation> getEnemyBases() {
        return this.enemyBaseLocations;
    }

    public boolean scoutedAnEnemyBase() {
        return !this.enemyBaseLocations.isEmpty();
    }

    public void onStart() {
        this.startLocationsThatCouldContainEnemy.addAll(this.bwta.getStartLocations());
        markBaseLocationAsNotEnemySpawnLocation(this.bwta.getStartLocation(this.game.self()));
        determineEnemyStartLocationByProcessOfElimination();
    }

    @Override
    public void onUnitDiscover(final Unit unitDiscovered) {
        if (this.game.self().isEnemy(unitDiscovered.getPlayer()) == false) {
            return;
        }

        // Original logic in BWSAL is that the first enemy building spotted in a base location
        // impales that said base location belongs to the enemy.  Logic inside of BWJSAL has
        // been updated to include a check that the region is equal to a possible starting location
        // of the enemy.
        if (scoutedAnEnemyBase() == false &&
                unitDiscovered.getType().isBuilding()) {
            final Optional<BaseLocation> enemyBaseLocation  = possibleEnemyStartingLocation(unitDiscovered);
            if (enemyBaseLocation.isPresent()) {
                // So the enemy probably spawned here.
                markBaseLocationAsEnemyBase(enemyBaseLocation.get());
            }
        }

        if (unitDiscovered.getType().isResourceDepot()) {
            final BaseLocation nearestBaseLocation = this.bwta.getNearestBaseLocation(unitDiscovered.getTilePosition());
            markBaseLocationAsEnemyBase(nearestBaseLocation, unitDiscovered);
        }
    }

    @Override
    public void onUnitDestroy(final Unit destroyedUnit) {
        if (this.game.self().isEnemy(destroyedUnit.getPlayer()) == false) {
            return;
        }

        // If this is an enemy unit we may need to remove a base location
        if (destroyedUnit.getType().isResourceDepot() == false) {
            return;
        }

        final BaseLocation resourceDepotBaseLocation =
                this.bwta.getNearestBaseLocation(destroyedUnit.getTilePosition());

        if (this.baseLocationToCenter.containsKey(resourceDepotBaseLocation) == false) {
            return;
        }

        final List<Unit> resourceDepotsAtNearestBaseLocation =
                this.baseLocationToCenter.get(resourceDepotBaseLocation);

        if (resourceDepotsAtNearestBaseLocation.contains(destroyedUnit) == false) {
            return;
        }

        resourceDepotsAtNearestBaseLocation.remove(destroyedUnit);

        if (resourceDepotsAtNearestBaseLocation.isEmpty()) {
            this.enemyBaseLocations.remove(resourceDepotBaseLocation);
        }
    }

    /**
     * Will return a base location belonging to the enemy if the given building
     * is inside of a region that also contains a starting location that could contain
     * an enemy base.
     */
    private Optional<BaseLocation> possibleEnemyStartingLocation(final Unit building) {
        // Find the region that this building belongs to.
        final Region unitRegion = this.bwta.getRegion(building.getTilePosition());
        final List<BaseLocation> baseLocationsInsideRegion = unitRegion.getBaseLocations();

        // Checks if the any base locations inside of the units region are possible
        // starting locations that could contain the enemy. If so, return.
        for (final BaseLocation baseLocationInsideRegion : baseLocationsInsideRegion) {
            if (this.startLocationsThatCouldContainEnemy.contains(baseLocationInsideRegion)) {
                return Optional.of(baseLocationInsideRegion);
            }
        }

        return Optional.empty();
    }

    /**
     * If we can determine the enemy start location by process of elimination,
     * add enemy start location to list of enemy bases
     */
    private void determineEnemyStartLocationByProcessOfElimination() {
        if (this.startLocationsThatCouldContainEnemy.size() == 1) {
            this.enemyBaseLocations.add(this.startLocationsThatCouldContainEnemy.iterator()
                                                                                .next());
        }
    }
}
