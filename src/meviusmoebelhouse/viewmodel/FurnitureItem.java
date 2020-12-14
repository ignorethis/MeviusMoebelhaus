package meviusmoebelhouse.viewmodel;

import meviusmoebelhouse.model.Furniture;

import java.math.BigDecimal;

public class FurnitureItem {
    private ShoppingCart shoppingCart;
    private Furniture furniture;

    private int furnitureCount;

    public FurnitureItem(ShoppingCart shoppingCart, Furniture furniture) {
        this.shoppingCart = shoppingCart;
        this.furniture = furniture;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    public void increaseFurnitureCount() {
        furnitureCount++;
    }

    public void decreaseFurnitureCount() {
        if (furnitureCount > 1) {
            furnitureCount--;
        } else {
            shoppingCart.removeFurniture(furniture);
        }
    }

    public int getFurnitureCount() {
        return furnitureCount;
    }

    public void setFurnitureCount(int furnitureCount) {
        this.furnitureCount = furnitureCount;
    }

    public BigDecimal getFurnitureItemPrice(){
        return furniture.getActualPrice().multiply(BigDecimal.valueOf(furnitureCount));
    }
}
