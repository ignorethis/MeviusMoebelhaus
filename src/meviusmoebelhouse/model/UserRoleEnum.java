package meviusmoebelhouse.model;

public enum UserRoleEnum {
    Admin,
    Staff,
    Customer,
    GoldCustomer;

    public int getIdUserRole() throws Exception {
        switch(this) {
            case Admin:
                return 1;
            case Staff:
                return 2;
            case Customer:
                return 3;
            case GoldCustomer:
                return 4;
            default:
                throw new Exception("'" + this + "' lacks an idUserRole.");
        }
    }

    public static UserRoleEnum parse(int idUserRole) throws Exception {
        switch(idUserRole) {
            case 1:
                return Admin;
            case 2:
                return Staff;
            case 3:
                return Customer;
            case 4:
                return GoldCustomer;
            default:
                throw new Exception("idUserRole '" + idUserRole + "' lacks a UserRoleEnum entry.");
        }
    }
}