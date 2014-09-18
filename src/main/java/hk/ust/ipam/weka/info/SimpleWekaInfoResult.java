package hk.ust.ipam.weka.info;

import hk.ust.ipam.weka.util.SimpleWekaUtil;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Passes the result of information gain in a good format.
 * For the detail of the format, please refer to SimpleWeakInfoAttribute class
 * Created by jeehoonyoo on 11/9/14.
 */
public class SimpleWekaInfoResult {
    /**
     * The list of SimpleWeakInfoAttribute
     */
    List<SimpleWeakInfoFeature> attributes = null;

    /**
     * Initializes with only data and the indexes to be excluded.
     * Identifies the index and name of attributes
     * @param data  Target data
     * @param removeIdx The indexes to be excluded
     */
    public SimpleWekaInfoResult(Instances data, int[] removeIdx) {
        this.attributes = new ArrayList<SimpleWeakInfoFeature>();
        for (int i = 0; i < data.numAttributes(); i++) {
            Attribute currAttr = data.attribute(i);
            int currIdx = currAttr.index();

            // do not calculate information gain for class attribute
            if (data.classIndex() == currIdx)
                continue;

            // skip the attribute excluded in classification
            if (SimpleWekaUtil.include(removeIdx, currIdx))
                continue;

            this.attributes.add(new SimpleWeakInfoFeature(currIdx, currAttr.name()));
        }
    }

    /**
     * Adds information gain from Weka output
     * @param rank  Weka output
     */
    public void updateInfoGain(double[][] rank) {
        if (this.attributes == null || this.attributes.size() <= 0)
            return;

        for (double[] currRank: rank) {
            // fixed indexes
            int currIdx = (int)currRank[0];
            double currInfoGain = currRank[1];

            updateInfoGain(currIdx, currInfoGain);
        }
    }

    /**
     * Adds information gain with given index
     * @param index The index of target attribute
     * @param infoGain  The information gain to be added
     */
    private void updateInfoGain(int index, double infoGain) {
        for (SimpleWeakInfoFeature curr: this.attributes) {
            if (curr.getIndex() == index) {
                curr.setInfoGain(infoGain);
                break;
            }
        }
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWekaInfoResult{" +
                "attributes=" + attributes +
                '}';
    }
}
