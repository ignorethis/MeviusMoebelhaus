package meviusmoebelhouse.model;

import java.math.BigDecimal;

public class Invoice {
    private int idInvoice = 0;
    private int idCustomer = 0;
    private Integer idStaff = null;
    private String firstName = null;
    private String lastName = null;
    private String shippingAddress = null;
    private String billOfGoods = null;
    private BigDecimal totalPrice = null;

    public int getIdInvoice() { return idInvoice; }

    public void setIdInvoice(int idInvoice) { this.idInvoice = idInvoice; }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdStaff() { return idStaff; }

    public void setIdStaff(int idStaff) { this.idStaff = idStaff; }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getShippingAddress() { return shippingAddress; }

    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getBillOfGoods() { return billOfGoods; }

    public void setBillOfGoods(String billOfGoods) { this.billOfGoods = billOfGoods; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("idInvoice: ").append(idInvoice).append(", ");
        sb.append("idCustomer: ").append(idCustomer).append(", ");
        sb.append("idStaff: ").append(idStaff).append(", ");
        sb.append("firstName: ").append(firstName).append(", ");
        sb.append("lastName: ").append(lastName).append(", ");
        sb.append("shippingAddress: ").append(shippingAddress).append(", ");
        sb.append("billOfGoods: ").append(billOfGoods).append(", ");
        sb.append("totalPrice: ").append(totalPrice).append(", ");
        return sb.toString();
    }
}
