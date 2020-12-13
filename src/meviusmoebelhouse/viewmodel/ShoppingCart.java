package meviusmoebelhouse.viewmodel;

import meviusmoebelhouse.model.Furniture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShoppingCart {
    private List<FurnitureItem> shoppingList = new ArrayList<>();

    public List<FurnitureItem> getShoppingList() {
        return shoppingList;
    }

    public void addFurniture(Furniture furniture) {
        addFurniture(furniture,1);
    }

    public void addFurniture(Furniture furniture, int count) {
        // get furnitureItem from shopping list where furnitureItem furniture is the furniture passed as argument
        Optional<FurnitureItem> furnitureMaybe = shoppingList.stream()
                .filter(fi -> fi.getFurniture().equals(furniture))
                .findFirst();
        if (furnitureMaybe.isPresent()) { // check if that item exists
            FurnitureItem furnitureItem = furnitureMaybe.get();
            furnitureItem.setFurnitureCount(furnitureItem.getFurnitureCount() + count);
        } else {
            FurnitureItem furnitureItem = new FurnitureItem(this, furniture);
            furnitureItem.setFurnitureCount(count);
            shoppingList.add(furnitureItem);
        }
    }

    public void removeFurniture(Furniture furniture) {
        // get furnitureItem from shopping list where furnitureItem furniture is the furniture passed as argument
        Optional<FurnitureItem> furnitureItemMaybe = shoppingList.stream()
                .filter(fi -> fi.getFurniture().equals(furniture))
                .findFirst();
        if (furnitureItemMaybe.isPresent()) { // check if that item exists
            shoppingList.remove(furnitureItemMaybe.get());
        }
    }

    public BigDecimal getTotalPrice(){
        BigDecimal total = BigDecimal.valueOf(0);
        for (FurnitureItem furnitureItem: shoppingList) {
            total = total.add(furnitureItem.getFurnitureItemPrice());
        }
        return total;
    }
}
