package model;

/**
 * Model for Outsourced Part.
 *
 * @author Nicholas Balabis
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor for a new Outsourced part.
     *
     * @param id Id of the part.
     * @param name Name of the part.
     * @param price Price of the part.
     * @param stock Inventory of the part.
     * @param min Minimum inventory required of the part.
     * @param max Maximum inventory allowed of the part.
     * @param companyName Company name of the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Company name setter.
     *
     * @param companyName Company name of the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Company name getter.
     *
     * @return Company name of the part.
     */
    public String getCompanyName() {
        return companyName;
    }
}
