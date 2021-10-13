package model;

/**
 * Model for all Parts.
 *
 * @author Nicholas Balabis
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for part instance.
     *
     * @param id ID of the part.
     * @param name Name of the part.
     * @param price Price of the part.
     * @param stock Inventory of the part.
     * @param min Minimum inventory required of the part.
     * @param max Maximum inventory allowed of the part.
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Part ID setter.
     *
     * @param id ID of the part.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Part name setter.
     *
     * @param name Name of the part.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Part price setter.
     *
     * @param price Price of the part.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Part stock setter.
     *
     * @param stock Inventory of the part.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Part minimum inventory setter.
     *
     * @param min Minimum inventory required of the part.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Part maximum inventory setter.
     *
     * @param max Maximum inventory allowed of the part.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * ID getter.
     *
     * @return ID of the part.
     */
    public int getId() {
        return id;
    }

    /**
     * Name getter.
     *
     * @return Name of the part.
     */
    public String getName() {
        return name;
    }

    /**
     * Price getter.
     *
     * @return price of the part.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Stock getter.
     *
     * @return Inventory of the part.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Min getter.
     *
     * @return Minimum inventory of the part.
     */
    public int getMin() {
        return min;
    }

    /**
     * Max getter.
     *
     * @return Maximum inventory of the part.
     */
    public int getMax() {
        return max;
    }
}
