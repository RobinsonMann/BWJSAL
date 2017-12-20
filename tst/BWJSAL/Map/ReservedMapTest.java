package BWJSAL.Map;

import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class ReservedMapTest {
    private static final UnitType DEFAULT_UNIT_TYPE = UnitType.None;
    private static final int MAP_TILE_WIDTH = 4;
    private static final int MAP_TILE_HEIGHT = 5;

    private Game mockGame;

    private ReservedMap target;

    @Before
    public void setUp() {
        this.mockGame = Mockito.mock(Game.class);
        when(this.mockGame.mapWidth()).thenReturn(MAP_TILE_WIDTH);
        when(this.mockGame.mapHeight()).thenReturn(MAP_TILE_HEIGHT);
        this.target = new ReservedMap(this.mockGame);
    }

    @Test
    public void isReserved_tilePosition_notReserved() {
        assertThat(this.target.isReserved(new TilePosition(2, 2))).isFalse();
    }

    @Test
    public void isReserved_xy_notReserved() {
        assertThat(this.target.isReserved(2, 2)).isFalse();
    }

    @Test
    public void isReserved_tilePosition_reserved() {
        final TilePosition tilePosition = new TilePosition(2, 2);
        this.target.reserveTiles(tilePosition);
        assertThat(this.target.isReserved(tilePosition)).isTrue();
    }

    @Test
    public void isReserved_xy_reserved() {
        this.target.reserveTiles(2, 2);
        assertThat(this.target.isReserved(2, 2)).isTrue();
    }

    @Test
    public void getReservedType_tilePosition_tileReserved() {
        final TilePosition tilePosition = new TilePosition(2, 2);
        this.target.reserveTiles(tilePosition, UnitType.Terran_Command_Center);
        assertThat(this.target.getReservedType(tilePosition)).isEqualTo(UnitType.Terran_Command_Center);
    }

    @Test
    public void getReservedType_xy_tileReserved() {
        final int x = 2, y = 2;
        this.target.reserveTiles(x, y, UnitType.Terran_Command_Center);
        assertThat(this.target.getReservedType(x, y)).isEqualTo(UnitType.Terran_Command_Center);
    }

    @Test
    public void getReservedType_tilePosition_notReserved() {
        assertThat(this.target.getReservedType(new TilePosition(2, 2))).isNull();
    }

    @Test
    public void getReservedType_xy_notReserved() {
        final int x = 2, y = 2;
        assertThat(this.target.getReservedType(2, 2)).isNull();
    }

    @Test
    public void reserveTiles_tilePosition_defaultUnitTypeIsNone() {
        this.target.reserveTiles(new TilePosition(0, 0));
        assertTileReservedType(0, 0, DEFAULT_UNIT_TYPE);
    }

    @Test
    public void reserveTiles_xy_defaultUnitTypeIsNone() {
        this.target.reserveTiles(0, 0);
        assertTileReservedType(0, 0, DEFAULT_UNIT_TYPE);
    }

    @Test
    public void reserveTiles_tilePositionRectangle_defaultUnitTypeIsNone() {
        final int width = 2, height = 3;
        this.target.reserveTiles(new TilePosition(0, 0), width, height);
        assertTileReservedType(0, 0, DEFAULT_UNIT_TYPE, width, height);
    }

    @Test
    public void reserveTiles_xyRectangle_defaultUnitTypeIsNone() {
        final int xPosition = 1, yPosition = 1, width = 2, height = 3;
        this.target.reserveTiles(xPosition, yPosition, width, height);
        assertTileReservedType(xPosition, yPosition, DEFAULT_UNIT_TYPE, width, height);
    }

    @Test
    public void reserveTiles_tilePosition_reserveSingleTile() {
        final TilePosition position = new TilePosition(0, 0);
        assertThatTileNotReserved(position);
        this.target.reserveTiles(position, UnitType.Terran_Command_Center);
        assertThatOnlyTileReserved(position);
        assertTileReservedType(position, UnitType.Terran_Command_Center);
    }

    @Test
    public void reserveTiles_xy_reserveSingleTile() {
        final int xPosition = 0, yPosition = 0;
        assertThatTileNotReserved(xPosition, yPosition);
        this.target.reserveTiles(xPosition, yPosition, UnitType.Terran_Command_Center);
        assertThatOnlyTileReserved(xPosition, yPosition);
        assertTileReservedType(xPosition, yPosition, UnitType.Terran_Command_Center);
    }

    @Test
    public void reserveTiles_tilePosition_customWidth() {
        final TilePosition tilePosition = new TilePosition(1, 1);
        final int width = 2, height = 3;
        this.target.reserveTiles(tilePosition, UnitType.Terran_Barracks, width, height);
        assertThatOnlyRectangleReserved(tilePosition, width, height);
        assertTileReservedType(tilePosition, UnitType.Terran_Barracks, width, height);
    }

    @Test
    public void reserveTiles_xy_customWidth() {
        final int xPosition = 1, yPosition = 1, width = 2, height = 3;
        this.target.reserveTiles(xPosition, yPosition, UnitType.Terran_Barracks, width, height);
        assertThatOnlyRectangleReserved(xPosition, yPosition, width, height);
        assertTileReservedType(xPosition, yPosition, UnitType.Terran_Barracks, width, height);
    }

    @Test
    public void freeTiles_tilePosition_freeSingleTile() {
        final TilePosition tilePosition = new TilePosition(1, 2);
        this.target.reserveTiles(tilePosition);
        assertThatTileReserved(tilePosition);
        this.target.freeTiles(tilePosition);
        assertThatTileNotReserved(tilePosition);
    }

    @Test
    public void freeTiles_xy_freeSingleTile() {
        final int xPosition = 1, yPosition = 1;
        this.target.reserveTiles(xPosition, yPosition);
        assertThatTileReserved(xPosition, yPosition);
        this.target.freeTiles(xPosition, yPosition);
        assertThatTileNotReserved(xPosition, yPosition);
    }

    @Test
    public void freeTiles_tilePosition_freeRectangle() {
        final TilePosition tilePosition = new TilePosition(1, 2);
        final int width = 2, height = 2;
        this.target.reserveTiles(tilePosition, width, height);
        assertThatRectangleReserved(tilePosition, width, height);
        this.target.freeTiles(tilePosition, width, height);
        assertThatRectangleNotReserved(tilePosition, width, height);
    }

    @Test
    public void freeTiles_xy_freeRectangle() {
        final int xPosition = 1, yPosition = 1;
        final int width = 2, height = 2;
        this.target.reserveTiles(xPosition, yPosition, width, height);
        assertThatRectangleReserved(xPosition, yPosition, width, height);
        this.target.freeTiles(xPosition, yPosition, width, height);
        assertThatRectangleNotReserved(xPosition, yPosition, width, height);
    }

    @Test
    public void canBuildHere_gameCanBuildHereFalse() {
        final TilePosition positionToBuild = new TilePosition(2, 2);
        final Unit builder = Mockito.mock(Unit.class);
        final UnitType typeToBuild = Mockito.mock(UnitType.class);

        verifyNoMoreInteractions(typeToBuild);

        when(this.mockGame.canBuildHere(positionToBuild, typeToBuild, builder)).thenReturn(false);

        assertThat(this.target.canBuildHere(builder, positionToBuild, typeToBuild)).isFalse();

        verify(this.mockGame).canBuildHere(positionToBuild, typeToBuild, builder);
    }

    @Test
    public void canBuildHere_partialTilesReserved() {
        final TilePosition positionToBuild = new TilePosition(2, 2);
        final Unit builder = Mockito.mock(Unit.class);
        final UnitType typeToBuild = Mockito.mock(UnitType.class);
        when(typeToBuild.tileWidth()).thenReturn(2);
        when(typeToBuild.tileHeight()).thenReturn(2);

        when(this.mockGame.canBuildHere(positionToBuild, typeToBuild, builder)).thenReturn(true);

        this.target.reserveTiles(3, 3);

        assertThat(this.target.canBuildHere(builder, positionToBuild, typeToBuild)).isFalse();

        verify(this.mockGame).canBuildHere(positionToBuild, typeToBuild, builder);
        verify(typeToBuild, times(1)).tileWidth();
        verify(typeToBuild, times(1)).tileHeight();
    }

    @Test
    public void canBuildHere_blockingTilesWereReservedAndFreed() {
        final TilePosition positionToBuild = new TilePosition(2, 2);
        final Unit builder = Mockito.mock(Unit.class);
        final UnitType typeToBuild = Mockito.mock(UnitType.class);
        when(typeToBuild.tileWidth()).thenReturn(2);
        when(typeToBuild.tileHeight()).thenReturn(2);

        when(this.mockGame.canBuildHere(positionToBuild, typeToBuild, builder)).thenReturn(true);

        // Reserve a tile that blocks building placement
        this.target.reserveTiles(3, 3);

        // Verify that tile does block placement
        assertThat(this.target.canBuildHere(builder, positionToBuild, typeToBuild)).isFalse();

        // Free blocking tile
        this.target.freeTiles(3, 3);

        // Verify that we can now place
        assertThat(this.target.canBuildHere(builder, positionToBuild, typeToBuild)).isTrue();

        verify(this.mockGame, times(2)).canBuildHere(positionToBuild, typeToBuild, builder);
        verify(typeToBuild, times(2)).tileWidth();
        verify(typeToBuild, times(2)).tileHeight();
    }

    /**
     * Assert that target shows the given tile being reserved.
     */
    public void assertThatTileReserved(final TilePosition tilePosition) {
        assertThatTileReserved(tilePosition.getX(), tilePosition.getY());
    }

    /**
     * Assert that target shows the given tile being reserved.
     */
    public void assertThatTileReserved(final int x, final int y) {
        assertThat(this.target.isReserved(x, y))
                .as(String.format("Tile (%d, %d) should be reserved", x, y))
                .isTrue();
    }

    /**
     * Assert that target shows the given tile being reserved.
     */
    public void assertThatTileNotReserved(final TilePosition tilePosition) {
        assertThatTileNotReserved(tilePosition.getX(), tilePosition.getY());
    }

    /**
     * Assert that target shows the given tile being reserved.
     */
    public void assertThatTileNotReserved(final int x, final int y) {
        assertThat(this.target.isReserved(x, y))
                .as(String.format("Tile (%d, %d) should not be reserved", x, y))
                .isFalse();
    }

    /**
     * Assert that target shows the given tile is reserved, and all other tiles are not reserved.
     */
    public void assertThatOnlyTileReserved(final TilePosition tilePosition) {
        assertThatOnlyTileReserved(tilePosition.getX(), tilePosition.getY());
    }

    /**
     * Assert that target shows the given tile is reserved, and all other tiles are not reserved.
     */
    public void assertThatOnlyTileReserved(final int x, final int y) {
        assertThatTileReserved(x, y);
        assertThatOutsideRectangleNotReserved(x, y, 1, 1);
    }

    /**
     * Assert that target shows all tiles inside of the given rectangle are reserved.
     */
    public void assertThatRectangleReserved(final TilePosition tilePosition, final int width, final int height) {
        assertThatRectangleReserved(tilePosition.getX(), tilePosition.getY(), width, height);
    }

    /**
     * Assert that target shows all tiles inside of the given rectangle are reserved.
     */
    public void assertThatRectangleReserved(final int x, final int y, final int width, final int height) {
        for (int posX = x; posX < x + width; posX++) {
            for (int posY = y; posY < y + height; posY++) {
                assertThatTileReserved(posX, posY);
            }
        }
    }

    /**
     * Assert that target shows all tiles inside of the given rectangle are not reserved.
     */
    public void assertThatRectangleNotReserved(final TilePosition tilePosition, final int width, final int height) {
        assertThatRectangleNotReserved(tilePosition.getX(), tilePosition.getY(), width, height);
    }

    /**
     * Assert that target shows all tiles inside of the given rectangle are not reserved.
     */
    public void assertThatRectangleNotReserved(final int x, final int y, final int width, final int height) {
        for (int posX = x; posX < x + width; posX++) {
            for (int posY = y; posY < y + height; posY++) {
                assertThatTileNotReserved(posX, posY);
            }
        }
    }

    /**
     * Assert that target shows all tiles outside of the given rectangle are not reserved.
     */
    public void assertThatOutsideRectangleNotReserved(final int x, final int y, final int width, final int height) {
        for (int posX = 0; posX < MAP_TILE_WIDTH; posX++) {
            if (x <= posX && posX < x + width) {
                continue;
            }

            for (int posY = 0; posY < MAP_TILE_HEIGHT; posY++) {
                if (y <= posY && posY < y + height) {
                    continue;
                }

                assertThatTileNotReserved(posX, posY);
            }
        }
    }

    /**
     * Assert that target shows only the given rectangle being reserved.
     */
    public void assertThatOnlyRectangleReserved(final TilePosition tilePosition, final int width, final int height) {
        assertThatOnlyRectangleReserved(tilePosition.getX(), tilePosition.getY(), width, height);
    }

    /**
     * Assert that target shows only the given rectangle being reserved.
     */
    public void assertThatOnlyRectangleReserved(final int x, final int y, final int width, final int height) {
        assertThatRectangleReserved(x, y, width, height);
        assertThatOutsideRectangleNotReserved(x, y, width, height);
    }

    /**
     * Assert that target shows the given UnitType reserving the x,y position.
     */
    public void assertTileReservedType(final TilePosition tilePosition, final UnitType unitType) {
        assertTileReservedType(tilePosition.getX(), tilePosition.getY(), unitType);
    }

    /**
     * Assert that target shows the given UnitType reserving the rectangle starting at x,y position
     */
    public void assertTileReservedType(final TilePosition tilePosition,
                                       final UnitType unitType,
                                       final int width,
                                       final int height) {
        assertTileReservedType(tilePosition.getX(), tilePosition.getY(), unitType, width, height);
    }

    /**
     * Assert that target shows the given UnitType reserving the rectangle starting at x,y position
     */
    public void assertTileReservedType(final int x,
                                       final int y,
                                       final UnitType unitType,
                                       final int width,
                                       final int height) {
        for (int posX = x; posX < x + width; posX++) {
            for (int posY = y; posY < y + height; posY++) {
                assertTileReservedType(posX, posY, unitType);
            }
        }
    }

    /**
     * Assert that target shows the given UnitType reserving the x,y position.
     */
    public void assertTileReservedType(final int x, final int y, final UnitType unitType) {
        assertThat(this.target.getReservedType(x, y))
                // UnitType.toString isn't linked, so error message cannot contain what
                // unit type was present instead of the given unitType.
                .overridingErrorMessage(String.format("Tile (%d, %d) is reserved by the wrong type", x, y))
                .isEqualTo(unitType);
    }
}