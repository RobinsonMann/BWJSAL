package BWJSAL.building.placement;

import BWJSAL.Map.ReservedMap;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

/**
 * Interface for building placement algorithms.
 */
public interface BuildingPlacer {
    public TilePosition findBuildLocation(ReservedMap reservedMap, UnitType unitType, TilePosition seedLocation, Unit builder);
}
