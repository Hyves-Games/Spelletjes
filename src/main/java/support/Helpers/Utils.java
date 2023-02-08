package Support.Helpers;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utils {
    public static String convertSerializableToString(Serializable value) {
        return value != null ? value.toString() : "";
    }

    public static Integer convertSerializableToInteger(Serializable value) {
        return Integer.parseInt(value.toString());
    }

    public static Float convertSerializableToFloat(Serializable value) {
        return Float.parseFloat(value.toString());
    }

    public static Double convertSerializableToDouble(Serializable value) {
        return Double.parseDouble(value.toString());
    }

    public static Boolean convertSerializableToBoolean(Serializable value) {
        return Boolean.parseBoolean(value.toString());
    }

    public static Timestamp convertSerializableToTimestamp(Serializable value) {
        return Timestamp.valueOf(value.toString());
    }

    public static String camelCaseToUnderscore(String string) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        return string.replaceAll(regex, replacement).toLowerCase();
    }
}
