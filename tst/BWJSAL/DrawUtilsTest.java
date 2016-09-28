package BWJSAL;

import BWJSAL.DrawUtils.DrawUtils;
import bwapi.Color;
import bwapi.CoordinateType.Enum;
import bwapi.Game;
import bwapi.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DrawUtilsTest {

    private static final int MINERAL_X = 123;
    private static final int MINERAL_Y = 456;

    @Mock
    public Unit mineral;

    @Mock
    public Unit baseLocation;

    @Mock
    public Color color;

    @Mock
    public Game game;

    @InjectMocks
    public DrawUtils target;

    @Before
    public void setup() {
        when(mineral.getX()).thenReturn(MINERAL_X);
        when(mineral.getY()).thenReturn(MINERAL_Y);
    }

    @Test
    public void testDrawCircleAroundMineralTest() {
        // Test
        target.highlightMineralField(mineral, color);

        // Verify dependencies were called correctly
        verify(game).drawCircle(Enum.Map, MINERAL_X, MINERAL_Y, 30, color, false);
    }
}
