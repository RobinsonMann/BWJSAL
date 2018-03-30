package BWJSAL.Information;

import BWJSAL.bwta.BwtaWrapper;
import BWJSAL.utils.UnitBuilder;
import bwapi.Game;
import bwapi.Player;
import bwapi.Race;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BaseLocation;
import org.junit.Before;
import org.junit.Test;

import static BWJSAL.utils.BwtaUtils.setNearestBaseLocationOnMock;
import static BWJSAL.utils.BwtaUtils.setSelfStartingLocationOnMock;
import static BWJSAL.utils.BwtaUtils.setStartingLocationsOnMock;
import static BWJSAL.utils.RaceUtils.createMockRace;
import static BWJSAL.utils.UnitTypeUtils.mockResourceDepotUnitType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests that try to replicate real usage scenarios for the Information Manager class.
 */
public class InformationManagerAcceptanceTest {
    private Game game;
    private Player self;
    private Player enemy;
    private BwtaWrapper bwtaWrapper;

    private InformationManager target;

    @Before
    public void setUp() {
        this.game = mock(Game.class);
        this.self = mock(Player.class);
        this.enemy = mock(Player.class);
        this.bwtaWrapper = mock(BwtaWrapper.class);
        this.target = new InformationManager(this.game, this.bwtaWrapper);

        when(this.game.self()).thenReturn(this.self);
        when(this.game.enemy()).thenReturn(this.enemy);
        when(this.self.isEnemy(this.enemy)).thenReturn(true);
    }

    /**
     * Verify that enemy base location manually set via setBaseAsEnemy is removed
     * when a command center that was later observed is destroyed.
     *
     * This test verifies that base locations can be tied to resource depots even
     * if the base location was already marked as an enemy location.
     */
    @Test
    public void resourceDepotToBaseLocationMappingCorrectlyKeptUpToDate() {
        final Race enemyRace = createMockRace(mock(UnitType.class), mock(UnitType.class));
        when(this.enemy.getRace()).thenReturn(enemyRace);

        final BaseLocation selfStartingLocation = mock(BaseLocation.class);
        final BaseLocation enemyStartingLocation = mock(BaseLocation.class);
        final BaseLocation otherStartingLocation = mock(BaseLocation.class);

        setStartingLocationsOnMock(this.bwtaWrapper, selfStartingLocation, enemyStartingLocation, otherStartingLocation);
        setSelfStartingLocationOnMock(this.bwtaWrapper, this.self, selfStartingLocation);

        this.target.onStart();

        assertThat(this.target.getEnemyBases())
                .as("no known enemy bases")
                .isEmpty();

        // manually set base as enemy
        this.target.setBaseAsEnemy(enemyStartingLocation);

        assertThat(this.target.getEnemyBases())
                .as("sanctity test that setBaseAsEnemy actual sets the base as an enemy base")
                .containsExactly(enemyStartingLocation);

        // observe enemy command center in enemy base
        final TilePosition commandCenterPosition = mock(TilePosition.class);
        setNearestBaseLocationOnMock(this.bwtaWrapper, commandCenterPosition, enemyStartingLocation);
        final Unit commandCenter = UnitBuilder.mockUnit(this.enemy, mockResourceDepotUnitType(), commandCenterPosition);
        this.target.onUnitDiscover(commandCenter);

        // command center destroyed
        this.target.onUnitDestroy(commandCenter);

        // verify location is not enemy
        assertThat(this.target.getEnemyBases())
                .as("because all bases mapped to base location have been destroyed, no more bases for enemy")
                .isEmpty();
    }

    /**
     * Verify that enemy base locations are not removed if multiple resource depots are observed at the same location.
     */
    @Test
    public void multipleResourceDepotsAtBaseOneDestroyed_baseLocationStillEnemies() {
        final Race enemyRace = createMockRace(mock(UnitType.class), mock(UnitType.class));
        when(this.enemy.getRace()).thenReturn(enemyRace);

        final BaseLocation selfStartingLocation = mock(BaseLocation.class);
        final BaseLocation enemyStartingLocation = mock(BaseLocation.class);
        final BaseLocation otherStartingLocation = mock(BaseLocation.class);

        setStartingLocationsOnMock(this.bwtaWrapper, selfStartingLocation, enemyStartingLocation, otherStartingLocation);
        setSelfStartingLocationOnMock(this.bwtaWrapper, this.self, selfStartingLocation);

        this.target.onStart();

        assertThat(this.target.getEnemyBases())
                .as("no known enemy bases")
                .isEmpty();

        // observe multiple resource depots in enemy base location
        final TilePosition firstResourceDepotPosition = mock(TilePosition.class);
        setNearestBaseLocationOnMock(this.bwtaWrapper, firstResourceDepotPosition, enemyStartingLocation);
        final Unit firstResourceDepot = UnitBuilder.mockUnit(this.enemy, mockResourceDepotUnitType(), firstResourceDepotPosition);
        this.target.onUnitDiscover(firstResourceDepot);

        final TilePosition secondResourceDepotPosition = mock(TilePosition.class);
        setNearestBaseLocationOnMock(this.bwtaWrapper, secondResourceDepotPosition, enemyStartingLocation);
        final Unit secondResourceDepot = UnitBuilder.mockUnit(this.enemy, mockResourceDepotUnitType(), firstResourceDepotPosition);
        this.target.onUnitDiscover(secondResourceDepot);

        // first resource depot destroyed
        this.target.onUnitDestroy(firstResourceDepot);

        // verify location is still enemy
        assertThat(this.target.getEnemyBases())
                .as("selfStartingLocation still enemy base")
                .containsExactly(enemyStartingLocation);
    }
}