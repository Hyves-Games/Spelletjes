package support.abstracts;

import support.database.DatabaseLogger;
import support.database.SQLite;
import support.database.SQLiteValue;
import support.database.WhereClause;
import support.records.ModelField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

abstract public class AbstractQuery<T extends AbstractQuery<T>> {

    private StringBuilder query;
    private final ArrayList<WhereClause> whereClauses = new ArrayList<>();

    abstract public AbstractTable getTable();

    private void resetQuery() {
        this.query = new StringBuilder("SELECT * FROM " + this.getTable().getTableName());
    }

    public AbstractQuery() {
        this.resetQuery();
    }

    private PreparedStatement getStatement() {
        // add where clauses to query
        for (WhereClause whereClause : this.whereClauses) {
            if (whereClauses.get(0) == whereClause) {
                this.query.append(" WHERE ").append(whereClause.sqlQuery());
            } else {
                this.query.append(" AND ").append(whereClause.sqlQuery());
            }
        }

        // bind data to where clauses
        try {
            PreparedStatement statement = SQLite.getInstance().getConnection().prepareStatement(this.query.toString());

            DatabaseLogger.log(this.query.toString());

            for (WhereClause whereClause : this.whereClauses) {
                whereClause.bindValue(statement, whereClauses.indexOf(whereClause) + 1);
            }

            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    protected  <M extends AbstractModel<M>> M getModelFromResultSet(ResultSet result) {
        AbstractModel<M> model = this.getTable().getModel();

        try {
            // set id
            Field idField = model.getClass().getSuperclass().getDeclaredField("id");

            idField.setAccessible(true);
            idField.set(model, result.getInt("id"));

            // set other fields
            for (Field tableField : this.getTable().getDeclaredFields()) {
                ModelField field = new ModelField(tableField, model);

                field.setValue(result);
            }
        } catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException|NullPointerException e) {
            System.out.println("Query failed: " + this.query);

            e.printStackTrace();
        }

        this.resetQuery();

        return (ArrayList<M>) models;
    }

    public T printQuery() {
        System.out.println(this.query);

        return (T) this;
    }

    public <M extends AbstractModel<M>> M findOne() {
        try {
            ResultSet result = this.getStatement().executeQuery();

            if (result.next()) {
                return this.getModelFromResultSet(result);
            }
        } catch (SQLException e) {
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

    public T where(String column, String operator, Serializable value) {
        this.whereClauses.add(new WhereClause(column, operator, new SQLiteValue(value)));

        return (T) this;
    }

    public T filterById(int id) {
        this.where("id", "=", id);

        return (T) this;
    }

    public int count() {
        return this.find().size();
    }
}
