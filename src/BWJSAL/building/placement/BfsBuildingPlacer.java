package BWJSAL.building.placement;

import BWJSAL.Map.ReservedMap;
import bwapi.Game;
import bwapi.Pair;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Breadth First building placement algorithm
 */
public class BfsBuildingPlacer implements BuildingPlacer {
    private boolean[][] walkable;

    private Game game;

    /**
     * Creates a new instance of BfsBuildingPlacer
     *
     * @param game               Broodwar game instance
     */
    public BfsBuildingPlacer(final Game game) {
        this.game = game;
        this.walkable = new boolean[game.mapWidth()][game.mapHeight()];

        for (int x = 0; x < game.mapWidth(); x++) {
            for (int y = 0; y < game.mapHeight(); y++) {
                this.walkable[x][y] = true;
            }
        }
    }

    public TilePosition findBuildLocation(ReservedMap reservedMap, UnitType unitType, TilePosition seedLocation, Unit builder) {
        return getBuildLocationNear(reservedMap, unitType, seedLocation, builder, 1);
    }

    private TilePosition getBuildLocationNear(ReservedMap reservedMap, UnitType type, TilePosition position, Unit builder, int buildDist) {
        // returns a valid build location near the specified tile position.
        if ( type.isAddon() )
        {
            type = type.whatBuilds().first;
        }
        PriorityQueue<Pair<TilePosition, Integer>> searchHeap = new PriorityQueue<>((o1, o2) -> o1.second.compareTo(o2.second));
        searchHeap.add(new Pair<>(position, 0));

        Set<TilePosition> closed = new HashSet<>();

        // Do a breadth first search to find a nearby valid build location with space
        while ( searchHeap.isEmpty() == false )
        {
            TilePosition t = searchHeap.peek().first;
            int s = searchHeap.peek().second;
            searchHeap.poll();

            if ( canBuildHereWithSpace( reservedMap, type, t, builder, buildDist ) )
            {
                // We can build here with space so return this tile position
                return t;
            }
            int tx = t.getX();
            int ty = t.getY();
            int minx = Math.max( tx - 1, 0 );
            int maxx = Math.min( tx + 1, game.mapWidth() - 1 );
            int miny = Math.max( ty - 1, 0 );
            int maxy = Math.min( ty + 1, game.mapHeight() - 1 );

            for ( int x = minx; x <= maxx; x++ )
            {
                for ( int y = miny; y <= maxy; y++ )
                {
                    if ( walkable[x][y] )
                    {
                        TilePosition t2 = new TilePosition( x, y );
                        if ( !closed.contains( t2 ))
                        {
                            int ds = 10;
                            if ( x != tx && y != ty )
                            {
                                ds = 14; // diagonal distance, approximation of 10*sqrt( 2 )
                            }
                            closed.add(t2);
                            searchHeap.add(new Pair( t2, s + ds ) );
                        }
                    }
                }
            }
        }
        // We didn't find a build position, try looking for one with less space
        if ( buildDist > 0 )
        {
            return getBuildLocationNear( reservedMap, type, position, builder, buildDist - 1 );
        }

        return TilePosition.None;
    }

    private boolean canBuildHereWithSpace(ReservedMap reservedMap, UnitType type, TilePosition position, Unit builder, int buildDist ) {
        if ( type.isAddon() )
        {
            type = type.whatBuilds().first;
        }
        // returns true if we can build this type of unit here with the specified amount of space.
        // space value is stored in this.buildDistance.

        // if we can't build here, we of course can't build here with space

        if (builder != null) {
            if (!reservedMap.canBuildHere(builder, position, type)) {
                return false;
            }
        } else {
            if (!reservedMap.canBuildHere(position, type)) {
                return false;
            }
        }


        if ( type.isRefinery() )
        {
            return true;
        }

        int width = type.tileWidth();
        int height = type.tileHeight();

        // make sure we leave space for add - ons. These types of units can have addons:
        if ( type == UnitType.Terran_Command_Center ||
                type == UnitType.Terran_Factory ||
                type == UnitType.Terran_Starport ||
                type == UnitType.Terran_Science_Facility )
        {
            width += 2;
        }
        int startx = position.getX() - buildDist;
        if ( startx < 0 )
        {
            return false;
        }
        int starty = position.getY() - buildDist;
        if ( starty < 0 )
        {
            return false;
        }
        int endx = position.getX() + width + buildDist;
        if ( endx > game.mapWidth() )
        {
            return false;
        }
        int endy = position.getY() + height + buildDist;
        if ( endy > game.mapHeight() )
        {
            return false;
        }

        for ( int x = startx; x < endx; x++ )
        {
            for ( int y = starty; y < endy; y++ )
            {
                if ( !isBuildable( builder, x, y ) || reservedMap.isReserved( x, y ) )
                {
                    return false;
                }
            }
        }

        if ( position.getX() > 3 )
        {
            int startx2 = Math.max( startx - 2, 0 );
            for ( int x = startx2; x < startx; x++ )
            {
                for ( int y = starty; y < endy; y++ )
                {
                    for ( Unit u : game.getUnitsOnTile( x, y ) )
                    {
                        if ( !u.isLifted() && u != builder )
                        {
                            UnitType type2 = u.getType();
                            if ( type2 == UnitType.Terran_Command_Center ||
                                type2 == UnitType.Terran_Factory ||
                                type2 == UnitType.Terran_Starport ||
                                type2 == UnitType.Terran_Science_Facility )
                            {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
   }

    /**
     * Returns true if this tile is currently buildable. Takes into account units on the tile.
     * Does not take into account reserved map.
     */
    private boolean isBuildable(final Unit builder, final int x, final int y ) {
        if (!this.game.isBuildable(x, y)) {
            return false;
        }

        for (final Unit unit : this.game.getUnitsOnTile(x, y)) {
            if (unit.getType().isBuilding()
                    && !unit.isLifted()
                    && !unit.getType().isFlyer()
                    && unit != builder) {
                return false;
            }
        }

        return true;
    }
}
