package meviusmoebelhouse.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {

    private static final String sqliteConnection = "jdbc:sqlite:MeviusMoebelhaus.db";

    public static Connection createConnection() throws SQLException {
        Connection connection = null;
        try {
            String pathToDatabase = "jdbc:sqlite:db/MeviusMoebelhaus.db";

            connection = DriverManager.getConnection(pathToDatabase);

            System.out.println("Connection to database initialized.");

            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
