package support.database;

import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLiteValue {

    private Class type;

    private Serializable value;

    public <T> SQLiteValue(Serializable value) {
        this.type = value.getClass();
        this.value = value;
    }

    public <T> GenericDeclaration getType() {
        return type;
    }

    public Serializable getValue() {
        return value;
    }

    public void bindValueToPreparedStatement(PreparedStatement preparedStatement, int index) {
        try {
            switch (this.type.getSimpleName()) {
                case "String" -> preparedStatement.setString(index, (String) this.value);
                case "Integer" -> preparedStatement.setInt(index, (Integer) this.getValue());
                case "Float" -> preparedStatement.setFloat(index, (Float) this.getValue());
                case "Double" -> preparedStatement.setDouble(index, (Double) this.getValue());
                case "Boolean" -> preparedStatement.setBoolean(index, (Boolean) this.getValue());
                case "Timestamp" -> preparedStatement.setTimestamp(index, (Timestamp) this.getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
