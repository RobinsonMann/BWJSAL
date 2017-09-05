package BWJSAL.DrawUtils;

import bwapi.Color;
import bwapi.CoordinateType.Enum;
import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Position;
import bwapi.Unit;
import bwta.BaseLocation;

/**
 * Class used to add semantic meaning to game draw operations
 */
public class DrawUtils {

    private static final int PIXELS_IN_TILES = 32;

    /**
     * Tile dividers are the 'fence posts' of tiles.
     * I.e. for a base
     *   Dividers
     *   | | | |
     *   v v v v
     *    B B B
     *    B B B
     */

    // Bases have a 3 tile width, therefore there is 4 dividers.
    private static final int BASE_WIDTH_TILE_DIVIDER_COUNT = 4;

    // Bases have a 2 tile height, therefore there is 3 dividers.
    private static final int BASE_HEIGHT_TILE_DIVIDER_COUNT = 3;

    private static final int PROGRESS_BAR_WIDTH = 20;

    private static final int PROGRESS_BAR_HEIGHT = 4;

    private static final Color PROGRESS_BAR_OUTLINE_COLOR = Color.Blue;

    private static final Color PROGRESS_BAR_BACKGROUND_COLOR = new Color(0, 0, 170);

    private Game game;

    public DrawUtils(final Game game) {
        this.game = game;
    }

    /**
     * Highlights a mineral field by drawing a circle around it
     */
    public void highlightMineralField(final Unit mineral, final Color circleColor) {
        game.drawCircle(Enum.Map, mineral.getX(), mineral.getY(), 30, circleColor, false);
    }

    public void highlightGeyser(final Unit geyser, final Color highlightColor) {
        game.drawBox(Enum.Map,
                     geyser.getX() * 32,
                     geyser.getY() * 32,
                     (geyser.getX() + 4) * 32,
                     (geyser.getY() + 2) * 32,
                     highlightColor,
                     false);
    }

    /**
     * Highlights the give base location by drawing a box around it
     */
    public void highlightBaseLocation(final BaseLocation baseLocation, final Color boxColor) {
        final TilePosition baseTilePosition = baseLocation.getTilePosition();
        game.drawBox(Enum.Map,
                     baseTilePosition.getX() * PIXELS_IN_TILES,
                     baseTilePosition.getY() * PIXELS_IN_TILES,
                     baseTilePosition.getX() * PIXELS_IN_TILES + BASE_WIDTH_TILE_DIVIDER_COUNT * PIXELS_IN_TILES,
                     baseTilePosition.getY() * PIXELS_IN_TILES + BASE_HEIGHT_TILE_DIVIDER_COUNT * PIXELS_IN_TILES,
                     boxColor,
                     false);
    }

    public void highlightIslandBaseLocation(final BaseLocation baseLocation, final Color islandHighlightColor)
    {
        final Position basePosition = baseLocation.getPosition();
        game.drawCircle(Enum.Map, basePosition.getX(), basePosition.getY(), 80, islandHighlightColor, false);
    }

    public void drawProgressBar(final Position position, final double progressFraction, final Color innerBarColor) {
        final int xLeft = position.getX() - PROGRESS_BAR_WIDTH / 2;
        final int xRight = position.getX() + PROGRESS_BAR_WIDTH / 2;
        final int yBottom = position.getY() - PROGRESS_BAR_HEIGHT / 2;
        final int yTop = position.getY() + PROGRESS_BAR_HEIGHT / 2;

        // draw outline top
        game.drawLineMap(xLeft + 1, yTop, xRight - 1, yTop, PROGRESS_BAR_OUTLINE_COLOR);

        // draw outline bottom
        game.drawLineMap(xLeft + 1, yBottom, xRight - 1, yBottom, PROGRESS_BAR_OUTLINE_COLOR);

        // draw outline left
        game.drawLineMap(xLeft, yTop + 1, xLeft, yBottom, PROGRESS_BAR_OUTLINE_COLOR);

        // draw outline right
        game.drawLineMap(xRight - 1, yTop + 1, xRight - 1, yBottom, PROGRESS_BAR_OUTLINE_COLOR);

        // draw bar background
        game.drawBoxMap(xLeft + 1, yTop + 1, xRight - 1, yBottom, PROGRESS_BAR_BACKGROUND_COLOR, true);

        //Draw progress bar
        final int innerWidth = (xRight - 1) - (xLeft + 1);
        final int progressWidth = (int) (progressFraction * innerWidth);
        game.drawBoxMap(xLeft + 1, yTop + 1, xLeft + 1 + progressWidth, yBottom, innerBarColor, true);
    }
}
