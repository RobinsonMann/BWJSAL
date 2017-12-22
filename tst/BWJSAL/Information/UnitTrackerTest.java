package BWJSAL.Information;

import bwapi.Game;
import bwapi.Player;
import bwapi.Position;
import bwapi.Unit;
import bwapi.UnitType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class UnitTrackerTest {
    private Game mockGame;

    private UnitTracker target;

    @Before
    public void setUp() {
        this.mockGame = Mockito.mock(Game.class);
        this.target = new UnitTracker(this.mockGame);
    }

    @Test
    public void getLastPosition_unitExists_returnsPosition() {
        assertThatDataExtractedFromUnit(Position.class,
                                        Unit::getPosition,
                                        (unit) -> this.target.getLastPosition(unit));
    }

    @Test
    public void getLastPosition_unitDoesNotExistAndNotTracked_returnsPositionNone() {
        assertThat(this.target.getLastPosition(createMockUnit(false))).isEqualTo(Position.None);
    }

    @Test
    public void getLastPosition_unitDoesNotExistButIsTracked_returnsLastKnownPosition() {
        assertThatTrackedInformationReturned(Position.class,
                                             Unit::getPosition,
                                             (unit) -> this.target.getLastPosition(unit));
    }

    @Test
    public void getPlayer_unitExists_returnsPlayer() {
        assertThatDataExtractedFromUnit(Player.class,
                                        Unit::getPlayer,
                                        (unit) -> this.target.getPlayer(unit));
    }

    @Test
    public void getPlayer_unitDoesNotExistAndNotTracked_returnsNull() {
        assertThat(this.target.getPlayer(createMockUnit(false))).isNull();
    }

    @Test
    public void getPlayer_unitDoesNotExistButIsTracked_returnsLastKnownPlayer() {
        assertThatTrackedInformationReturned(Player.class,
                                             Unit::getPlayer,
                                             (unit) -> this.target.getPlayer(unit));
    }

    @Test
    public void getUnitType_unitExists_returnsType() {
        assertThatDataExtractedFromUnit(UnitType.class,
                                        Unit::getType,
                                        (unit) -> this.target.getUnitType(unit));
    }

    @Test
    public void getUnitType_unitDoesNotExistAndNotTracked_returnsUnitTypeNone() {
        assertThat(this.target.getUnitType(createMockUnit(false))).isEqualTo(UnitType.None);
    }

    @Test
    public void getUnitType_unitDoesNotExistButIsTracked_returnsLastKnownUnitType() {
        assertThatTrackedInformationReturned(UnitType.class,
                                             Unit::getType,
                                             (unit) -> this.target.getUnitType(unit));
    }

    @Test
    public void doesUnitExist_unitExists_returnsTrue() {
        assertThat(this.target.doesUnitExist(createMockUnit(true))).isTrue();
    }

    @Test
    public void doesUnitExist_unitDoesNotExistAndNotTracked_returnsFalse() {
        assertThat(this.target.doesUnitExist(createMockUnit(false))).isFalse();
    }

    @Test
    public void doesUnitExist_unitDoesNotExistButIsTracked_returnsLastKnownUnitType() {
        assertThatTrackedInformationReturned(Position.class,
                                             Unit::getPosition,
                                             (unit) -> this.target.getLastPosition(unit));
    }

    @Test
    public void getLastSeenTime_unitExists_returnsGameFrameCount() {
        when(this.mockGame.getFrameCount()).thenReturn(12345);
        assertThat(this.target.getLastSeenTime(createMockUnit(true))).isPresent()
                                                                     .contains(12345);
    }

    @Test
    public void getLastSeenTime_unitDoesNotExistAndNotTracked_returnsOptionalEmpty() {
        assertThat(this.target.getLastSeenTime(createMockUnit(false))).isNotPresent();
    }

    @Test
    public void getLastSeenTime_unitDoesNotExistButIsTracked_returnsLastSeenFrame() {
        final Unit unit = createMockUnit(true);

        // Unit exists, so last seen frame count should now be set to 0
        when(this.mockGame.getFrameCount()).thenReturn(0);
        this.target.onUnitDiscover(unit, 0);
        this.target.onFrame(0);

        // Unit exists, so last seen frame count should be set to 1
        when(this.mockGame.getFrameCount()).thenReturn(1);
        this.target.onFrame(1);

        when(unit.exists()).thenReturn(false);

        // Unit no longer exists, so last seen frame count should still be 1
        when(this.mockGame.getFrameCount()).thenReturn(2);
        this.target.onFrame(2);

        assertThat(this.target.getLastSeenTime(unit)).isPresent()
                                                     .contains(1);
    }

    @Test
    public void onDiscovery_unitDiscovered_unitTracked() {
        final Unit unit = createMockUnit(true);
        final Player unitOwner = Mockito.mock(Player.class);
        when(unit.getPlayer()).thenReturn(unitOwner);

        this.target.onUnitDiscover(unit, 0);

        assertThat(this.target.getPlayer(unit)).isEqualTo(unitOwner);
    }

    @Test
    public void onFrame_propertyChangedSinceDiscovery_latestPropertyReturned() {
        final Unit unit = createMockUnit(true);
        final Player originalUnitOwner = Mockito.mock(Player.class);
        when(unit.getPlayer()).thenReturn(originalUnitOwner);

        this.target.onUnitDiscover(unit, 0);

        // Owner has changed and unit still exists, so tracked owner should change at next onFrame()
        final Player newUnitOwner = Mockito.mock(Player.class);
        when(unit.getPlayer()).thenReturn(newUnitOwner);

        this.target.onFrame(1);

        assertThat(this.target.getPlayer(unit)).isEqualTo(newUnitOwner);
    }

    @Test
    public void unitDestroyed_unitDestroyed_existsIsFalse() {
        final Unit unit = createMockUnit(true);

        this.target.onUnitDiscover(unit, 1);
        this.target.onFrame(1);

        this.target.onFrame(2);

        when(unit.exists()).thenReturn(false);
        this.target.onUnitDestroy(unit, 2);

        this.target.onFrame(3);

        assertThat(this.target.doesUnitExist(unit)).isFalse();
    }

    /**
     * Verifies that targetToInvoke returns Unit.methodToMock.
     *
     * This is used to verify that unit getters are called when the unit exists.
     */
    private <T> void assertThatDataExtractedFromUnit(Class<T> responseClass,
                                                     Function<Unit, T> methodToMock,
                                                     Function<Unit, T> targetToInvoke) {
        final Unit unit = createMockUnit(true);

        final T mockResponse = Mockito.mock(responseClass);
        when(methodToMock.apply(unit)).thenReturn(mockResponse);

        assertThat(targetToInvoke.apply(unit)).isEqualTo(mockResponse);
    }

    /**
     * Verify that UnitTracker returns the last information saved available when the unit existed.
     *
     * @param invocationTargetReturnType    The class that is being tracked
     * @param unitGetterMethodThatIsTracked What method is called to get the tracked data from the unit
     * @param targetMethodToInvoke          What method is called from the target to get the tracked data
     * @param <T>                           The class that is being tracked
     */
    public <T> void assertThatTrackedInformationReturned(Class<T> invocationTargetReturnType,
                                                         Function<Unit, T> unitGetterMethodThatIsTracked,
                                                         Function<Unit, T> targetMethodToInvoke) {
        final Unit unit = createMockUnit(true);

        // First value returned by getter. This should tracked, and will be updated in frame 2
        final T getterValueAtDiscovery = Mockito.mock(invocationTargetReturnType);
        when(unitGetterMethodThatIsTracked.apply(unit)).thenReturn(getterValueAtDiscovery);

        this.target.onUnitDiscover(unit, 0);
        this.target.onFrame(0);

        // Last value of getter that will be tracked
        final T lastGetterValueWhileUnitExisted = Mockito.mock(invocationTargetReturnType);
        when(unitGetterMethodThatIsTracked.apply(unit)).thenReturn(lastGetterValueWhileUnitExisted);

        this.target.onFrame(1);

        // Unit no longer exists
        when(unit.exists()).thenReturn(false);

        // Update getter to a new value. This value should NOT be returned.
        final Position getterValueButUnitDoesntExist = Mockito.mock(Position.class);
        when(unit.getPosition()).thenReturn(getterValueButUnitDoesntExist);
        this.target.onFrame(2);

        // Verify that the value tracked in frame 1 is returned.
        assertThat(targetMethodToInvoke.apply(unit)).isEqualTo(lastGetterValueWhileUnitExisted)
                                                    .isNotEqualTo(getterValueAtDiscovery)
                                                    .isNotEqualTo(getterValueButUnitDoesntExist);
    }

    private Unit createMockUnit(final boolean exists) {
        final Unit unit = Mockito.mock(Unit.class);
        when(unit.exists()).thenReturn(exists);
        return unit;
    }
}