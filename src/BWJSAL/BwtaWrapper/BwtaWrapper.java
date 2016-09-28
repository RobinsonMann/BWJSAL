package BWJSAL.BwtaWrapper;

import bwapi.Pair;
import bwapi.Player;
import bwapi.Position;
import bwapi.TilePosition;
import bwta.BWTA;
import bwta.BaseLocation;
import bwta.Chokepoint;
import bwta.Polygon;
import bwta.Region;

import java.util.List;
import java.util.Map;

/**
 * BwtaWrapper is a wrapper for the entirely BWTA class.
 *
 * This class is useful if you want to easily mock responses from BWTA.
 */
public class BwtaWrapper {
    public void readMap() {
        BWTA.readMap();
    }

    public void analyze() {
        BWTA.analyze();
    }

    public void computeDistanceTransform() {
        BWTA.computeDistanceTransform();
    }

    public void balanceAnalysis() {
        BWTA.balanceAnalysis();
    }

    public void cleanMemory() {
        BWTA.cleanMemory();
    }

    public int getMaxDistanceTransform() {
        return BWTA.getMaxDistanceTransform();
    }

    public List<Region> getRegions() {
        return BWTA.getRegions();
    }

    public List<Chokepoint> getChokepoints() {
        return BWTA.getChokepoints();
    }

    public List<BaseLocation> getBaseLocations() {
        return BWTA.getBaseLocations();
    }

    public List<BaseLocation> getStartLocations() {
        return BWTA.getStartLocations();
    }

    public List<Polygon> getUnwalkablePolygons() {
        return BWTA.getUnwalkablePolygons();
    }

    public BaseLocation getStartLocation(final Player var0) {
        return BWTA.getStartLocation(var0);
    }

    public Region getRegion(final TilePosition var0) {
        return BWTA.getRegion(var0);
    }

    public Region getRegion(final int var0, final int var1) {
        return BWTA.getRegion(var0, var1);
    }

    public Region getRegion(final Position var0) {
        return BWTA.getRegion(var0);
    }

    public Chokepoint getNearestChokepoint(final int var0, final int var1) {
        return BWTA.getNearestChokepoint(var0, var1);
    }

    public Chokepoint getNearestChokepoint(final TilePosition var0) {
        return BWTA.getNearestChokepoint(var0);
    }

    public Chokepoint getNearestChokepoint(final Position var0) {
        return BWTA.getNearestChokepoint(var0);
    }

    public BaseLocation getNearestBaseLocation(final int var0, final int var1) {
        return BWTA.getNearestBaseLocation(var0, var1);
    }

    public BaseLocation getNearestBaseLocation(final TilePosition var0) {
        return BWTA.getNearestBaseLocation(var0);
    }

    public BaseLocation getNearestBaseLocation(final Position var0) {
        return BWTA.getNearestBaseLocation(var0);
    }

    public Polygon getNearestUnwalkablePolygon(final int var0, final int var1) {
        return BWTA.getNearestUnwalkablePolygon(var0, var1);
    }

    public Polygon getNearestUnwalkablePolygon(final TilePosition var0) {
        return BWTA.getNearestUnwalkablePolygon(var0);
    }

    public Position getNearestUnwalkablePosition(final Position var0) {
        return BWTA.getNearestUnwalkablePosition(var0);
    }

    public boolean isConnected(final int var0, final int var1, final int var2, final int var3) {
        return BWTA.isConnected(var0, var1, var2, var3);
    }

    public boolean isConnected(final TilePosition var0, final TilePosition var1) {
        return BWTA.isConnected(var0, var1);
    }

    public double getGroundDistance(final TilePosition var0, final TilePosition var1) {
        return BWTA.getGroundDistance(var0, var1);
    }

    public Pair<TilePosition, Double> getNearestTilePosition(final TilePosition var0, final List<TilePosition> var1) {
        return BWTA.getNearestTilePosition(var0, var1);
    }

    public Map<TilePosition, Double> getGroundDistances(final TilePosition var0, final List<TilePosition> var1) {
        return BWTA.getGroundDistances(var0, var1);
    }

    public List<TilePosition> getShortestPath(final TilePosition var0, final TilePosition var1) {
        return BWTA.getShortestPath(var0, var1);
    }

    public List<TilePosition> getShortestPath(final TilePosition var0, final List<TilePosition> var1) {
        return BWTA.getShortestPath(var0, var1);
    }

    public void buildChokeNodes() {
        BWTA.buildChokeNodes();
    }

    public int getGroundDistance2(final TilePosition var0, final TilePosition var1) {
        return BWTA.getGroundDistance2(var0, var1);
    }
}
