package meviusmoebelhouse.model;

import javafx.scene.image.Image;

public class Subcategory {
    private int idSubcategory = 0;
    private int idCategory = 0;
    private String name = null;
    private Image image = null;

    public int getIdSubcategory() {
        return idSubcategory;
    }

    public void setIdSubcategory(int idSubcategory) {
        this.idSubcategory = idSubcategory;
    }

    public int getIdCategory() { return idCategory; }

    public void setIdCategory(int idCategory) { this.idCategory = idCategory; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image = image;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idSubcategory: ").append(idSubcategory).append(", ");
        sb.append("idCategory: ").append(idCategory).append(", ");
        sb.append("name: ").append(name).append(", ");
        return sb.toString();
    }
}
