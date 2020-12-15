package meviusmoebelhouse.model;

public class User {
    private int idUser = 0;
    private int idUserRole = 0;
    private String username = null;
    private String password = null;
    private int failedLoginAttempts = 0;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdUserRole() { return idUserRole; }

    public void setIdUserRole(int idUserRole) { this.idUserRole = idUserRole; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public boolean isAdmin() throws Exception {
        return getUserRole() == UserRoleEnum.Admin;
    }

    public UserRoleEnum getUserRole() throws Exception {
        return UserRoleEnum.parse(idUserRole);
    }

    public void setUserRole(UserRoleEnum userRole) throws Exception {
        idUserRole = userRole.getIdUserRole();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idUser: ").append(idUser).append(", ");
        sb.append("username: ").append(username).append(", ");
        sb.append("password: ").append(password).append(", ");
        sb.append("failedLoginAttempts: ").append(failedLoginAttempts).append(", ");
        return sb.toString();
    }



}
