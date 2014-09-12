package hk.ust.ipam.weka;

import hk.ust.ipam.weka.info.SimpleWekaInfoGain;
import hk.ust.ipam.weka.info.SimpleWekaInfoMethod;
import hk.ust.ipam.weka.info.SimpleWekaInfoResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 11/9/14.
 */
public class SimpleWekaInfoGainTest {
    public static String IRIS_ARFF    = "src/test/resources/iris.arff";

    @Test
    public void calculateInfoGainTest() {
        SimpleWekaInfoMethod.MethodName methodName = SimpleWekaInfoMethod.MethodName.RANKER;

        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);

        int[] removeIdx = new int[1];
        int idxID = 0;
        removeIdx[0] = idxID;

        SimpleWekaInfoGain infoGain = new SimpleWekaInfoGain(methodName);
        SimpleWekaInfoResult result = infoGain.calculateInfoGain(data, removeIdx);

        System.out.println(result.toString());
    }
}
