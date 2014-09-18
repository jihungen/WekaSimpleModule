package hk.ust.ipam.weka.feature;

/**
 * Created by jeehoonyoo on 18/9/14.
 */
public abstract class SimpleWekaFeature {
    /**
     * The index of attribute
     */
    private int index;

    /**
     * The name of attribute
     */
    private String name;

    /**
     * Any value related to attribute
     */
    private double value;

    /**
     * Initializes with only index and name
     * @param index The index of attribute
     * @param name  The name of attribute
     */
    protected SimpleWekaFeature(int index, String name) {
        this.index = index;
        this.name = name;
    }

    /**
     * Gets index of attribute
     * @return  The index of attribute
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets name of attribute
     * @return  The name of attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the value related to attribute
     * @return  Any value related to attribute
     */
    protected double getValue() {
        return value;
    }

    /**
     * Sets the value related to attribute
     * @param value Any value related to attribute
     */
    protected void setValue(double value) {
        this.value = value;
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return index + "," + name + "," + value;
    }
}
