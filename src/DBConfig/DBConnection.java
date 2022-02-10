package DBConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private String user = "noodles";
    private String password = "";
    private String ip = "localhost";
    private String port = "5432";
    private String url = "jdbc:postgresql://";
    private static DBConnection instance;
    private Connection connection;

    private DBConnection(String DB) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url + ip + ":" + port + "/" + DB, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Database Connection Creation Failed: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance(String DB) throws SQLException {
        if (instance==null) {
            instance = new DBConnection(DB);
        }
        else if (instance.getConnection().isClosed()) {
            instance = new DBConnection(DB);
        }
        return instance;
    }
}
