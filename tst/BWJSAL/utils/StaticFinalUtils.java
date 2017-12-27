package BWJSAL.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class StaticFinalUtils {
    /**
     * Change the static final field fieldName inside of clazz to newValue.
     *
     * This is a terrible hack that is needed because BWAPI constants are loaded from DLLs, and we do
     * not want to do this inside of our unit tests.
     *
     *  Implementation copied from https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection/3301720#3301720
     */
    public static void setStaticFinalField(final Class clazz, final String fieldName, final Object newValue) throws Exception {
        final Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        final Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
