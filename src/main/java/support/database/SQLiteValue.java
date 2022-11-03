package support.database;

import java.lang.reflect.GenericDeclaration;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLiteValue {

    private Class type;

    private String value;

    public <T> SQLiteValue(Class<T> type, String value) {
        this.type = type;
        this.value = value;
    }

    public <T> SQLiteValue(Class<T> type, Integer value) {
        this.type = type;
        this.value = value.toString();
    }

    public <T> SQLiteValue(Class<T> type, Float value) {
        this.type = type;
        this.value = value.toString();
    }

    public <T> SQLiteValue(Class<T> type, Double value) {
        this.type = type;
        this.value = value.toString();
    }

    public <T> SQLiteValue(Class<T> type, Boolean value) {
        this.type = type;
        this.value = value.toString();
    }

    public <T> SQLiteValue(Class<T> type, Timestamp value) {
        this.type = type;
        this.value = Long.toString(value.getTime());
    }

    public <T> GenericDeclaration getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void bindValueToPreparedStatement(PreparedStatement preparedStatement, int index) {
        try {
            switch (this.type.getSimpleName()) {
                case "String" -> preparedStatement.setString(index, this.value);
                case "Integer" -> preparedStatement.setInt(index, Integer.parseInt(this.value));
                case "Float" -> preparedStatement.setFloat(index, Float.parseFloat(this.value));
                case "Double" -> preparedStatement.setDouble(index, Double.parseDouble(this.value));
                case "Boolean" -> preparedStatement.setBoolean(index, Boolean.parseBoolean(this.value));
                case "Timestamp" -> preparedStatement.setTimestamp(index, new Timestamp(Long.parseLong(this.value)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
