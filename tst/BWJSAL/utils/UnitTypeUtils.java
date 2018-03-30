package BWJSAL.utils;

import bwapi.UnitType;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static BWJSAL.utils.StaticFinalUtils.setStaticFinalField;
import static org.mockito.Mockito.when;

public class UnitTypeUtils {
    /**
     * Replaces the static final UnitType.unitTypeName with a mock UnitType.
     * This is needed as UnitType values are loaded from DLLs, and we do not want to do this
     * for our unit tests.
     */
    public static void replaceUnitTypeWithMock(final String unitTypeName) {
        final UnitType mockUnitType = Mockito.mock(UnitType.class);
        setStaticFinalField(UnitType.class, unitTypeName, mockUnitType);
    }

    /**
     * Create a mock instance of a unit type that represents a resource depot.
     *
     * @return Mock instance of UnitType where isResourceDepot() returns true.
     */
    public static UnitType mockResourceDepotUnitType() {
        final UnitType resourceDepot = Mockito.mock(UnitType.class);
        when(resourceDepot.isResourceDepot()).thenReturn(true);
        return resourceDepot;
    }


    public static void mockUnitTypeFromOfflineData(final String unitTypeName) {
        final OfflineUnitType offlineData = OfflineUnitType.getOfflineUnitType(unitTypeName);
        final UnitType mockUnitType = Mockito.mock(UnitType.class);
        when(mockUnitType.mineralPrice()).thenReturn(offlineData.mineralPrice);
        when(mockUnitType.gasPrice()).thenReturn(offlineData.vespeneGasPrice);
        when(mockUnitType.supplyRequired()).thenReturn(offlineData.supplyRequired);
        setStaticFinalField(UnitType.class, unitTypeName, mockUnitType);
    }


    private enum OfflineUnitType {
        Offline_Terran_SCV("Terran_SCV", 50, 0, 2),
        Offline_Terran_Command_Center("Terran_Command_Center", 400, 0, 0);

        String realUnitTypeName;
        int mineralPrice;
        int vespeneGasPrice;
        int supplyRequired;

        OfflineUnitType(String realUnitTypeName, int mineralPrice, int vespeneGasPrice, int supplyRequired) {
            this.realUnitTypeName = realUnitTypeName;
            this.mineralPrice = mineralPrice;
            this.vespeneGasPrice = vespeneGasPrice;
            this.supplyRequired = supplyRequired;
        }

        static Map<String, OfflineUnitType> unitTypeMap = null;

        public static OfflineUnitType getOfflineUnitType(String realUnitType) {
            if (unitTypeMap == null) {
                unitTypeMap = new HashMap<>();

                for (OfflineUnitType offlineUnitType : OfflineUnitType.values()) {
                    unitTypeMap.put(offlineUnitType.realUnitTypeName, offlineUnitType);
                }
            }
            return unitTypeMap.get(realUnitType);
        }
    }
}
