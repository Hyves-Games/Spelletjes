package support.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public record WhereClause(String column, String operator, SQLiteValue value) {

    public String getWhereClause() {
        return this.column + " " + this.operator + " ?";
    }

    public void bindValue(PreparedStatement preparedStatement, int index) {
        this.value.bindValueToPreparedStatement(preparedStatement, index);
    }
}
