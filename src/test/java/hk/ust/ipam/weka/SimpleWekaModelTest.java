package hk.ust.ipam.weka;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.classification.result.SimpleWekaStatisticalResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Sample codes for how to use SimpleWekaModel class
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaModelTest {
    public static String IRIS_ARFF    = "src/test/resources/iris.arff";
    public static String IRIS_MODEL    = "src/test/resources/iris.model";

    @Test
    public void trainingModelTest() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        int[] removeIdx = null;

        SimpleWekaModel model = new SimpleWekaModel(classifierName);
        model.trainModel(data, removeIdx);

        model.saveModel(IRIS_MODEL);
    }

    @Test
    public void testModelTest() {
        SimpleWekaModel model = new SimpleWekaModel(IRIS_MODEL);
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        int idxID = 0;

        SimpleWekaStatisticalResult statisticalResult = new SimpleWekaStatisticalResult(data.classAttribute());

        for (int i = 0; i < data.numInstances(); i++) {
            Instance curr = data.get(i);

            SimpleWekaBinaryResult result = model.classifyInstance(curr, idxID);
            statisticalResult.addResult(result);

            System.out.println(result.toString());
        }

        statisticalResult.computeResult();
        System.out.println(statisticalResult.toString());
    }
}
