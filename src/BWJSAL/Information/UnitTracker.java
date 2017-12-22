package BWJSAL.Information;

import bwapi.Game;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * On each frame, this class saves information on all visible units.
 *
 * This information can then be retrieved when the unit is no longer visible.
 *
 * It should be noted that "exists" in BWAPI terminology refers to if the unit is visible to the AI,
 * but for this classs, existence refers to it in the general sense.
 */
class UnitTracker {
    final private Map<Unit, UnitTrackingData> latestUnitTrackingData;
    final private Game game;

    public UnitTracker(final Game game) {
        this.latestUnitTrackingData = new HashMap<>();
        this.game = game;
    }

    /**
     * Retrieve the last observed position of this unit.
     */
    public Position getLastPosition(final Unit unit) {
        return extractTrackedData(unit,
                                  Unit::getPosition,
                                  UnitTrackingData::getPosition,
                                  Position.None);
    }

    /**
     * Retrieve the last known owner of this unit.
     */
    public Player getPlayer(final Unit unit) {
        return extractTrackedData(unit,
                                  Unit::getPlayer,
                                  UnitTrackingData::getPlayer,
                                  null);
    }

    /**
     * Retrieve the last known type of this unit.
     */
    public UnitType getUnitType(final Unit unit) {
        return extractTrackedData(unit,
                                  Unit::getType,
                                  UnitTrackingData::getType,
                                  UnitType.None);
    }

    /**
     * Retrieve if (to the best of the information manager's knowledge) this unit still exists.
     */
    public boolean doesUnitExist(final Unit unit) {
        return extractTrackedData(unit,
                                  Unit::exists,
                                  UnitTrackingData::doesUnitExist,
                                  false);
    }

    /**
     * Retrieve the time (as a frame number) this unit was last observed.
     */
    public Optional<Integer> getLastSeenTime(final Unit unit) {
        return extractTrackedData(unit,
                                  unitThatExists -> Optional.of(this.game.getFrameCount()),
                                  unitTrackingData -> Optional.ofNullable(unitTrackingData.getLastSeenTime()),
                                  Optional.empty());
    }

    /**
     * Common pattern that firsts trys to extract required data from the unit, if that is not
     * possible it will try to extract required data from tracked information. If this isn't
     * possible, falls back to default value.
     */
    private <T> T extractTrackedData(final Unit unit,
                                     final Function<Unit, T> dataToExtractIfUnitExists,
                                     final Function<UnitTrackingData, T> dataToExtractIfUnitTracked,
                                     final T defaultValue) {
        if (unit.exists()) {
            return dataToExtractIfUnitExists.apply(unit);
        }

        if (this.latestUnitTrackingData.containsKey(unit)) {
            return dataToExtractIfUnitTracked.apply(this.latestUnitTrackingData.get(unit));
        }

        return defaultValue;
    }


    public void onFrame(final int frameCount) {
        for (final Map.Entry<Unit, UnitTrackingData> trackedUnitPair : latestUnitTrackingData.entrySet()) {
            final Unit trackedUnit = trackedUnitPair.getKey();
            if (trackedUnit.exists()) {
                trackedUnitPair.getValue()
                               .update(trackedUnit, frameCount);
            }
        }
    }

    public void onUnitDiscover(final Unit discoveredUnit, final int frameCount) {
        final UnitTrackingData unitTrackingData = new UnitTrackingData();
        unitTrackingData.update(discoveredUnit, frameCount);
        this.latestUnitTrackingData.put(discoveredUnit, unitTrackingData);
    }

    public void onUnitDestroy(final Unit destroyedUnit, final int frameCount) {
        if (this.latestUnitTrackingData.containsKey(destroyedUnit)) {
            this.latestUnitTrackingData.get(destroyedUnit)
                                       .unitDestroyed(destroyedUnit, frameCount);
        }
    }
}
