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

        List<Subcategory> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from subcategory");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public Subcategory getByIdSubcategory(int idSubcategory) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from subcategory where idSubcategory = ?");
            stmt.setInt(1, idSubcategory);

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

    public void create(Subcategory subcategory) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into subcategory (idCategory, name) values (?,?)");
            stmt.setInt(1, subcategory.getIdCategory());
            stmt.setString(2, subcategory.getName());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                subcategory.setIdSubcategory(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(Subcategory subcategory) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update subcategory idCategory = ?, name = ? where idSubcategory = ?");
            stmt.setInt(1, subcategory.getIdCategory());
            stmt.setString(2, subcategory.getName());
            stmt.setInt(3, subcategory.getIdSubcategory());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Subcategory subcategory) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("delete from subcategory where idSubcategory = ?");
            stmt.setInt(1, subcategory.getIdSubcategory());
            
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
