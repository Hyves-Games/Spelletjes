package support.database;

import support.helpers.Utils;

import java.io.Serializable;
import java.lang.reflect.GenericDeclaration;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SQLiteValue {

    private Class type;

    private String value;

    public <T> SQLiteValue(Serializable value) {
        this.type = value.getClass();
        this.value = Utils.getValueForSerializable(value);
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
