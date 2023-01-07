package support.abstracts;

import support.database.SQLite;
import support.helpers.Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

abstract public class AbstractModel<T extends AbstractModel<T>> {
    protected int id = 0;

    protected Timestamp createdAt = null;
    protected Timestamp updatedAt = null;

    abstract public AbstractTable getTable();

    public boolean isNew() {
        return this.id == 0;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
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

        Timestamp now = new Timestamp(new Date().getTime());
        if (this.isNew()) {
            this.createdAt = now;
        }

        this.updatedAt = now;

        try {
            // prepare statement and bind values
            PreparedStatement preparedStatement = SQLite.getInstance()
                    .getConnection()
                    .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            for (Field field: fields) {
                int index = fields.indexOf(field) + 1;
                Field modelField = Utils.getFieldFromModel(this.getClass(), field.getName());
                modelField.setAccessible(true);

                String type = field.getType().getSimpleName().toLowerCase();
                if (Utils.isModelField(field)) {
                    type = "foreign_key";

                    if (Utils.getModelIdFromField(modelField, this) == null) {
                        type = "null";
                    }
                }

                if (modelField.getType().isEnum()) {
                    type = "string";
                }

                switch (type) {
                    case "string" -> preparedStatement.setString(index, (String) modelField.get(this));
                    case "integer" -> preparedStatement.setInt(index, (Integer) modelField.get(this));
                    case "boolean" -> preparedStatement.setBoolean(index, (Boolean) modelField.get(this));
                    case "timestamp" -> preparedStatement.setTimestamp(index, (Timestamp) modelField.get(this));
                    case "serializable", "arraylist" -> preparedStatement.setString(index, Utils.convertSerializableToString((Serializable) modelField.get(this)));
                    case "foreign_key" -> preparedStatement.setInt(index, Utils.getModelIdFromField(modelField, this));
                    case "null" -> preparedStatement.setNull(index, 0);
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
        } catch (SQLException|IllegalAccessException|NullPointerException e) {
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

            sql.append(Utils.camelCaseToUnderscore(field.getName()));
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

            sql.append(Utils.camelCaseToUnderscore(field.getName())).append(" = ?");
        }

        // add where clause
        sql.append(" WHERE id = ?;");

        return sql.toString();
    }
}
