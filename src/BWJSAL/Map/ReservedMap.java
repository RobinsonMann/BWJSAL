package BWJSAL.Map;

import bwapi.Game;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

import static BWJSAL.ExceptionUtils.NonNullUtil.nonNull;

/**
 * Manages map tile allocation. Provides basic methods related to allocation, freeing, and checking allocation status.
 * This logic is required by most building placement algorithms to prevent placing a building on a tile position that
 * is scheduled to be built on but construction has not started yet.
 *
 * When reserving a tile, you can optionally provide a UnitType to reserve the tile with. You can then retrieve this
 * UnitType later.
 *
 * If just an (x,y) coordinate or TilePosition is provided to reserve/free a tile, then only that tile will be reserved/freed.
 * When width and height are provided, then the following tiles will be reserved/freed:
 * (x, y), (x + 1, y), ..., (x + width, y), (x, y + 1), ..., (x, y + height), ... (x + width, y + height)
 */
public class ReservedMap {
    /**
     * Array of size [mapWidth][MapHeight] used to track reserved tiles.
     *
     * If (x,y) is set to null, the position is not reserved. If set to any other unit type, the position is reserved.
     **/
    private final UnitType[][] reservedMap;

    private final Game game;

    /**
     * If no UnitType is provided during reserveTiles, this type will be used.
     */
    private static final UnitType DEFAULT_RESERVE_TYPE = UnitType.None;

    /**
     * Create new instance of ReserveMap.
     * Reference to game will be saved.
     */
    public ReservedMap(final Game game) {
        this.reservedMap = new UnitType[game.mapWidth()][game.mapHeight()];
        this.game = game;
    }

    /**
     * Reserve the given tile position for UnitType.None.
     */
    public void reserveTiles(final TilePosition position) {
        reserveTiles(position, DEFAULT_RESERVE_TYPE);
    }

    /**
     * Reserve a rectangle of tile positions starting at given tile position for UnitType.None.
     */
    public void reserveTiles(final TilePosition position, final int width, final int height) {
        reserveTiles(position, DEFAULT_RESERVE_TYPE, width, height);
    }

    /**
     * Reserve the given tile position (give as (x,y) coordinate) for UnitType.None.
     */
    public void reserveTiles(final int positionX, final int positionY) {
        reserveTiles(positionX, positionY, DEFAULT_RESERVE_TYPE, 1, 1);
    }

    /**
     * Reserve a rectangle of tile positions starting at given tile position (give as (x,y) coordinate) for UnitType.None.
     */
    public void reserveTiles(final int positionX, final int positionY, final int width, final int height) {
        reserveTiles(positionX, positionY, DEFAULT_RESERVE_TYPE, width, height);
    }

    /**
     * Reserve the given tile position for given unit type.
     */
    public void reserveTiles(final TilePosition position, final UnitType type) {
        nonNull(position, "position");
        reserveTiles(position.getX(), position.getY(), type, 1, 1);
    }

    /**
     * Reserve a rectangle of tile positions starting at given tile position for given unit type.
     */
    public void reserveTiles(final TilePosition position, final UnitType type, final int width, final int height) {
        nonNull(position, "position");
        reserveTiles(position.getX(), position.getY(), type, width, height);
    }

    /**
     * Reserve the given tile position (give as (x,y) coordinate) for given unit type.
     */
    public void reserveTiles(final int positionX, final int positionY, final UnitType type) {
        reserveTiles(positionX, positionY, type, 1, 1);
    }

    /**
     * Reserve a rectangle of tile positions starting at given tile position (give as (x,y) coordinate) for given unit type.
     */
    public void reserveTiles(final int positionX,
                             final int positionY,
                             final UnitType type,
                             final int width,
                             final int height) {
        nonNull(type, "type", "If this was intentional, use UnitType.None instead.");

        for (int x = positionX; x < positionX + width; x++) {
            for (int y = positionY; y < positionY + height; y++) {
                this.reservedMap[x][y] = type;
            }
        }
    }

    /**
     * Free the given tile position.
     */
    public void freeTiles(final TilePosition tilePosition) {
        freeTiles(tilePosition, 1, 1);
    }

    /**
     * Free the given tile position (give as (x,y) coordinate).
     */
    public void freeTiles(final int positionX, final int positionY) {
        freeTiles(positionX, positionY, 1, 1);
    }


    /**
     * Free a rectangle of tile positions starting at given tile position.
     */
    public void freeTiles(final TilePosition tilePosition, final int width, final int height) {
        nonNull(tilePosition, "tilePosition");
        freeTiles(tilePosition.getX(), tilePosition.getY(), width, height);
    }

    /**
     * Free a rectangle of tile positions starting at given tile position (give as (x,y) coordinate).
     */
    public void freeTiles(final int positionX, final int positionY, int width, int height) {
        for (int x = positionX; x < positionX + width; x++) {
            for (int y = positionY; y < positionY + height; y++) {
                this.reservedMap[x][y] = null;
            }
        }
    }

    /**
     * Returns true if the given tile position (as x,y coordinates) is reserved for construction.
     */
    public boolean isReserved(final int x, final int y) {
        return this.reservedMap[x][y] != null;
    }

    /**
     * Returns true if the given tile position is reserved for construction.
     */
    public boolean isReserved(final TilePosition tilePosition) {
        nonNull(tilePosition, "tilePosition");
        return this.reservedMap[tilePosition.getX()][tilePosition.getY()] != null;
    }

    /**
     * Returns true if the given builder can build the given unit type at the provided position.
     * This takes into account both what the broodwar engine determines is buildable, and what tiles are reserved.
     */
    public boolean canBuildHere(final Unit builder, final TilePosition position, final UnitType typeToBuild) {
        nonNull(builder, "builder");
        nonNull(position, "position");
        nonNull(typeToBuild, "typeToBuild");

        if (this.game.canBuildHere(position, typeToBuild, builder) == false) {
            return false;
        }

        final int xMax = position.getX() + typeToBuild.tileWidth();
        final int yMax = position.getY() + typeToBuild.tileHeight();

        for (int x = position.getX(); x < xMax; x++) {
            for (int y = position.getY(); y < yMax; y++) {
                if  (this.reservedMap[x][y] != null) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns the type of unit reserving the tile at the specified tile position (given as x,y coordinates).
     */
    public UnitType getReservedType(final int x, final int y) {
        return this.reservedMap[x][y];
    }

    /**
     * Returns the type of unit reserving the tile at the specified TilePosition.
     */
    public UnitType getReservedType(final TilePosition position) {
        return this.reservedMap[position.getX()][position.getY()];
    }
}
