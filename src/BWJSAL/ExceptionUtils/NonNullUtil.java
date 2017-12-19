package BWJSAL.ExceptionUtils;

/**
 * Verify that an object is not null. If it is null throw an exception.
 *
 * This class is used in place of lombok's @NonNull so as to not add a non-needed dependency.
 */
public class NonNullUtil {
    public static void nonNull(final Object object, final String objectName) {
        if (object == null) {
            throw new NullPointerException(String.format("%s is required to be non-null.",
                                                         objectName));
        }
    }

    public static void nonNull(final Object object, final String objectName, final String additionalInformation) {
        if (object == null) {
            throw new NullPointerException(String.format("%s is required to be non-null. %s",
                                                         objectName,
                                                         additionalInformation));
        }
    }
}
