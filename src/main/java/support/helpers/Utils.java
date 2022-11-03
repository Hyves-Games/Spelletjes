package support.helpers;

import java.io.Serializable;
import java.sql.Timestamp;

public class Utils {
    public static String getValueForSerializable(Serializable value) {
        return switch (value.getClass().getSimpleName()) {
            case "Integer" -> Integer.toString((Integer) value);
            case "Double" -> Double.toString((Double) value);
            case "Float" -> Float.toString((Float) value);
            case "Boolean" -> Boolean.toString((Boolean) value);
            case "Timestamp" -> Long.toString(((Timestamp) value).getTime());
            default -> value.toString();
        };
    }
}
