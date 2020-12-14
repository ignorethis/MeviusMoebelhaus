package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private Connection conn;

    public InvoiceRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Invoice> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Invoice> result = new ArrayList<>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from invoice");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }
    public Invoice getByIdInvoice(int idInvoice) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from invoice where idInvoice = ?");
            stmt.setInt(1, idInvoice);
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

    public void create(Invoice invoice) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into invoice (idCustomer, idStaff, firstName, lastName, shippingAddress, totalPrice) values (?,?,?,?,?,?)");
            stmt.setInt(1, invoice.getIdCustomer());
            stmt.setObject(2, invoice.getIdStaff());
            stmt.setString(3, invoice.getFirstName());
            stmt.setString(4, invoice.getLastName());
            stmt.setString(5, invoice.getShippingAddress());
            stmt.setBigDecimal(6, invoice.getTotalPrice());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                invoice.setIdInvoice(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(Invoice invoice) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update invoice set idCustomer = ?, idStaff = ?, firstName = ?, lastName = ?, shippingAddress = ?, totalPrice = ? where idInvoice = ?");
            stmt.setInt(1, invoice.getIdCustomer());
            stmt.setInt(2, invoice.getIdStaff());
            stmt.setString(3, invoice.getFirstName());
            stmt.setString(4, invoice.getLastName());
            stmt.setString(5, invoice.getShippingAddress());
            stmt.setBigDecimal(6, invoice.getTotalPrice());
            stmt.setInt(7, invoice.getIdInvoice());
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Invoice invoice) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from invoice where idInvoice = ?");
            stmt.setInt(1, invoice.getIdInvoice()); 
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Invoice fromResultSet(ResultSet rs) throws Exception {
        Invoice inv = new Invoice();

        inv.setIdInvoice(rs.getInt("idInvoice"));
        inv.setIdCustomer(rs.getInt("idCustomer"));
        inv.setIdStaff(rs.getInt("idStaff"));
        inv.setFirstName(rs.getString("firstName"));
        inv.setLastName(rs.getString("lastName"));
        inv.setShippingAddress(rs.getString("shippingAddress"));
        inv.setTotalPrice(rs.getBigDecimal("totalPrice"));

        return inv;
    }
}
