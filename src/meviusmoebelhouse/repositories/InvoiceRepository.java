package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Furniture;
import meviusmoebelhouse.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private Connection conn;

    public InvoiceRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Invoice> getAll() {
        Statement stmt = null;
        ResultSet rs = null;

        List<Invoice> result = new ArrayList<Invoice>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from invoice");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }

        return null;
    }
    public Invoice getByIdInvoice(int idInvoice) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from invoice where idInvoice = ?");
            stmt.setInt(1, idInvoice);

            rs = stmt.executeQuery();

            return fromResultSet(rs);
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public int create(Invoice invoice) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into invoice (idCustomer, idStaff, firstName, lastName, shippingAddress, billOfGoods, totalPrice) values (?,?,?,?,?,?,?)");
            stmt.setInt(1, invoice.getIdCustomer());
            stmt.setInt(2, invoice.getIdStaff());
            stmt.setString(3, invoice.getFirstName());
            stmt.setString(4, invoice.getLastName());
            stmt.setString(5, invoice.getShippingAddress());
            stmt.setString(6, invoice.getBillOfGoods());
            stmt.setBigDecimal(7, invoice.getTotalPrice());

            return stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void update(Invoice invoice) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update invoice set idCustomer = ?, idStaff = ?, firstName = ?, lastName = ?, shippingAddress = ?, billOfGoods = ?, totalPrice = ? where idInvoice = ?");
            stmt.setInt(1, invoice.getIdCustomer());
            stmt.setInt(2, invoice.getIdStaff());
            stmt.setString(3, invoice.getFirstName());
            stmt.setString(4, invoice.getLastName());
            stmt.setString(5, invoice.getShippingAddress());
            stmt.setString(6, invoice.getBillOfGoods());
            stmt.setBigDecimal(7, invoice.getTotalPrice());
            stmt.setInt(8, invoice.getIdInvoice());
            
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
        if (!rs.next()) {
            return null;
        }

        Invoice inv = new Invoice();

        inv.setIdInvoice(rs.getInt("idInvoice"));
        inv.setIdCustomer(rs.getInt("idCustomer"));
        inv.setIdStaff(rs.getInt("idStaff"));
        inv.setFirstName(rs.getString("firstName"));
        inv.setLastName(rs.getString("lastName"));
        inv.setShippingAddress(rs.getString("shippingAddress"));
        inv.setBillOfGoods(rs.getString("billOfGoods"));
        inv.setTotalPrice(rs.getBigDecimal("totalPrice"));

        return inv;
    }
}
