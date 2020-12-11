package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.UserRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRoleRepository {
    private Connection conn;

    public UserRoleRepository(Connection conn) {
        this.conn = conn;
    }

    public List<UserRole> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<UserRole> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from userRole");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public UserRole getByIdUserRole(int idUserRole) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from userRole where idUserRole = ?");
            stmt.setInt(1, idUserRole);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return fromResultSet(rs);
            } else {
                return null;
            }
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public void create(UserRole userRole) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into userRole (name) values (?)");
            stmt.setString(1, userRole.getName());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                userRole.setIdUserRole(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(UserRole userRole) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update userRole set name = ? where idUserRole = ?");
            stmt.setString(1, userRole.getName());
            stmt.setInt(2, userRole.getIdUserRole());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(UserRole userRole) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from userRole where idUserRole = ?");
            stmt.setInt(1, userRole.getIdUserRole());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private UserRole fromResultSet(ResultSet rs) throws Exception {
        UserRole cus = new UserRole();

        cus.setIdUserRole(rs.getInt("idUserRole"));
        cus.setName(rs.getString("name"));

        return cus;
    }

}
