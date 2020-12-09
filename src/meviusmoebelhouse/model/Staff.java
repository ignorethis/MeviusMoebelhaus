package meviusmoebelhouse.model;

public class Staff {
    private int idStaff = 0;
    private int idUser = 0;
    private String firstName = "";
    private String lastName = "";

    public int getIdStaff(){
        return idStaff;
    }

    public void setIdStaff(int idStaff){
        this.idStaff = idStaff;
    }

    public int getIdUser() { return idUser; }

    public void setIdUser(int idUser) { this.idUser = idUser; }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idStaff: ").append(idStaff).append(", ");
        sb.append("idUser: ").append(idUser).append(", ");
        sb.append("firstName: ").append(firstName).append(", ");
        sb.append("lastName: ").append(lastName).append(", ");
        return sb.toString();
    }
}
