package BWJSAL.Information;

import BWJSAL.bwta.BwtaWrapper;
import BWJSAL.utils.UnitBuilder;
import bwapi.Game;
import bwapi.Player;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BaseLocation;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static BWJSAL.utils.UnitTypeUtils.replaceUnitTypeWithMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EnemyBaseTrackerTest {
    private Game mockGame;
    private BwtaWrapper mockBwta;
    private Player mockSelf;
    private Player mockEnemy;

    private EnemyBaseTracker target;

    @Before
    public void setUp() {
        this.mockGame = Mockito.mock(Game.class);
        this.mockBwta = Mockito.mock(BwtaWrapper.class);
        this.mockSelf = Mockito.mock(Player.class);
        this.mockEnemy = Mockito.mock(Player.class);
        this.target = new EnemyBaseTracker(this.mockGame,
                                           this.mockBwta);

        when(this.mockGame.self()).thenReturn(this.mockSelf);
        when(this.mockSelf.isEnemy(this.mockEnemy)).thenReturn(true);
    }

    @Test
    public void onStart_twoBases_enemyBaseDeducible() {
        final BaseLocation selfLocation = Mockito.mock(BaseLocation.class);
        final BaseLocation enemyLocation = Mockito.mock(BaseLocation.class);

        when(this.mockBwta.getStartLocations()).thenReturn(Lists.newArrayList(selfLocation,
                                                                              enemyLocation));
        when(this.mockBwta.getStartLocation(mockSelf)).thenReturn(selfLocation);

        this.target.onStart();

        assertThat(this.target.getEnemyBases())
                .as("Enemy base deduce from onStart")
                .containsExactly(enemyLocation);
    }

    @Test
    public void onUnitDiscover_baseDeducdedThenFound_onlyOneBaseLocatios() {
        // First we find a building, and deducde that the base is present in the same region
        // After this ic omplete, we then discover that

    }

    @Test
    public void onUnitDiscover_firstBuildingObservedInsidePossibleEnemyStartingLocation_markBaseLocationAsEnemies() {
        final Unit observedUnit = UnitBuilder.mockUnit(UnitType.Protoss_Pylon, this.mockEnemy);
        replaceUnitTypeWithMock("Protoss_Pylon");
    }


    @Test
    public void onStart_fourBases_enemyBaseNotDeducible() {
        when(this.mockBwta.getStartLocations()).thenReturn(Lists.newArrayList(Mockito.mock(BaseLocation.class),
                                                                              Mockito.mock(BaseLocation.class),
                                                                              Mockito.mock(BaseLocation.class),
                                                                              Mockito.mock(BaseLocation.class)));

        this.target.onStart();

        assertThat(this.target.getEnemyBases())
                .as("Enemy base cannot be deduced from onStart")
                .isEmpty();
    }
}