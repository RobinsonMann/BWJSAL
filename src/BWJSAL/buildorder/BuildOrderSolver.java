package BWJSAL.buildorder;

import bwapi.Unit;
import bwapi.UnitType;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BuildOrderSolver {

    /**
     *
     * @param unitTypesToBuild Each entry in this list will be built once. Order does not matter.
     * @param productionFaciltiies Used to calculate
     * @param resourceCenters Used to calculate mineral gathering rates
     * @return
     */
    public BuildOrderSolution createFastestBuildOrder(List<UnitType> unitTypesToBuild,
                                                      List<ProductionFacility> productionFaciltiies,
                                                      List<ResourceCenter> resourceCenters) {
        return null;
    }

    // Key frame

    public BuildOrderSolution createFastestBuildOrder(SimplifiedGameState asd) {
        // At this frame, calculate if we can build a target unit

        // If so, create new branch,

        // If not, continue until next key frame
        // - Calculate next key frame time

        // -

        return null;

    }

    public class SimplifiedGameState {
        int gameTime;

        double minerals;
        double vespeneGas;
        double freeSupply;

        double mineralGatheringRate;
        double vespeneGasGatheringRate;
    }

    public Function<SimplifiedGameState, SimplifiedGameState> workerSpawnAtProductionFacility(Unit unit) {
        return (SimplifiedGameState game) -> { return game; };
    }

    public class ProductionFacility {
        public UnitType facilityType;
        public Integer timeUntilAvaliable;
        public Optional<Integer> avaliableLarva;
    }
    
    public class ResourceCenter {

    }
    
    public class Mineral {
        public Integer remainingMineral;
    }
}
