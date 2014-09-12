package hk.ust.ipam.weka.info;

import hk.ust.ipam.weka.util.SimpleWekaUtil;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeehoonyoo on 11/9/14.
 */
public class SimpleWekaInfoResult {
    List<SimpleWeakInfoAttribute> attributes = null;

    public SimpleWekaInfoResult(Instances data, int[] removeIdx) {
        this.attributes = new ArrayList<SimpleWeakInfoAttribute>();
        for (int i = 0; i < data.numAttributes(); i++) {
            Attribute currAttr = data.attribute(i);
            int currIdx = currAttr.index();

            // do not calculate information gain for class attribute
            if (data.classIndex() == currIdx)
                continue;

            // skip the attribute excluded in classification
            if (SimpleWekaUtil.include(removeIdx, currIdx) == true)
                continue;

            this.attributes.add(new SimpleWeakInfoAttribute(currIdx, currAttr.name()));
        }
    }

    public void updateInfoGain(double[][] rank) {
        if (this.attributes == null || this.attributes.size() <= 0)
            return;

        for (double[] currRank: rank) {
            int currIdx = (int)currRank[0];
            double currInfoGain = currRank[1];

            updateInfoGain(currIdx, currInfoGain);
        }
    }

    private void updateInfoGain(int index, double infoGain) {
        for (SimpleWeakInfoAttribute curr: this.attributes) {
            if (curr.getIndex() == index) {
                curr.setInfoGain(infoGain);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "SimpleWekaInfoResult{" +
                "attributes=" + attributes +
                '}';
    }
}
