package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.InvoiceDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDetailsRepository {
    private Connection conn;

    public InvoiceDetailsRepository(Connection conn) {
        this.conn = conn;
    }

    public List<InvoiceDetails> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<InvoiceDetails> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from invoiceDetails");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public void create(InvoiceDetails invoiceDetails) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into invoiceDetails (idInvoice, idFurniture, amount, price, totalPrice) values (?,?,?,?,?)");
            stmt.setInt(1, invoiceDetails.getIdInvoice());
            stmt.setInt(2, invoiceDetails.getIdFurniture());
            stmt.setInt(3, invoiceDetails.getAmount());
            stmt.setBigDecimal(4, invoiceDetails.getPrice());
            stmt.setBigDecimal(5, invoiceDetails.getTotalPrice());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                invoiceDetails.setIdInvoiceDetails(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(InvoiceDetails InvoiceDetails) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update invoiceDetails set idInvoice = ?, idFurniture = ?, amount = ?, price = ?, totalPrice = ? where idInvoiceDetails = ?");
            stmt.setInt(1, InvoiceDetails.getIdInvoice());
            stmt.setInt(2, InvoiceDetails.getIdFurniture());
            stmt.setInt(3, InvoiceDetails.getAmount());
            stmt.setBigDecimal(4, InvoiceDetails.getPrice());
            stmt.setBigDecimal(5, InvoiceDetails.getTotalPrice());
            stmt.setInt(6, InvoiceDetails.getIdInvoiceDetails());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(InvoiceDetails InvoiceDetails) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from invoiceDetails where idInvoiceDetails = ?");
            stmt.setInt(1, InvoiceDetails.getIdInvoiceDetails());
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private InvoiceDetails fromResultSet(ResultSet rs) throws Exception {
        InvoiceDetails id = new InvoiceDetails();

        id.setIdInvoiceDetails(rs.getInt("idInvoiceDetails"));
        id.setIdInvoice(rs.getInt("idInvoice"));
        id.setIdFurniture(rs.getInt("idFurniture"));
        id.setAmount(rs.getInt("amount"));
        id.setPrice(rs.getBigDecimal("price"));
        id.setTotalPrice(rs.getBigDecimal("totalPrice"));

        return id;
    }
}
