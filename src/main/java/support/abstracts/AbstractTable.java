package support.abstracts;

import support.database.SQLite;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;

abstract public class AbstractTable {

    abstract protected String getTableName();

    abstract protected <T extends AbstractModel<T>> T getModel();

    public final void createTable() {
        System.out.println("Create table: " + this.getTableName());

        String sql = "CREATE TABLE IF NOT EXISTS " + this.getTableName() + " (id integer PRIMARY KEY";

        for (Field field : this.getDeclaredFields()) {
            sql += ", ";

            HashMap<String, String> converted = new HashMap<>() {{
              put("string", "text");
            }};

            String type = field.getType().getSimpleName().toLowerCase();
            String sqlType = converted.get(type) != null ? converted.get(type) : type;

            sql += field.getName() + " " + sqlType;
        }

        sql += ");";

        SQLite.getInstance().execute(sql);
    }

    public final Field[] getDeclaredFields() {
        return this.getClass().getDeclaredFields();
    }
}
