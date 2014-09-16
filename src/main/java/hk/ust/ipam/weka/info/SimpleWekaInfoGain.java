package hk.ust.ipam.weka.info;

import org.apache.log4j.Logger;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Instances;

/**
 * Sets the method by name, then calculates information gain for data
 * Created by jeehoonyoo on 11/9/14.
 */
public class SimpleWekaInfoGain {

    /**
     * Information gain evaluator
     */
    private InfoGainAttributeEval attributeEval;

    /**
     * Search method
     */
    private ASSearch method;

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(SimpleWekaInfoGain.class.getName());

    /**
     * Initializes with search method name
     * @param methodName    search method name
     */
    public SimpleWekaInfoGain(SimpleWekaInfoMethod.MethodName methodName) {
        this.attributeEval = new InfoGainAttributeEval();

        // Use predefined method
        this.method = SimpleWekaInfoMethod.getMethod(methodName);
    }

    /**
     * Calculates the information gain of all attributes in data
     * @param data  Target data
     * @param removeIdx The indexes to be excluded in classification
     * @return  SimpleWekaInfoResult, please refer to SimpleWekaInfoResult class
     */
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

        // weka default return form
        double[][] infoGain;
        try {
            infoGain = attributeSelection.rankedAttributes();
        } catch (Exception e) {
            log.error("cannot calculate rank:", e);
            return null;
        }

        // initializes SimpleWekaInfoResult by given data
        SimpleWekaInfoResult result = new SimpleWekaInfoResult(data, removeIdx);

        // adds information gain to result
        result.updateInfoGain(infoGain);

        return result;
    }
}
