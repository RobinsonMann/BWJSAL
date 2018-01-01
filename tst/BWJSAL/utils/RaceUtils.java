package BWJSAL.utils;

import bwapi.Race;
import bwapi.UnitType;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class RaceUtils {
    /**
     * Creates a mock Race instance.
     */
    public static Race createMockRace(final UnitType raceCenter, final UnitType raceWorker) {
        final Race mockRace = Mockito.mock(Race.class);
        when(mockRace.getCenter()).thenReturn(raceCenter);
        when(mockRace.getWorker()).thenReturn(raceWorker);
        return mockRace;
    }
}
