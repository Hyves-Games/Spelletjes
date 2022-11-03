package support.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {
    public static final String DB_NAME = "database.db";

    public static final String DB_PATH = "jdbc:sqlite:src/main/resources/databases/" + DB_NAME;

    private final Connection connection;

    private static SQLite instance;

    public SQLite() throws SQLException {
        this.connection = DriverManager.getConnection(DB_PATH);
    }

    public static SQLite getInstance() {
        if (SQLite.instance == null) {
            try {
                SQLite.instance = new SQLite();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return SQLite.instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void execute(String query) {
        try {
            this.connection.createStatement().execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
