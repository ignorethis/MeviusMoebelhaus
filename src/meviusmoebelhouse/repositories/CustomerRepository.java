package meviusmoebelhouse.repositories;

import meviusmoebelhouse.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        List<Customer> result = new ArrayList<>();

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

            if (rs.next()) {
                return fromResultSet(rs);
            } else {
                return null;
            }
        } finally {
            RepositoryHelper.CloseIfExists(rs, stmt);
        }
    }

    public Customer getByIdUser(int idUser) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement("select * from customer where idUser = ?");
            stmt.setInt(1, idUser);

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

    public void create(Customer customer) throws Exception {
        PreparedStatement stmt = null;
        ResultSet generatedKeysResultSet = null;

        try {
            stmt = conn.prepareStatement("insert into customer (idUser, firstName, lastName, birthday, IBAN, emailAddress) values (?,?,?,?,?,?)");
            stmt.setInt(1, customer.getIdUser());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getBirthday().format(DateTimeFormatter.ISO_DATE));
            stmt.setString(5, customer.getIBAN());
            stmt.setString(6, customer.getEmailAddress());

            stmt.executeUpdate();

            generatedKeysResultSet = stmt.getGeneratedKeys();
            if (generatedKeysResultSet.next()){
                customer.setIdCustomer(generatedKeysResultSet.getInt(1));
            } else {
                throw new Exception("No key was returned.");
            }
        } finally {
            RepositoryHelper.CloseIfExists(generatedKeysResultSet, stmt);
        }
    }

    public void update(Customer customer) throws Exception {
        PreparedStatement stmt = null;

        try {
            stmt = conn.prepareStatement("update customer set firstName = ?, lastName = ?, birthday = ?, IBAN = ?, emailAddress = ? where idCustomer = ?");
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getBirthday().format(DateTimeFormatter.ISO_DATE));
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
        Customer cus = new Customer();
        
        cus.setIdCustomer(rs.getInt("idCustomer"));
        cus.setIdUser(rs.getInt("idUser"));
        cus.setFirstName(rs.getString("firstName"));
        cus.setLastName(rs.getString("lastName"));
        cus.setBirthday(LocalDate.parse(rs.getString("birthday"), DateTimeFormatter.ISO_DATE));
        cus.setIBAN(rs.getString("IBAN"));
        cus.setEmailAddress(rs.getString("emailAddress"));

        return cus;
    }
}
