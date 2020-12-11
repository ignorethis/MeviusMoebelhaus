package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Invoice;
import meviusmoebelhouse.model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StaffRepository {
    private Connection conn;

    public StaffRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Staff> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Staff> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from staff");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public Staff getByIdStaff(int idStaff) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from staff where idStaff = ?");
            stmt.setInt(1, idStaff);
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
    public void create(Staff staff) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into staff (idUser, firstName, lastName) values (?,?,?)");
            stmt.setInt(1, staff.getIdUser());
            stmt.setString(2, staff.getFirstName());
            stmt.setString(3, staff.getLastName());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                staff.setIdStaff(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(Staff staff) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update staff set idUser = ?, firstName = ?, lastName = ? where idStaff = ?");
            stmt.setInt(1, staff.getIdUser());
            stmt.setString(2, staff.getFirstName());
            stmt.setString(3, staff.getLastName());
            stmt.setInt(4, staff.getIdStaff());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Staff staff) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("delete from staff where idStaff = ?");
            stmt.setInt(1, staff.getIdStaff()); 
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Staff fromResultSet(ResultSet rs) throws Exception {
        Staff sta = new Staff();

        sta.setIdStaff(rs.getInt("idStaff"));
        sta.setIdUser(rs.getInt("idUser"));
        sta.setFirstName(rs.getString("firstName"));
        sta.setLastName(rs.getString("lastName"));

        return sta;
    }
}