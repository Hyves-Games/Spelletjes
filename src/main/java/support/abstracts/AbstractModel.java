package support.abstracts;

import support.database.SQLite;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract public class AbstractModel {
    protected int id = 0;

    abstract public AbstractTable getTable();

    public boolean isNew() {
        return this.id == 0;
    }

    public int getId() {
        return id;
    }

    public void delete() {
        if (this.isNew()) {
            throw new RuntimeException("Cannot delete a new model");
        }

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

    public final AbstractModel save() throws SQLException {
        this.preSave();

        String sql = "";
        Field[] fields = this.getTable().getDeclaredFields();

        // create sql query for insert or update
        if (this.isNew()) {
            sql = this.getInsertQuery(fields);
        } else {
            sql = this.getUpdateQuery(fields);
        }

        // prepare statement and bind values
        PreparedStatement preparedStatement = SQLite.getInstance().getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        int index = 1;
        for (Field field: fields) {
            try {
                Field modelField = this.getClass().getDeclaredField(field.getName());
                modelField.setAccessible(true);

                switch (field.getType().getSimpleName().toLowerCase()) {
                    case "string" -> preparedStatement.setString(index, (String) modelField.get(this));
                    case "integer" -> preparedStatement.setInt(index, (Integer) modelField.get(this));
                    case "boolean" -> preparedStatement.setBoolean(index, (Boolean) modelField.get(this));
                    default -> throw new RuntimeException("Unsupported field type: " + field.getType().getSimpleName());
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }

            index++;
        }

        // add id to update query
        if (!this.isNew()) {
            preparedStatement.setInt(index, this.id);
        }

        // execute insert SQL statement
        int affectedRows = preparedStatement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating row failed, no rows affected.");
        }

        try {
            this.id = preparedStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    private String getInsertQuery(Field[] fields) {
        StringBuilder sql = new StringBuilder("INSERT INTO " + this.getTable().getTableName() + " (");

        boolean first = true;

        // generate field names for insert
        for (Field field : fields) {
            if (first) {
                first = false;
            } else {
                sql.append(", ");
            }

            sql.append(field.getName());
        }

        // generate binding values for insert
        sql.append(") VALUES (?");
        sql.append(", ?".repeat(Math.max(0, fields.length - 1)));
        sql.append(");");

        return sql.toString();
    }

    private String getUpdateQuery(Field[] fields) {
        StringBuilder sql = new StringBuilder("UPDATE " + this.getTable().getTableName() + " SET ");

        boolean first = true;

        // generate field names for update
        for (Field field : fields) {
            if (first) {
                first = false;
            } else {
                sql.append(", ");
            }

            sql.append(field.getName()).append(" = ?");
        }

        // add where clause
        sql.append("WHERE id = ?;");

        return sql.toString();
    }
}
