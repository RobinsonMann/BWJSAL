package BWJSAL.utils;

import BWJSAL.bwta.BwtaWrapper;
import bwapi.Player;
import bwapi.TilePosition;
import bwta.BaseLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class BwtaUtils {
    public static void setStartingLocationsOnMock(final BwtaWrapper mockBwtaWrapper,
                                                  final BaseLocation selfStartingLocation,
                                                  final BaseLocation... otherStartingLocations) {

        final List<BaseLocation> allBaseLocations = new ArrayList<>(1 + otherStartingLocations.length);
        allBaseLocations.add(selfStartingLocation);
        allBaseLocations.addAll(Arrays.asList(otherStartingLocations));

        when(mockBwtaWrapper.getStartLocations()).thenReturn(allBaseLocations);
    }

    public static void setSelfStartingLocationOnMock(final BwtaWrapper mockBwtaWrapper,
                                                     final Player self,
                                                     final BaseLocation selfStartingLocation) {
        when(mockBwtaWrapper.getStartLocation(self)).thenReturn(selfStartingLocation);
    }

    public static void setNearestBaseLocationOnMock(final BwtaWrapper mockBwtaWrapper,
                                                    final TilePosition tilePosition,
                                                    final BaseLocation nearestBaseLocationToTilePosition) {
        when(mockBwtaWrapper.getNearestBaseLocation(tilePosition)).thenReturn(nearestBaseLocationToTilePosition);
    }
}
