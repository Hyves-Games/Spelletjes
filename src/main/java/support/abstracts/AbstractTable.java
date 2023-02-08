package Support.Abstracts;

import Support.Database.DatabaseLogger;
import Support.Database.SQLite;
import Support.Records.ModelField;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

abstract public class AbstractTable {

    protected Timestamp updatedAt = null;
    protected Timestamp createdAt = null;

    public abstract String getTableName();

    abstract protected <T extends AbstractModel<T>> T getModel();

    abstract public <T extends AbstractQuery<T>> T getQuery();

    public final  void createTable() {
        ArrayList<String> foreignKeys = new ArrayList<>();

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + this.getTableName() + " (id integer PRIMARY KEY");

        DatabaseLogger.log("Creating table " + this.getTableName());
        for (Field tableField : this.getDeclaredFields()) {
            ModelField field = new ModelField(tableField, this.getModel());

            sql.append(field.getSqlForTableCreation());

            String foreignKeySql = field.getForeignKeyConstraint();
            if (foreignKeySql != null) {
                foreignKeys.add(foreignKeySql);
            }

            DatabaseLogger.log("- Adding field " + field.getName() + " as " + field.getSqlType());
        }

        // handle foreign keys
        for (String foreignKey : foreignKeys) {
            sql.append(", ").append(foreignKey);

            DatabaseLogger.log("- Adding foreign key " + foreignKey);
        }

        sql.append(")");

        DatabaseLogger.log("SQL: " + sql);
        DatabaseLogger.log("");

        SQLite.getInstance().execute(sql.toString());
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
}
