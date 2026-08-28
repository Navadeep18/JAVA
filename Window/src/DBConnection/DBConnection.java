package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/employee_db"
            + "?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";

    // CHANGE THIS if your MySQL root password is different
    private static final String PASSWORD = "root";

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            }

            connection = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD
            );
        }

        return connection;
    }

    public static void closeConnection() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}