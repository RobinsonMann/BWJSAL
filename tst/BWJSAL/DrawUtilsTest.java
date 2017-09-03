package BWJSAL;

import BWJSAL.DrawUtils.DrawUtils;
import bwapi.Color;
import bwapi.CoordinateType.Enum;
import bwapi.Game;
import bwapi.Position;
import bwapi.Unit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;

@RunWith(MockitoJUnitRunner.class)
public class DrawUtilsTest {

    private static final int MINERAL_X = 123;
    private static final int MINERAL_Y = 456;

    private static final int POSITION_X = 100;
    private static final int POSITION_Y = 500;

    @Mock
    public Unit mineral;

    @Mock
    public Unit baseLocation;

    @Mock
    public Position position;

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

        when(position.getX()).thenReturn(POSITION_X);
        when(position.getY()).thenReturn(POSITION_Y);
    }

    @Test
    public void drawCircleAroundMineralTest() {
        // Test
        target.highlightMineralField(mineral, color);

        // Verify dependencies were called correctly
        verify(game).drawCircle(Enum.Map, MINERAL_X, MINERAL_Y, 30, color, false);
    }

    @Test
    public void drawProgressBarTest() {
        target.drawProgressBar(position, 0.5, color);

        // Verify top outline
        verify(game).drawLineMap(POSITION_X - 9, POSITION_Y + 2, POSITION_X + 9, POSITION_Y + 2, Color.Blue);

        // Verify bottom outline
        verify(game).drawLineMap(POSITION_X - 9, POSITION_Y - 2, POSITION_X + 9, POSITION_Y - 2, Color.Blue);

        // Verify left outline
        verify(game).drawLineMap(POSITION_X - 10, POSITION_Y + 3, POSITION_X - 10, POSITION_Y - 2, Color.Blue);

        // Verify right outline
        verify(game).drawLineMap(POSITION_X + 9, POSITION_Y + 3, POSITION_X + 9, POSITION_Y - 2, Color.Blue);

        // Verify bar background
        verify(game).drawBoxMap(eq(POSITION_X - 9), eq(POSITION_Y + 3), eq(POSITION_X + 9), eq(POSITION_Y - 2), any(), eq(true));

        // Verify progress bar
        verify(game).drawBoxMap(POSITION_X - 9, POSITION_Y + 3, POSITION_X, POSITION_Y - 2, color, true);
    }
}
