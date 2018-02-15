package BWJSAL.Information;

import BWJSAL.utils.UnitBuilder;
import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import bwapi.UnitType;
import org.assertj.core.util.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static BWJSAL.utils.RaceUtils.createMockRace;
import static BWJSAL.utils.StaticFinalUtils.setStaticFinalField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class BuildTimeTrackerTest {
    private Game mockGame;
    private Player mockSelf;
    private Player mockEnemy;
    private BuildTimeTracker target;

    @Before
    public void setUp() throws Exception {
        this.mockGame = Mockito.mock(Game.class);
        this.mockSelf = Mockito.mock(Player.class);
        this.mockEnemy = Mockito.mock(Player.class);
        this.target = new BuildTimeTracker(this.mockGame);

        when(this.mockGame.enemy()).thenReturn(this.mockEnemy);
        when(this.mockGame.self()).thenReturn(this.mockSelf);
        when(this.mockSelf.isEnemy(this.mockEnemy)).thenReturn(true);

        setupUnitTypes();
    }

    @Test
    public void enemyHasBuilt_onStart_haveNotBuiltNonStartingUnit() {
        setEnemyRace(createMockRace(UnitType.Terran_Command_Center, UnitType.Terran_SCV));

        this.target.onStart();

        assertThat(this.target.enemyHasBuilt(UnitType.Terran_Marine)).isFalse();
    }

    @Test
    public void enemyHasBuilt_enemyZerg_additionalStartingUnitTypes() throws Exception {
        final Race mockZerg = createMockRace(UnitType.Zerg_Hatchery, UnitType.Zerg_Drone);
        setStaticFinalField(Race.class, "Zerg", mockZerg);

        when(this.mockGame.self().isEnemy(this.mockEnemy)).thenReturn(true);
        when(this.mockEnemy.getRace()).thenReturn(Race.Zerg);

        this.target.onStart();

        assertEnemyHasBuilt(UnitType.Zerg_Overlord, "Zerg starts with overlord");
        assertEnemyHasBuilt(UnitType.Zerg_Larva, "Zerg starts with larva");
    }

    @Test
    public void enemyHasBuilt_mockEnemyRace_hasBuiltForCenterAndWorkersTrue() throws Exception {
        final UnitType mockCenter = Mockito.mock(UnitType.class);
        final UnitType mockWorker = Mockito.mock(UnitType.class);
        final Race mockRace = createMockRace(mockCenter, mockWorker);

        when(this.mockEnemy.getRace()).thenReturn(mockRace);

        this.target.onStart();

        assertEnemyHasBuilt(mockCenter, "Race's Center UnitType always built");
        assertEnemyHasBuilt(mockWorker, "Race's Worker UnitType always built");
    }

    /**
     * Update build time
     */
    @Test
    public void onUnitDiscovery_requiredUnitsNotPreviouslyObserved_requiredUnitBuildTimesSetCorrectly() {
        setEnemyRace(createMockRace(UnitType.Protoss_Nexus, UnitType.Protoss_Probe));
        this.target.onStart();

        final int currentGameTime = 1000;
        final int dragoonBuildTime = 50;
        final int cyberneticsCoreBuildTime = 100;
        final int gatewayBuildTime = 100;

        when(UnitType.Protoss_Dragoon.buildTime()).thenReturn(dragoonBuildTime);
        when(UnitType.Protoss_Cybernetics_Core.buildTime()).thenReturn(cyberneticsCoreBuildTime);
        when(UnitType.Protoss_Gateway.buildTime()).thenReturn(gatewayBuildTime);

        this.target.onUnitDiscover(UnitBuilder.mockUnit(UnitType.Protoss_Dragoon, this.mockEnemy), currentGameTime);

        assertUnitTypeBuildTime(UnitType.Protoss_Dragoon,
                                currentGameTime - dragoonBuildTime,
                                "Latest build time for top unit is game time - build time");

        assertUnitTypeBuildTime(UnitType.Protoss_Cybernetics_Core,
                                currentGameTime - dragoonBuildTime - cyberneticsCoreBuildTime,
                                "Latest build time recursively called for required units");

        assertUnitTypeBuildTime(UnitType.Protoss_Gateway,
                                currentGameTime - dragoonBuildTime - cyberneticsCoreBuildTime - gatewayBuildTime,
                                "Latest build time recursively called for required units");
    }

    @Test
    public void onUnitDiscovery_previouslyObservedUnit_observedBuildTimeNewer_buildTimeNotUpdated() {
        setEnemyRace(createMockRace(UnitType.Protoss_Nexus, UnitType.Protoss_Probe));
        this.target.onStart();

        final int firstGameTime = 1000;
        final int secondGameTime = 10000;
        this.target.onUnitDiscover(UnitBuilder.mockUnit(UnitType.Protoss_Gateway, this.mockEnemy), firstGameTime);
        this.target.onUnitDiscover(UnitBuilder.mockUnit(UnitType.Protoss_Gateway, this.mockEnemy), secondGameTime);

        assertUnitTypeBuildTime(UnitType.Protoss_Gateway,
                                firstGameTime,
                                "build time not changed");
    }

    @Test
    public void onUnitDiscovery_observeStartingUnit_buildTimeNotUpdated() {
        setEnemyRace(createMockRace(UnitType.Terran_Command_Center, UnitType.Terran_SCV));
        this.target.onStart();

        this.target.onUnitDiscover(UnitBuilder.mockUnit(UnitType.Terran_SCV, this.mockEnemy), 10);

        assertUnitTypeBuildTime(UnitType.Terran_SCV,
                                0,
                                "build time not changed");
    }

    @Test
    public void getBuildTime_onStart_startingUnitsBuildTimeOf0() {
        final Race mockZerg = createMockRace(UnitType.Zerg_Hatchery, UnitType.Zerg_Drone);
        setStaticFinalField(Race.class, "Zerg", mockZerg);

        when(this.mockEnemy.getRace()).thenReturn(Race.Zerg);

        this.target.onStart();

        assertEnemyHasBuilt(UnitType.Zerg_Overlord, "Zerg starts with overlord");
        assertEnemyHasBuilt(UnitType.Zerg_Larva, "Zerg starts with larva");
    }

    @Test
    public void getBuildTime_unitNotBuilt() {
        setupUnitTypes();
        assertThatThrownBy(() -> this.target.getBuildTime(UnitType.Protoss_Dragoon))
                .hasMessage("Enemy has not built unitType Protoss_Dragoon");
    }

    private void setEnemyRace(final Race race) {
        when(this.mockEnemy.getRace()).thenReturn(race);
    }

    /**
     * Properties of unit types are loaded from DLLS on game start.
     * As the unit tests don't start the the game, we need to mock
     * the final constant unit types.
     */
    private void setupUnitTypes() {
        modifyUnitTypeToMock("Terran_SCV", new HashMap<>());
        modifyUnitTypeToMock("Terran_Command_Center", new HashMap<>());

        modifyUnitTypeToMock("Zerg_Drone", new HashMap<>());
        modifyUnitTypeToMock("Zerg_Hatchery", new HashMap<>());
        modifyUnitTypeToMock("Zerg_Larva", new HashMap<>());
        modifyUnitTypeToMock("Zerg_Overlord", new HashMap<>());

        modifyUnitTypeToMock("Protoss_Nexus", new HashMap<>());
        modifyUnitTypeToMock("Protoss_Probe", Maps.newHashMap(UnitType.Protoss_Nexus, 1));
        modifyUnitTypeToMock("Protoss_Pylon", Maps.newHashMap(UnitType.Protoss_Probe, 1));
        modifyUnitTypeToMock("Protoss_Gateway", Maps.newHashMap(UnitType.Protoss_Pylon, 1));
        modifyUnitTypeToMock("Protoss_Cybernetics_Core", Maps.newHashMap(UnitType.Protoss_Gateway, 1));
        modifyUnitTypeToMock("Protoss_Dragoon", Maps.newHashMap(UnitType.Protoss_Cybernetics_Core, 1));
    }

    private void modifyUnitTypeToMock(final String unitTypeName,
                                      final Map<UnitType, Integer> requiredUnits) {
        final UnitType mockDrone = Mockito.mock(UnitType.class);
        when(mockDrone.toString()).thenReturn(unitTypeName);
        when(mockDrone.requiredUnits()).thenReturn(requiredUnits);
        setStaticFinalField(UnitType.class, unitTypeName, mockDrone);
    }

    private void assertEnemyHasBuilt(final UnitType unitType, final String description) {
        assertThat(this.target.enemyHasBuilt(unitType))
                .as(description)
                .isTrue();
    }

    private void assertUnitTypeBuildTime(final UnitType unitType, final Integer expectedBuildTime, final String description) {
        assertThat(this.target.getBuildTime(unitType))
                .as(description)
                .isEqualTo(expectedBuildTime);
    }
}