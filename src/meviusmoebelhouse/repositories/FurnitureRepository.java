package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Category;
import meviusmoebelhouse.model.Furniture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FurnitureRepository {
    private Connection conn;

    public FurnitureRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Furniture> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Furniture> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from furniture");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public void create(Furniture furniture) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into furniture (idSubcategory, name, width, length, height, price, rebate, isActive, description) values (?,?,?,?,?,?,?,?,?)");
            stmt.setInt(1, furniture.getIdSubcategory());
            stmt.setString(2, furniture.getName());
            stmt.setFloat(3, furniture.getWidth());
            stmt.setFloat(4, furniture.getLength());
            stmt.setFloat(5, furniture.getHeight());
            stmt.setBigDecimal(6, furniture.getPrice());
            stmt.setDouble(7, furniture.getRebate());
            stmt.setBoolean(8, furniture.getIsActive());
            stmt.setString(9, furniture.getDescription());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                furniture.setIdFurniture(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(Furniture furniture) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update furniture set idFurniture = ?, idSubcategory = ?, name = ?, width = ?, length = ?, height = ?, price = ?, rebate = ?, isActive = ?, description = ? where idFurniture = ?");
            stmt.setInt(1, furniture.getIdFurniture());
            stmt.setInt(2, furniture.getIdSubcategory());
            stmt.setString(3, furniture.getName());
            stmt.setFloat(4, furniture.getWidth());
            stmt.setFloat(5, furniture.getLength());
            stmt.setFloat(6, furniture.getHeight());
            stmt.setBigDecimal(7, furniture.getPrice());
            stmt.setDouble(8, furniture.getRebate());
            stmt.setBoolean(9, furniture.getIsActive());
            stmt.setString(10, furniture.getDescription());
            stmt.setInt(11, furniture.getIdFurniture());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Furniture furniture) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("delete from furniture where idFurniture = ?");
            stmt.setInt(1, furniture.getIdFurniture()); 
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Furniture fromResultSet(ResultSet rs) throws Exception {
        Furniture fur = new Furniture();

        fur.setIdFurniture(rs.getInt("idFurniture"));
        fur.setIdSubcategory(rs.getInt("idSubcategory"));
        fur.setName(rs.getString("name"));
        fur.setWidth(rs.getFloat("width"));
        fur.setLength(rs.getFloat("length"));
        fur.setHeight(rs.getFloat("height"));
        fur.setPrice(rs.getBigDecimal("price"));
        fur.setRebate(rs.getDouble("rebate"));
        fur.setIsActive(rs.getBoolean("isActive"));
        fur.setDescription(rs.getString("description"));

        return fur;
    }
}
