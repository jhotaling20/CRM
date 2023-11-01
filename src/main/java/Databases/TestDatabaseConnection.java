package Databases;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestDatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/data";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Patriots20";

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
    }
}
