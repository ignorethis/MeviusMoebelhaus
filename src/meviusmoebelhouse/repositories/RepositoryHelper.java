package meviusmoebelhouse.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class RepositoryHelper {
    public static void CloseIfExists(ResultSet rs, Statement stmt){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
    }

    public static void CloseIfExists(Statement stmt){
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
    }
}
