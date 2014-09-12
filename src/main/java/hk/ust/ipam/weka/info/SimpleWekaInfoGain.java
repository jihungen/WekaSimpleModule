package hk.ust.ipam.weka.info;

import org.apache.log4j.Logger;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 11/9/14.
 */
public class SimpleWekaInfoGain {

    /**
     *
     */
    private InfoGainAttributeEval attributeEval;

    /**
     *
     */
    private ASSearch method;

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(SimpleWekaInfoGain.class.getName());

    public SimpleWekaInfoGain(SimpleWekaInfoMethod.MethodName methodName) {
        this.attributeEval = new InfoGainAttributeEval();
        this.method = SimpleWekaInfoMethod.getMethod(methodName);
    }

    public SimpleWekaInfoResult calculateInfoGain(Instances data, int[] removeIdx) {
        AttributeSelection attributeSelection = new AttributeSelection();
        attributeSelection.setEvaluator(attributeEval);
        attributeSelection.setSearch(this.method);

        try {
            attributeSelection.SelectAttributes(data);
        } catch (Exception e) {
            log.error("cannot select attributes from the given instance:", e);
            return null;
        }

        double[][] infoGain;

        try {
            infoGain = attributeSelection.rankedAttributes();
        } catch (Exception e) {
            log.error("cannot calculate rank:", e);
            return null;
        }

        SimpleWekaInfoResult result = new SimpleWekaInfoResult(data, removeIdx);
        result.updateInfoGain(infoGain);

        return result;
    }
}
