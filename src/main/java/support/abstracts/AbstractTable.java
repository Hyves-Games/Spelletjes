package support.abstracts;

import support.database.DatabaseLogger;
import support.database.SQLite;
import support.helpers.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

abstract public class AbstractTable {

    protected Timestamp updatedAt = null;
    protected Timestamp createdAt = null;

    abstract protected String getTableName();

    abstract protected <T extends AbstractModel<T>> T getModel();

    abstract public <T extends AbstractQuery<T>> T getQuery();

    public final  void createTable() {
        ArrayList<String> foreignKeys = new ArrayList<>();

        String sql = "CREATE TABLE IF NOT EXISTS " + this.getTableName() + " (id integer PRIMARY KEY";

        DatabaseLogger.log("Creating table " + this.getTableName());
        for (Field field : this.getDeclaredFields()) {
            String databaseName = Utils.camelCaseToUnderscore(field.getName());

            sql += ", ";

            HashMap<String, String> converted = new HashMap<>() {{
              put("string", "text");
              put("arraylist", "text");
              put("timestamp", "TIMESTAMP");
            }};

            Class<?> typeClass = field.getType();

            String type = typeClass.getSimpleName().toLowerCase();
            if (field.getType().isEnum()) {
                type = this.convertEnumToSqlType(field);

                // if field is model reference, add foreign key + put type to integer
            } else if (Utils.isModelField(field)) {
                type = "integer";

                AbstractTable table = Utils.getTableFromModelField(field);
                if (table != null) {
                    foreignKeys.add("CONSTRAINT fk_" + databaseName + " FOREIGN KEY (" + databaseName + ") REFERENCES " + table.getTableName() + "(id) ON DELETE CASCADE");
                }
            }

            String sqlType = converted.get(type) != null ? converted.get(type) : type;

            sql += databaseName + " " + sqlType;

            DatabaseLogger.log("- Adding field " + field.getName() + " as " + sqlType);
        }

        // handle foreign keys
        for (String foreignKey : foreignKeys) {
            sql += ", " + foreignKey;

            DatabaseLogger.log("- Adding foreign key " + foreignKey);
        }

        sql += ")";

        DatabaseLogger.log("SQL: " + sql);
        DatabaseLogger.log("");

        SQLite.getInstance().execute(sql);
    }

    public final void dropTable() {
        String sql = "DROP TABLE IF EXISTS " + this.getTableName();

        DatabaseLogger.log("Dropping table " + this.getTableName() + "\n");

        SQLite.getInstance().execute(sql);
    }

    public final ArrayList<Field> getDeclaredFields() {
        ArrayList<Field> fields = new ArrayList<>(Arrays.asList(this.getClass().getDeclaredFields()));

        fields.addAll(Arrays.asList(this.getClass().getSuperclass().getDeclaredFields()));

        return fields;
    }

    private String convertEnumToSqlType(Field field) {
        Object[] enums = field.getType().getEnumConstants();

        StringBuilder enumString = new StringBuilder(
                "text check( " + Utils.camelCaseToUnderscore(field.getName()) + " in ("
        );

        for (Object e : enums) {
            enumString.append("'").append(e.toString()).append("',");
        }
        enumString.deleteCharAt(enumString.length() - 1);

        enumString.append("))");

        return enumString.toString();
    }
}
