package support.database;

import support.helpers.Utils;

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
                case "String" -> preparedStatement.setString(index, Utils.convertSerializableToString(this.value));
                case "Integer" -> preparedStatement.setInt(index, Utils.convertSerializableToInteger(this.value));
                case "Float" -> preparedStatement.setFloat(index, Utils.convertSerializableToFloat(this.value));
                case "Double" -> preparedStatement.setDouble(index, Utils.convertSerializableToDouble(this.value));
                case "Boolean" -> preparedStatement.setBoolean(index, Utils.convertSerializableToBoolean(this.value));
                case "Timestamp" -> preparedStatement.setTimestamp(index, Utils.convertSerializableToTimestamp(this.value));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
