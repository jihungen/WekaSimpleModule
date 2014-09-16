package hk.ust.ipam.weka.info;

/**
 * Information gain for an attribute
 * Created by jeehoonyoo on 12/9/14.
 */
public class SimpleWeakInfoAttribute {
    /**
     * The index of attribute
     */
    private int index;

    /**
     * The name of attribute
     */
    private String name;

    /**
     * The information gain of attribute
     */
    private double infoGain;

    /**
     * Initializes with only index and name
     * @param index The index of attribute
     * @param name  The name of attribute
     */
    public SimpleWeakInfoAttribute(int index, String name) {
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
     * Adds information gain
     * @param infoGain  The information gain of attribute
     */
    public void setInfoGain(double infoGain) {
        this.infoGain = infoGain;
    }

    /**
     * Gets information gain
     * @return  The information gain of attribute
     */
    public double getInfoGain() {
        return infoGain;
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWeakInfoAttribute{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", infoGain=" + infoGain +
                '}';
    }
}
