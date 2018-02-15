package BWJSAL.Information;

import BWJSAL.event.listener.OnStartListener;
import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashMap;
import java.util.Map;

/**
 * Based on information observed, tracks what unit types the enemy has produced
 * and the earliest time that the unit type was observed.
 */
public class BuildTimeTracker implements OnStartListener {
    private final Game game;
    private final Map<UnitType, Integer> earliestBuildTime;

    private Player self;

    public BuildTimeTracker(final Game game) {
        this.game = game;
        this.earliestBuildTime = new HashMap<>();
    }

    @Override
    public void onStart() {
        buildTimesForStartingUnits();
        this.self = this.game.self();
    }

    /**
     * Retrieve if it has been observed that an enemy has built this unit type, or it can infer such from that player's tech tree.
     *
     * @param unitType UnitType to check if the enemy has built
     * @return true if our observations confirms that the enemy has built this unit.
     */
    public boolean enemyHasBuilt(final UnitType unitType) {
        return this.earliestBuildTime.containsKey(unitType);
    }

    /**
     * From what has been observed, infer the latest possible time that the enemy must have build this unit type.
     * Latest possible build time is determined from direct observation or inferred from that tech required for another
     * observed unit.
     *
     * @param unitType The UnitType to get inferred latest possible build time for.
     * @return Time (as frame count) when we first discovered that the enemy has constructed the given UnitType.
     */
    public Integer getBuildTime(final UnitType unitType) {
        if (enemyHasBuilt(unitType) == false) {
            throw new IllegalArgumentException(String.format("Enemy has not built unitType %s", unitType.toString()));
        }

        return this.earliestBuildTime.get(unitType);
    }

    /**
     * Mark that we have observed the given UnitType at the current time.
     * This will recursively update the BuildTime for all units along the units tech tree.
     */
    private void updateBuildTime(final UnitType unitType, final int time) {
        if (this.earliestBuildTime.containsKey(unitType)) {
            final int previouslyObservedBuiltTime = this.earliestBuildTime.get(unitType);

            if (previouslyObservedBuiltTime <= time || previouslyObservedBuiltTime == 0) {
                return;
            }
        }

        this.earliestBuildTime.put(unitType, time);

        // Sanity check. (Present inside of original BWSAL)
        if (time < 0) {
            return;
        }

        // Update earliest known build times of required units
        for (final UnitType techTreeParent : unitType.requiredUnits()
                                                     .keySet()) {
            final int latestPossibleBuildTimeOfParentTechType = time - techTreeParent.buildTime();
            updateBuildTime(techTreeParent, latestPossibleBuildTimeOfParentTechType);
        }
    }

    /**
     * At the start of every game, based off of the enemies race, we know the build times for certain
     * units / items.
     */
    private void buildTimesForStartingUnits() {
        final Race enemyRace = this.game.enemy()
                                        .getRace();

        // Regardless of race, enemy will start with workers and command center
        updateBuildTime(enemyRace.getCenter(), 0);
        updateBuildTime(enemyRace.getWorker(), 0);

        // We know some additional
        if (enemyRace == Race.Zerg) {
            updateBuildTime(UnitType.Zerg_Overlord, 0);
            updateBuildTime(UnitType.Zerg_Larva, 0);
        }
    }

    public void onUnitDiscover(final Unit unit, final Integer time) {
        if (this.self.isEnemy(unit.getPlayer())) {
            final UnitType unitType = unit.getType();
            updateBuildTime(unitType, time - unitType.buildTime());
        }
    }
}
