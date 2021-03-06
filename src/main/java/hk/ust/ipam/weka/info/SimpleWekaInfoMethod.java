package hk.ust.ipam.weka.info;

import weka.attributeSelection.ASSearch;
import weka.attributeSelection.Ranker;

/**
 * Shows available search method in information gain calculation
 * Created by jeehoonyoo on 11/9/14.
 */
public final class SimpleWekaInfoMethod {
    /**
     * Available methods
     */
    public enum MethodName {
        RANKER
    }

    /**
     * Gets search method by name
     * @param methodName    The name of search method
     * @return  search method
     */
    public static ASSearch getMethod(MethodName methodName) {
        if (methodName == MethodName.RANKER)
            return new Ranker();

        return null;
    }
}
