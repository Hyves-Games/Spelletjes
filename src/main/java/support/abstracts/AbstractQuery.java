package support.abstracts;

import support.database.SQLite;
import support.database.SQLiteValue;
import support.database.WhereClause;
import support.helpers.Utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

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
            for (Field tableField : this.getTable().getDeclaredFields()) {
                Field field = Utils.getFieldFromModel(model.getClass(), tableField.getName());
                field.setAccessible(true);

                String databaseName = Utils.camelCaseToUnderscore(field.getName());
                switch (field.getType().getSimpleName()) {
                    case "Timestamp":
                        field.set(model, result.getTimestamp(databaseName));
                        break;
                        case "ArrayList":
                            String array = result.getString(databaseName);
                            if (array.isEmpty()) {
                                continue;
                            }

                            String[] arrayItems = array.substring(1, array.length() - 1).split(",");

                            ParameterizedType listType = (ParameterizedType) field.getGenericType();
                            Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];
                            System.out.println(listClass);

                            if (listClass == Integer.class) {
                                ArrayList<Integer> list = new ArrayList<>() {{
                                    for (String item : arrayItems) {
                                        add(Integer.parseInt(item.trim()));
                                    }
                                }};

                                field.set(model, list);
                            } else {
                                ArrayList<String> list = new ArrayList<>() {{
                                    this.addAll(Arrays.asList(arrayItems));
                                }};

                                field.set(model, list);
                            }
                        break;
                    default:
                        Object objectResult = result.getObject(databaseName);
                        if (objectResult == null) {
                            continue;
                        }

                        if (field.getType().isEnum()) {
                            Enum<?> enumClass = createEnumInstance(objectResult.toString(), field.getType());
                            field.set(model, enumClass);
                        } else if (Utils.isModelField(field)) {
                            AbstractQuery<?> query = Utils.getQueryFromField(field);

                            field.set(model, query.findOneById((int) objectResult));
                        } else {
                            field.set(model, objectResult);
                        }

                        break;
                }
            }
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return (M) model;
    }

    @SuppressWarnings("unchecked")
    private <A extends Enum<A>> A createEnumInstance(String name, Type type) {
        return Enum.valueOf((Class<A>) type, name);
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
