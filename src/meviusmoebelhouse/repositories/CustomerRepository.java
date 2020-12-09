package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private Connection conn;

    public CustomerRepository(Connection conn) {
        this.conn = conn;
    }

    public List<Customer> getAll() throws Exception {
        Statement stmt = null;
        ResultSet rs = null;

        List<Customer> result = new ArrayList<Customer>();

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from customer");

            while (rs.next()) {
                result.add(fromResultSet(rs));
            }

            return result;
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public Customer getByIdCustomer(int idCustomer) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from customer where idCustomer = ?");
            stmt.setInt(1, idCustomer);
            rs = stmt.executeQuery();

            return fromResultSet(rs);

        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public int create(Customer customer) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("insert into customer (firstName, lastName, birthday, IBAN, emailAddress) values (?,?,?,?,?)");
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setDate(3, customer.getBirthday());
            stmt.setString(4, customer.getIBAN());
            stmt.setString(5, customer.getEmailAddress());
            
            return stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void update(Customer customer) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update customer set firstName = ?, lastName = ?, birthday = ?, IBAN = ?, emailAddress = ? where idCustomer = ?");
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setDate(3, customer.getBirthday());
            stmt.setString(4, customer.getIBAN());
            stmt.setString(5, customer.getEmailAddress());
            stmt.setInt(6, customer.getIdCustomer());

            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    public void delete(Customer customer) throws Exception {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement("delete from customer where idCustomer = ?");
            stmt.setInt(1, customer.getIdCustomer());
            
            stmt.executeUpdate();
        } finally {
            RepositoryHelper.CloseIfExists(stmt);
        }
    }

    private Customer fromResultSet(ResultSet rs) throws Exception {
        if (!rs.next()) {
            return null;
        }

        Customer cus = new Customer();

        cus.setIdCustomer(rs.getInt("idCustomer"));
        cus.setIdUser(rs.getInt("idUser"));
        cus.setFirstName(rs.getString("firstName"));
        cus.setLastName(rs.getString("lastName"));
        cus.setBirthday(rs.getString("birthday"));
        cus.setIBAN(rs.getString("IBAN"));
        cus.setEmailAddress(rs.getString("emailAddress"));

        return cus;
    }
}
