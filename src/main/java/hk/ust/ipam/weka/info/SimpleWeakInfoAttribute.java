package hk.ust.ipam.weka.info;

/**
 * Created by jeehoonyoo on 12/9/14.
 */
public class SimpleWeakInfoAttribute {
    private int index;
    private String name;
    private double infoGain;

    public SimpleWeakInfoAttribute(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setInfoGain(double infoGain) {
        this.infoGain = infoGain;
    }

    public double getInfoGain() {
        return infoGain;
    }

    @Override
    public String toString() {
        return "SimpleWeakInfoAttribute{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", infoGain=" + infoGain +
                '}';
    }
}
