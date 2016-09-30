package BWJSAL.DrawUtils;

import bwapi.Color;
import bwapi.CoordinateType.Enum;
import bwapi.Game;
import bwapi.TilePosition;
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

    // Bases have a 2 tile width, therefore there is 3 dividers.
    private static final int BASE_HEIGHT_TILE_DIVIDER_COUNT = 3;

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

    /**
     * Highlights the give base location by drawing a box around it
     */
    public void highlightBaseLocation(final BaseLocation baseLocation, final Color boxColor) {
        TilePosition baseTilePosition = baseLocation.getTilePosition();
        game.drawBox(Enum.Map,
                     baseTilePosition.getX() * PIXELS_IN_TILES,
                     baseTilePosition.getY() * PIXELS_IN_TILES,
                     baseTilePosition.getX() * PIXELS_IN_TILES + BASE_WIDTH_TILE_DIVIDER_COUNT * PIXELS_IN_TILES,
                     baseTilePosition.getY() * PIXELS_IN_TILES + BASE_HEIGHT_TILE_DIVIDER_COUNT * PIXELS_IN_TILES,
                     boxColor,
                     false);
    }
}
