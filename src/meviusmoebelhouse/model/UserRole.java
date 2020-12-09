package meviusmoebelhouse.model;

public class UserRole {
    private int idUserRole = 0;
    private String name = null;

    public int getIdUserRole() {
        return idUserRole;
    }

    public void setIdUserRole(int idUserRole) {
        this.idUserRole = idUserRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("idUserRole: ").append(idUserRole).append(", ");
        sb.append("name: ").append(name).append(", ");
        return sb.toString();
    }
}
