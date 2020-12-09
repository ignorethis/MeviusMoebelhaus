package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection conn;

    public UserRepository(Connection conn){ this.conn = conn; }

    public List<User> getAll() throws Exception{
        Statement stmt = null;
        ResultSet rs = null;

        List<User> result = new ArrayList<User>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from user");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public User getByIdUser(int idUser) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from user where idUser = ?");
            stmt.setInt(1, idUser);
            rs = stmt.executeQuery();

            return fromResultSet(rs);
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public User getByUsernameAndPassword(String username, String password) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from user where username = ? and password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            return fromResultSet(rs);
        }finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public int create(User user) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into user (username, password, failedLoginAttempts) values (?,?,?)");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getFailedLoginAttempts());

            return stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void update(User user) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update user set username = ?, password = ?, failedLoginAttempts = ? where idUser = ?");
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getFailedLoginAttempts());
            stmt.setInt(4, user.getIdUser());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(User user) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from user where idUser = ?");
            stmt.setInt(1, user.getIdUser());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private User fromResultSet(ResultSet rs) throws Exception {
        User usr = new User();

        usr.setIdUser(rs.getInt("idUser"));
        usr.setUsername(rs.getString("username"));
        usr.setPassword(rs.getString("password"));
        usr.setFailedLoginAttempts(rs.getInt("failedLoginAttempts"));

        return usr;
    }
}
