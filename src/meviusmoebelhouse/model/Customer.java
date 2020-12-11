package meviusmoebelhouse.model;

import java.time.LocalDate;

public class Customer {
    private int idCustomer = 0;
    private Integer idUser = null;
    private String firstName = null;
    private String lastName = null;
    private LocalDate birthday = null;
    private String IBAN = null;
    private String emailAddress = null;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdUser() { return idUser; }

    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() { return birthday; }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idCustomer: ").append(idCustomer).append(", ");
        sb.append("idUser: ").append(idUser).append(", ");
        sb.append("firstName: ").append(firstName).append(", ");
        sb.append("lastName: ").append(lastName).append(", ");
        sb.append("birthday: ").append(birthday).append(", ");
        sb.append("IBAN: ").append(IBAN).append(", ");
        sb.append("emailAddress: ").append(emailAddress).append(", ");
        return sb.toString();
    }
}
