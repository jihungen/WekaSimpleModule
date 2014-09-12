package hk.ust.ipam.weka.info;

import weka.attributeSelection.ASSearch;
import weka.attributeSelection.Ranker;

/**
 * Created by jeehoonyoo on 11/9/14.
 */
public final class SimpleWekaInfoMethod {
    /**
     * Available methods
     */
    public enum MethodName {
        RANKER
    }

    public static ASSearch getMethod(MethodName methodName) {
        if (methodName == MethodName.RANKER)
            return new Ranker();

        return null;
    }
}
