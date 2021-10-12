package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        for(Part pt : allParts){
            if(pt.getId() == partId){
                return pt;
            }
        }
        return null;
    }

    public static Product lookupProduct(int productId) {
        for(Product pd : allProducts){
            if(pd.getId() == productId){
                return pd;
            }
        }
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for(Part pt : allParts){
            if((pt.getName().toLowerCase()).contains(partName.toLowerCase())){
                matchingParts.add(pt);
            }
        }
        return matchingParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for(Product pd : allProducts){
            if((pd.getName().toLowerCase()).contains(productName.toLowerCase())){
                matchingProducts.add(pd);
            }
        }
        return matchingProducts;
    }

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        if(selectedPart == null)
            return false;
        allParts.remove(selectedPart);
        return true;
    }

    public static boolean deleteProduct(Product selectedProduct) {
        if(selectedProduct == null)
            return false;
        allProducts.remove(selectedProduct);
        return true;
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
