package meviusmoebelhouse.model;

import javafx.scene.image.Image;

import java.math.BigDecimal;

public class Furniture {
    private int idFurniture = 0;
    private int idSubcategory = 0;
    private String name = null;
    private float width = 0;
    private float length = 0;
    private float height = 0;
    private BigDecimal price = null;
    private double rebate = 0;
    private boolean isActive = true;
    private String description = null;
    private Image image = null;

    public int getIdFurniture() {
        return idFurniture;
    }

    public void setIdFurniture(int idFurniture) {
        this.idFurniture = idFurniture;
    }

    public int getIdSubcategory() {
        return idSubcategory;
    }

    public void setIdSubcategory(int idSubcategory) {
        this.idSubcategory = idSubcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth(){
        return width;
    }

    public void setWidth(float width){
        this.width = width;
    }

    public float getLength(){
        return length;
    }

    public void setLength(float length){
        this.length = length;
    }

    public float getHeight(){
        return height;
    }

    public void setHeight(float hight){
        this.height = hight;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    public double getRebate(){
        return rebate;
    }

    public void setRebate(double rebate){
        this.rebate = rebate;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public BigDecimal getActualPrice() {
        return price.multiply(BigDecimal.valueOf((100 - getRebate())/100));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idFurniture: ").append(idFurniture).append(", ");
        sb.append("idSubcategory: ").append(idSubcategory).append(", ");
        sb.append("name: ").append(name).append(", ");
        sb.append("width: ").append(width).append(", ");
        sb.append("length: ").append(length).append(", ");
        sb.append("height: ").append(height).append(", ");
        sb.append("price: ").append(price).append(", ");
        sb.append("rebate: ").append(rebate).append(", ");
        sb.append("isActive: ").append(isActive);
        sb.append("description").append(description);
        return sb.toString();
    }
}
