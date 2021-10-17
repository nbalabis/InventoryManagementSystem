package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for product that can have associated parts.
 *
 * @author Nicholas Balabis
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for new product instance.
     *
     * @param id ID of the product.
     * @param name Name of the product.
     * @param price Price of the product.
     * @param stock Inventory of the product.
     * @param min Minimum inventory required of the product.
     * @param max Maximum inventory allowed of the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Product ID setter.
     *
     * @param id ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Product name setter.
     *
     * @param name Name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Product price setter.
     *
     * @param price Price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Product stock setter.
     *
     * @param stock Inventory of the product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Product minimum inventory setter.
     *
     * @param min Minimum inventory required of the product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Product maximum inventory setter.
     *
     * @param max Maximum inventory allowed of the product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Product ID getter.
     *
     * @return ID of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Product name getter.
     *
     * @return Name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Product price getter.
     *
     * @return Price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Product stock getter.
     *
     * @return Inventory of the product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Product Min getter.
     *
     * @return Minimum inventory of the product.
     */
    public int getMin() {
        return min;
    }

    /**
     * Product Max getter.
     *
     * @return Maximum inventory of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds part to the associated parts list of the product.
     *
     * @param part Part to add to the list.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes a part from the associated parts list of the product.
     *
     * @param selectedAssociatedPart Part to remove from the list.
     * @return Boolean value corresponding to the status of removal.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns all associated parts of the product.
     *
     * @return List of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
