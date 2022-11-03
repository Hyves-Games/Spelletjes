package support.abstracts;

import support.database.SQLite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

abstract public class AbstractModel<T extends AbstractModel<T>> {
    protected int id = 0;

    abstract public AbstractTable getTable();

    public boolean isNew() {
        return this.id == 0;
    }

    public int getId() {
        return id;
    }

    public void preDelete() {}

    public void delete() {
        if (this.isNew()) {
            throw new RuntimeException("Cannot delete a new model");
        }

        this.preDelete();

        String sql = "DELETE FROM " + this.getTable().getTableName() + " WHERE id = ?";

        try {
            PreparedStatement statement = SQLite.getInstance().getConnection().prepareStatement(sql);

            statement.setInt(1, this.id);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preSave() {}

    public final T save() {
        this.preSave();

        // create sql query for insert or update
        ArrayList<Field> fields = this.getTable().getDeclaredFields();
        String sql = this.isNew() ? this.getInsertQuery(fields) : this.getUpdateQuery(fields);

        try {
            // prepare statement and bind values
            PreparedStatement preparedStatement = SQLite.getInstance()
                    .getConnection()
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            for (Field field: fields) {
                int index = fields.indexOf(field) + 1;
                Field modelField = this.getClass().getDeclaredField(field.getName());
                modelField.setAccessible(true);

                switch (field.getType().getSimpleName().toLowerCase()) {
                    case "string" -> preparedStatement.setString(index, (String) modelField.get(this));
                    case "integer" -> preparedStatement.setInt(index, (Integer) modelField.get(this));
                    case "boolean" -> preparedStatement.setBoolean(index, (Boolean) modelField.get(this));
                    case "serializable" -> preparedStatement.setObject(index, modelField.get(this));
                    default -> throw new RuntimeException("Unsupported field type: " + field.getType().getSimpleName());
                }

                if (!this.isNew() && fields.get(fields.size() - 1) == field) {
                    preparedStatement.setInt(index + 1, this.id);
                }
            }

            // execute insert SQL statement
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            this.id = preparedStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException|IllegalAccessException|NoSuchFieldException e) {
            e.printStackTrace();
        }

        return (T) this;
    }

    private String getInsertQuery(ArrayList<Field> fields) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + this.getTable().getTableName() + " (");

        // generate field names for insert
        for (Field field : fields) {
            if (fields.get(0) != field) {
                sql.append(", ");
            }

            sql.append(field.getName());
        }

        // generate binding values for insert
        sql.append(") VALUES (?");
        sql.append(", ?".repeat(Math.max(0, fields.size() - 1)));
        sql.append(");");

        return sql.toString();
    }

    private String getUpdateQuery(ArrayList<Field> fields) {
        StringBuilder sql = new StringBuilder("UPDATE " + this.getTable().getTableName() + " SET ");

        // generate field names for update
        for (Field field : fields) {
            if (fields.get(0) != field) {
                sql.append(", ");
            }

            sql.append(field.getName()).append(" = ?");
        }

        // add where clause
        sql.append(" WHERE id = ?;");

        return sql.toString();
    }
}
