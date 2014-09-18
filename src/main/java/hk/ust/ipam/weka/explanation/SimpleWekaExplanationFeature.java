package hk.ust.ipam.weka.explanation;

import hk.ust.ipam.weka.feature.SimpleWekaFeature;

/**
 * Created by jeehoonyoo on 18/9/14.
 */
public class SimpleWekaExplanationFeature extends SimpleWekaFeature {

    /**
     * Initializes with only index and name
     * @param index The index of feature
     * @param name  The name of feature
     */
    public SimpleWekaExplanationFeature(int index, String name) {
        super(index, name);
    }

    /**
     * Adds explanation information
     * @param explanation  The explanation information of feature
     */
    public void setExplanation(double explanation) {
        this.setValue(explanation);
    }

    /**
     * Gets explanation information
     * @return  The explanation information of feature
     */
    public double getExplanation() {
        return this.getValue();
    }
}
