package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Category;
import meviusmoebelhouse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final Connection conn;

    public CategoryRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Category> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Category> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from category");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public Category getByIdCategory(int idCategory) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from category where idCategory = ?");
            stmt.setInt(1, idCategory);
            rs = stmt.executeQuery();

            return fromResultSet(rs);
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public int create(Category Category) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into category (name) values (?)");
            stmt.setString(1, Category.getName());
            
            return stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void update(Category Category) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update category set name = ? where idCategory = ?");
            stmt.setString(1, Category.getName());
            stmt.setInt(2, Category.getIdCategory());
           
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }

    }

    public void delete(Category Category) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from category where idCategory = ?");
            stmt.setInt(1, Category.getIdCategory()); 
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Category fromResultSet(ResultSet rs) throws Exception {
        if (!rs.next()) {
            return null;
        }

        Category cat = new Category();

        cat.setIdCategory(rs.getInt("idCategory"));
        cat.setName(rs.getString("name"));

        return cat;
    }
}
