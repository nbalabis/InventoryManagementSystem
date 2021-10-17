package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for an inventory list of parts, products, and methods for both.
 *
 * @author Nicholas Balabis
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a part to inventory.
     *
     * @param newPart Part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to inventory.
     *
     * @param newProduct Prodcut to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches inventory for a part with a matching ID.
     *
     * @param partId Part ID to use for search.
     * @return Part with matching ID or null if none exist.
     */
    public static Part lookupPart(int partId) {
        for(Part pt : allParts){
            if(pt.getId() == partId){
                return pt;
            }
        }
        return null;
    }

    /**
     * Searches inventory for a product with a matching ID.
     *
     * @param productId Product ID to use for search.
     * @return Product with matching ID or null if none exist.
     */
    public static Product lookupProduct(int productId) {
        for(Product pd : allProducts){
            if(pd.getId() == productId){
                return pd;
            }
        }
        return null;
    }

    /**
     * Searches inventory for Parts that contains a partial string.
     *
     * @param partName Partial string to use for search.
     * @return List of parts with names that contain the partial string.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for(Part pt : allParts){
            if((pt.getName().toLowerCase()).contains(partName.toLowerCase())){
                matchingParts.add(pt);
            }
        }
        return matchingParts;
    }

    /**
     * Searches inventory for Products that contain a partial string.
     *
     * @param productName Partial string to use for search.
     * @return List of products with names that contain the partial string.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for(Product pd : allProducts){
            if((pd.getName().toLowerCase()).contains(productName.toLowerCase())){
                matchingProducts.add(pd);
            }
        }
        return matchingProducts;
    }

    /**
     * Replaces a Part in inventory.
     *
     * @param index Index of part.
     * @param selectedPart Part to be replaced.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Replaces a Product in inventory.
     *
     * @param index Index of product.
     * @param newProduct Product to be replaced.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes a part from inventory.
     *
     * @param selectedPart Part to be removed.
     * @return Boolean value corresponding to the status of removal.
     */
    public static boolean deletePart(Part selectedPart) {
        if(selectedPart == null)
            return false;
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Removes a product from inventory.
     *
     * @param selectedProduct Product to be removed.
     * @return Boolean value corresponding to the status of removal.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(selectedProduct == null)
            return false;
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Getter for all parts in inventory.
     *
     * @return List of all parts in inventory.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for all products in inventory.
     *
     * @return List of all products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
