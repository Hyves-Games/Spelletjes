package Support.Abstracts;

import Support.Database.DatabaseLogger;
import Support.Database.SQLite;
import Support.Helpers.Utils;
import Support.Records.ModelField;

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

            for (Field tableField: fields) {
                int index = fields.indexOf(tableField) + 1;
                ModelField field = new ModelField(tableField, this);

                field.prepareStatement(preparedStatement, index);

                if (!this.isNew() && fields.get(fields.size() - 1) == tableField) {
                    preparedStatement.setInt(index + 1, this.id);

                }
            }

            DatabaseLogger.log("Insert/update of model: " + this.getClass().getSimpleName());
            DatabaseLogger.log(preparedStatement.getParameterMetaData() + " " + preparedStatement.getMetaData());
            DatabaseLogger.log("");

            // execute insert SQL statement
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating row failed, no rows affected.");
            }

            this.id = preparedStatement.getGeneratedKeys().getInt(1);
        } catch (SQLException|NullPointerException e) {
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
