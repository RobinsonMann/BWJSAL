package BWJSAL.utils;

import bwapi.Player;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Builds a mock unit. Mocking is the only way to create a Unit with certain return types.
 * This is because the BWMirror Unit class is basically a wrapper around the C++ unit class.
 */
public class UnitBuilder {
    private Boolean exists;
    private UnitType unitType;
    private Player player;
    private TilePosition tilePosition;

    public static Unit mockUnit() {
        return UnitBuilder.mockUnitBuilder().build();
    }

    public static Unit mockUnit(final UnitType unitType) {
        return mockUnit((Boolean) null, unitType, null);
    }

    public static Unit mockUnit(final Boolean exists) {
        return mockUnit(exists, null, null);
    }

    public static Unit mockUnit(final Player player, final UnitType unitType, final TilePosition tilePosition) {
        return mockUnit(null, unitType, player, tilePosition);
    }

    public static Unit mockUnit(final Player player, final UnitType unitType) {
        return mockUnit(unitType, player);
    }

    public static Unit mockUnit(final UnitType unitType, final Player player) {
        return mockUnit(null, unitType, player);
    }

    public static Unit mockUnit(final Boolean exists, final UnitType unitType, final Player player) {
        return mockUnit(exists, unitType, player, null);
    }

    public static Unit mockUnit(final Boolean exists, final UnitType unitType, final Player player, final TilePosition tilePosition) {
        return UnitBuilder.mockUnitBuilder()
                          .withExists(exists)
                          .withUnitType(unitType)
                          .withPlayer(player)
                          .withTilePosition(tilePosition)
                          .build();
    }

    public static UnitBuilder mockUnitBuilder() {
        return new UnitBuilder();
    }

    public UnitBuilder withExists(final Boolean exists) {
        this.exists = exists;
        return this;
    }

    public UnitBuilder withUnitType(final UnitType unitType) {
        this.unitType = unitType;
        return this;
    }

    public UnitBuilder withPlayer(final Player player) {
        this.player = player;
        return this;
    }

    public UnitBuilder withTilePosition(final TilePosition tilePosition) {
        this.tilePosition = tilePosition;
        return this;
    }

    public Unit build() {
        final Unit mockUnit = Mockito.mock(Unit.class);

        if (this.exists != null) {
            when(mockUnit.exists()).thenReturn(this.exists);
        }

        if (this.unitType != null) {
            when(mockUnit.getType()).thenReturn(this.unitType);
        }

        if (this.player != null) {
            when(mockUnit.getPlayer()).thenReturn(this.player);
        }

        if (this.tilePosition != null) {
            when(mockUnit.getTilePosition()).thenReturn(this.tilePosition);
        }

        return mockUnit;
    }
}
