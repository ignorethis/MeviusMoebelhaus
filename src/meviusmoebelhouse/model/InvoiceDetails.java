package meviusmoebelhouse.model;

import java.math.BigDecimal;

public class InvoiceDetails {
    private int idInvoiceDetails = 0;
    private int idInvoice = 0;
    private int idFurniture = 0;
    private int amount = 0;
    private BigDecimal price = null;
    private BigDecimal totalPrice = null;

    public int getIdInvoiceDetails() { return idInvoiceDetails; }

    public void setIdInvoiceDetails(int idInvoiceDetails) { this.idInvoiceDetails = idInvoiceDetails;  }

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getIdFurniture() {
        return idFurniture;
    }

    public void setIdFurniture(int idFurniture) {
        this.idFurniture = idFurniture;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() { return price; }

    public void setPrice(BigDecimal price) { this.price = price; }

    public BigDecimal getTotalPrice() { return totalPrice; }

    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("idInvoiceDetails: ").append(idInvoiceDetails).append(", ");
        sb.append("idInvoice: ").append(idInvoice).append(", ");
        sb.append("idFurniture: ").append(idFurniture).append(", ");
        sb.append("amount: ").append(amount).append(", ");
        sb.append("price: ").append(price).append(", ");
        sb.append("totalPrice: ").append(totalPrice).append(", ");
        return sb.toString();
    }
}
