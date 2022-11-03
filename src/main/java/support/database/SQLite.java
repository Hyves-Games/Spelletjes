package support.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {
    public static final String DB_NAME = "database.db";

    public static final String DB_PATH = "jdbc:sqlite:src/main/resources/databases/" + DB_NAME;

    private Connection connection;

    private static SQLite instance;

    public SQLite() {
        try {
            this.connection = DriverManager.getConnection(DB_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SQLite getInstance() {
        if (SQLite.instance == null) {
            SQLite.instance = new SQLite();
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
