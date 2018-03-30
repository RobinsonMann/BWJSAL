package BWJSAL.buildorder;

import BWJSAL.utils.UnitBuilder;
import BWJSAL.utils.UnitTypeUtils;
import bwapi.UnitType;
import org.junit.Before;
import org.junit.Test;

import static BWJSAL.utils.UnitTypeUtils.mockUnitTypeFromOfflineData;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class BuildOrderSolverAcceptanceTest {

    BuildOrderSolver buildOrderSolver;

    @Before
    public void setUp() {

    }

    @Test
    public void basicSanityTest_buildSingleScv() {
        mockUnitTypeFromOfflineData("Terran_SCV");
        mockUnitTypeFromOfflineData("Terran_Command_Center");

assertThat(UnitType.Terran_SCV.mineralPrice()).isEqualTo(50);

        UnitBuilder.mockUnit(UnitType.Terran_Command_Center);
    }

    @Test
    public void basicSanityTest_buildTwoScv() {

    }
}