package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Invoice;
import meviusmoebelhouse.model.Subcategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubcategoryRepository {
    private Connection conn;

    public SubcategoryRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Subcategory> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Subcategory> result = new ArrayList<Subcategory>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from subcategory");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
            rs = null;
            stmt = null;
        }
    }

    public Subcategory getByIdSubcategory(int idSubcategory) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from subcategory where idSubcategory = ?");
            stmt.setInt(1, idSubcategory);
            rs = stmt.executeQuery();

            return fromResultSet(rs);
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public int create(Subcategory Subcategory) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into subcategory (idCategory, name) values (?,?)");
            stmt.setInt(1, Subcategory.getIdCategory());
            stmt.setString(2, Subcategory.getName());

            return stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void update(Subcategory Subcategory) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update subcategory idCategory = ?, name = ? where idSubcategory = ?");
            stmt.setInt(1, Subcategory.getIdCategory());
            stmt.setString(2, Subcategory.getName());
            stmt.setInt(3, Subcategory.getIdSubcategory());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Subcategory Subcategory) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("delete from subcategory where idSubcategory = ?");
            stmt.setInt(1, Subcategory.getIdSubcategory()); 
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Subcategory fromResultSet(ResultSet rs) throws Exception {
        Subcategory sc = new Subcategory();

        sc.setIdSubcategory(rs.getInt("idSubcategory"));
        sc.setIdCategory(rs.getInt("idCategory"));
        sc.setName(rs.getString("name"));

        return sc;
    }
}
