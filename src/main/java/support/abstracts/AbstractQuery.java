package support.abstracts;

import support.database.SQLite;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

abstract public class AbstractQuery {
    private final StringBuilder query;

    private final ArrayList<String> values = new ArrayList<>();

    public AbstractQuery() {
        this.query = new StringBuilder("SELECT * FROM " + this.getTable().getTableName());
    }

    abstract public AbstractTable getTable();

    private PreparedStatement getStatement() throws SQLException {
        PreparedStatement statement = SQLite.getInstance().getConnection().prepareStatement(this.query.toString());

        for (String value: this.values) {
            statement.setString(this.values.indexOf(value) + 1, value);
        }
        return statement;
    }

    private AbstractModel getModelFromResultSet(ResultSet result) throws SQLException, NoSuchFieldException, IllegalAccessException {
        AbstractModel model = this.getTable().getModel();

        // set id
        Field idField = model.getClass().getSuperclass().getDeclaredField("id");

        idField.setAccessible(true);
        idField.set(model, result.getInt("id"));

        // set other fields
        for (Field field : model.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            field.set(model, result.getObject(field.getName()));
        }

        return model;
    }

    public AbstractModel findOneById(int id) throws SQLException, NoSuchFieldException, IllegalAccessException {
        this.query.append(" WHERE id = ?");

        PreparedStatement statement = this.getStatement();
        statement.setInt(this.values.size() + 1, id);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return this.getModelFromResultSet(resultSet);
        }

        return null;
    }

    public AbstractModel[] find() {
        ArrayList<AbstractModel> models = new ArrayList<>();

        try {
            ResultSet result = this.getStatement().executeQuery();
            while (result.next()) {
                models.add(this.getModelFromResultSet(result));
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return models.toArray((AbstractModel[]) Array.newInstance(this.getTable().getModel().getClass(), models.size()));
    }

    public AbstractQuery where(String column, String operator, String value) {
        this.query.append(" WHERE ").append(column).append(" ").append(operator).append(" ?");
        this.values.add(value);

        return this;
    }
}
