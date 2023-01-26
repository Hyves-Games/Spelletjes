package support.database;

import support.enums.DatabaseTableEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLite {
    public static final String DB_NAME = "database.db";

    public static final String DB_PATH = "jdbc:sqlite:src/main/resources/databases/" + DB_NAME;

    private Connection connection;

    private static SQLite instance;

    public SQLite() {
        try {
            this.connection = DriverManager.getConnection(DB_PATH);
//            this.connection.createStatement().executeQuery("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {

        }
    }

    public static SQLite getInstance() {
        if (SQLite.instance == null) {
            SQLite.instance = new SQLite();

            try {
                DatabaseTableEnum.createTables();
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

            System.out.println("Failed to execute query: " + query);
        }
    }
}
