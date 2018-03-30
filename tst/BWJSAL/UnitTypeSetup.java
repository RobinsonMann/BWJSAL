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

    // Runtime of Theta(n^2)
    public static int answer(String s) {

        int sLength = s.length();

        // Max number of slices occurs when s={.}*
        // Min number of slices is when string has no patterns

        int maxNumberOfSlices = 1; // Know 1 is always a solution. I.e. no slices.

        // Test from every starting position
        for (int i = 0; i < sLength; i++) {

            // Test for every string length
            for (int j = 1; j < sLength; j++) {

                // If gcd(sLength, j) != 0, then cannot have even slices
                if (sLength % j != 0) {
                    continue;
                }

                String patternToTest = cyclicSubstring(s, i, j);

                if (patternWorkForString(s, patternToTest, i)) {
                    int numberOfSlices = s.length() / j;
                    if (numberOfSlices > maxNumberOfSlices)
                        maxNumberOfSlices = numberOfSlices;
                }
            }
        }

        return maxNumberOfSlices;
    }

    public static String cyclicSubstring(String s, int index, int length) {
        StringBuilder sb = new StringBuilder();
        char[] sChars = s.toCharArray();

        for (int i = index; i < index + length; i++) {
            sb.append(sChars[i  % sChars.length]);
        }

        return sb.toString();
    }

    public static boolean patternWorkForString(String s, String pattern, int startingIndex) {
        if (pattern.length() > s.length()) // Cannot have slice bigger then cake!
            return false;

        if (s.length() % pattern.length() != 0) // Pattern doesn't go into s even amount of times
            return false;

        char[] sChar = s.toCharArray();
        char[] patternChar = pattern.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            char sCharAtIndex = sChar[(startingIndex + i) % s.length()];
            char pCharAtIndex = patternChar[i % pattern.length()];

            if (sCharAtIndex != pCharAtIndex) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void basicTest() {
        assertThat(patternWorkForString("bababa", "ab", 1)).isTrue();
        assertThat(patternWorkForString("aaaaaa", "aaaaaa", 1)).isTrue();
        assertThat(patternWorkForString("aaa", "aaaaaaaaaa", 1)).isFalse();
        assertThat(patternWorkForString("aabbcc", "aa", 1)).isFalse();
        assertThat(cyclicSubstring("abc", 0, 1)).isEqualTo("a");
        assertThat(cyclicSubstring("abc", 0, 3)).isEqualTo("abc");
        assertThat(cyclicSubstring("abc", 0, 0)).isEqualTo("");
        assertThat(cyclicSubstring("abc", 1, 3)).isEqualTo("bca");


        String repeatedA = new String(new char[100]).replace("\0", "a");
        String repeatedB = new String(new char[100]).replace("\0", "b");

        assertThat(answer(repeatedA + repeatedB)).isEqualTo(1);
        assertThat(answer("abvasdqweqwehqwer")).isEqualTo(1);
        assertThat(answer("aaaaa")).isEqualTo(5);
        assertThat(answer("bababa")).isEqualTo(3);
        assertThat(answer("abccbaabccba")).isEqualTo(2);
        assertThat(answer("abcabcabcabc")).isEqualTo(4);
    }


    @SneakyThrows
    public static void setUpUnitTypes() {
        UnitType mockFirebat = mock(UnitType.class);
        when(mockFirebat.toString()).thenReturn("Firebat");
        setFinalStatic(bwapi.UnitType.class.getField("Terran_Firebat"), mockFirebat);
    }
}
