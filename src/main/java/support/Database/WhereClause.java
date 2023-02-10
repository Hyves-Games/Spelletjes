package support.database;

import java.sql.PreparedStatement;

public record WhereClause(String column, String operator, SQLiteValue value) {

    public String sqlQuery() {
        return this.column + " " + this.operator + " ?";
    }

    public void bindValue(PreparedStatement preparedStatement, int index) {
        this.value.bindValueToPreparedStatement(preparedStatement, index);
    }
}
