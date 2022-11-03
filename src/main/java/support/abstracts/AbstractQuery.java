package support.abstracts;

import support.database.SQLite;
import support.database.SQLiteValue;
import support.database.WhereClause;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

abstract public class AbstractQuery<T extends AbstractQuery<T>> {
    private final StringBuilder query;

    private final ArrayList<WhereClause> whereClauses = new ArrayList<>();

    public AbstractQuery() {
        this.query = new StringBuilder("SELECT * FROM " + this.getTable().getTableName());
    }

    abstract public AbstractTable getTable();

    private PreparedStatement getStatement() throws SQLException {
        // add where clauses to query

        boolean first = true;
        for (WhereClause whereClause : this.whereClauses) {
            if (first) {
                this.query.append(" WHERE ").append(whereClause.getWhereClause());
                first = false;
            } else {
                this.query.append(" AND ").append(whereClause.getWhereClause());
            }
        }

        PreparedStatement statement = SQLite.getInstance().getConnection().prepareStatement(this.query.toString());

        // bind data to where clauses
        int index = 1;
        for (WhereClause whereClause : this.whereClauses) {
            whereClause.bindValue(statement, index);

            index++;
        }

        return statement;
    }

    private <M extends AbstractModel<M>> M getModelFromResultSet(ResultSet result) throws SQLException, NoSuchFieldException, IllegalAccessException {
        AbstractModel<M> model = this.getTable().getModel();

        // set id
        Field idField = model.getClass().getSuperclass().getDeclaredField("id");

        idField.setAccessible(true);
        idField.set(model, result.getInt("id"));

        // set other fields
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            field.set(model, result.getObject(field.getName()));
        }

        return (M) model;
    }

    public <M extends AbstractModel<M>> ArrayList<M> find() {
        ArrayList<AbstractModel<M>> models = new ArrayList<>();

        try {
            ResultSet result = this.getStatement().executeQuery();
            while (result.next()) {
                models.add(this.getModelFromResultSet(result));
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (ArrayList<M>) models;
    }

    public <M extends AbstractModel<M>> M findOne() {
        try {
            ResultSet result = this.getStatement().executeQuery();

            if (result.next()) {
                return this.getModelFromResultSet(result);
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public <M extends AbstractModel<M>> M findOneOrCreate() {
        M model = this.findOne();

        if (model == null) {
            model = this.getTable().getModel();
        }

        return model;
    }

    public <M extends AbstractModel<M>> M findOneById(int id) {
        this.where("id", "=", id);

        return this.findOne();
    }

    public T where(String column, String operator, String value) {
        this.whereClauses.add(
            new WhereClause(column, operator, new SQLiteValue(String.class, value))
        );

        return (T) this;
    }

    public T where(String column, String operator, Integer value) {
        this.whereClauses.add(
                new WhereClause(column, operator, new SQLiteValue(Integer.class, value))
        );

        return (T) this;
    }

    public T where(String column, String operator, Float value) {
        this.whereClauses.add(
                new WhereClause(column, operator, new SQLiteValue(Float.class, value))
        );

        return (T) this;
    }

    public T where(String column, String operator, Double value) {
        this.whereClauses.add(
                new WhereClause(column, operator, new SQLiteValue(Double.class, value))
        );

        return (T) this;
    }

    public T where(String column, String operator, Boolean value) {
        this.whereClauses.add(
                new WhereClause(column, operator, new SQLiteValue(Boolean.class, value))
        );

        return (T) this;
    }

    public T where(String column, String operator, Timestamp value) {
        this.whereClauses.add(
                new WhereClause(column, operator, new SQLiteValue(Timestamp.class, value))
        );

        return (T) this;
    }

    public T filterById(int id) {
        this.where("id", "=", id);

        return (T) this;
    }
}
