package BWJSAL.Information;

import bwapi.Game;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class InformationManagerTest {

    private Game mockGame;

    private InformationManager target;

    @Before
    public void setUp() {
        this.mockGame = Mockito.mock(Game.class);
        this.target = new InformationManager(this.mockGame);
    }



    @Test
    public void enemyHasBuilt_typeNotObserved() {
        assertThat(this.target.enemyHasBuilt(UnitType.Protoss_Zealot)).isFalse();
    }

    @Test
    public void enemyHasBuilt_typeDirectlyObserved() {

    }

    @Test
    public void enemyHasBuilt_typeIndirectlyObserved() {

    }

    @Test
    public void enemyHasBuilt_enemyZerg_seenOverloadAndHatcheryAtStart() {

    }

    @Test
    public void buildTimeForStartingUnits_playerTerran() {
    }


}