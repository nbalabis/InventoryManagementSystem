package model;

/**
 * Model for In-House Part.
 *
 * @author Nicholas Balabis
 */
public class InHouse  extends Part{
    private int machineId;

    /**
     * Constructor for a new In-House part instance.
     *
     * @param id Id of the part.
     * @param name Name of the part.
     * @param price Price of the part.
     * @param stock Inventory of the part.
     * @param min Minimum inventory required of the part.
     * @param max Maximum inventory allowed of the part.
     * @param machineId Machine ID of the part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Machine ID setter.
     *
     * @param machineId Machine ID of the part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    } //FIXME: method never used

    /**
     * Machine ID getter.
     *
     * @return Machine ID of the part.
     */
    public int getMachineId() {
        return machineId;
    }
}
