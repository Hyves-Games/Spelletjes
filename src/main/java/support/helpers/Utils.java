package support.helpers;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utils {
    public static String convertSerializableToString(Serializable value) {
        return value.toString();
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
}
