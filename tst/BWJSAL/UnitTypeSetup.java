package BWJSAL;

import bwapi.UnitType;
import lombok.SneakyThrows;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.in;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * For every unit in the game, BWMirror provides a public static final
 * UnitType that contains information about the unit (Such as it's HP,
 * Attack Damage, BuildTime, etc). At this time, I believe that this
 * information is loaded when broodwar is started. If you call any of
 * these methods before the data is loaded, you get an ____ exception
 *
 * Therefore in order to have meaningful unit tests without broodwar
 * running we need to go behind the scenes, and update the static final
 * unit types to mocks that represent what the real class will look like
 * if Broodwar was run.
 */
public class UnitTypeSetup {

    /**
     * Update the value of static final field
     *
     * Source: http://stackoverflow.com/questions/2474017/using-reflection-to-change-static-final-file-separatorchar-for-unit-testing
     */
    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

    @SneakyThrows
    public static void setUpUnitTypes() {
        UnitType mockFirebat = mock(UnitType.class);
        when(mockFirebat.toString()).thenReturn("Firebat");
        setFinalStatic(bwapi.UnitType.class.getField("Terran_Firebat"), mockFirebat);
    }
}
