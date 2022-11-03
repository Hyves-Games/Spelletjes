package support.abstracts;

import support.database.SQLite;
import support.database.SQLiteValue;
import support.database.WhereClause;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

abstract public class AbstractQuery<T extends AbstractQuery<T>> {

    private final StringBuilder query = new StringBuilder("SELECT * FROM " + this.getTable().getTableName());
    private final ArrayList<WhereClause> whereClauses = new ArrayList<>();

    abstract public AbstractTable getTable();

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
            for (Field field : model.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                field.set(model, result.getObject(field.getName()));
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
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
        } catch (SQLException e) {
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
}
