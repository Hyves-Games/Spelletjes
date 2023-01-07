package support.helpers;

import support.abstracts.AbstractModel;
import support.abstracts.AbstractQuery;
import support.abstracts.AbstractTable;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    public static Field getFieldFromModel(Class<?> modelClass, String fieldName) {
        try {
            try {
                return modelClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                return modelClass.getSuperclass().getDeclaredField(fieldName);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isModelField(Field field) {
        Class<?> typeClass = field.getType();

        return typeClass.getSuperclass() != null && typeClass.getSuperclass().getName().equals(AbstractModel.class.getName());
    }

    public static AbstractTable getTableFromModelField(Field field) {
        Class<?> typeClass = field.getType();

        try {
            Method method = typeClass.getMethod("getTable");

            return (AbstractTable) method.invoke(typeClass.newInstance());
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static AbstractQuery<?> getQueryFromField(Field field) {
        AbstractTable table = Utils.getTableFromModelField(field);
        if (table != null) {
            return table.getQuery();
        }

        return null;
    }

    public static Integer getModelIdFromField(Field field, AbstractModel<?> model) {
        try {
            AbstractModel<?> foreignModel = ((AbstractModel<?>) field.get(model));
            if (foreignModel != null) {
                if (foreignModel.isNew()) {
                    foreignModel.save();
                }

                return foreignModel.getId();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
