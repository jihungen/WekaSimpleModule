package hk.ust.ipam.weka.info;

import hk.ust.ipam.weka.feature.SimpleWekaFeature;

/**
 * Information gain for an feature
 * Created by jeehoonyoo on 12/9/14.
 */
public class SimpleWeakInfoFeature extends SimpleWekaFeature {
    /**
     * Initializes with only index and name
     * @param index The index of feature
     * @param name  The name of feature
     */
    public SimpleWeakInfoFeature(int index, String name) {
        super(index, name);
    }

    /**
     * Adds information gain
     * @param infoGain  The information gain of feature
     */
    public void setInfoGain(double infoGain) {
        this.setValue(infoGain);
    }

    /**
     * Gets information gain
     * @return  The information gain of feature
     */
    public double getInfoGain() {
        return this.getValue();
    }
}
