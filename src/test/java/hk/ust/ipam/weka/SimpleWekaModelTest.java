package hk.ust.ipam.weka;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.result.SimpleWekaResult;
import hk.ust.ipam.weka.result.SimpleWekaResultTable;
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
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, -1);

        SimpleWekaModel model = new SimpleWekaModel(SimpleWekaClassifier.ClassifierName.RANDOM_FOREST);
        model.trainModel(data, null, true);

        model.saveModel(IRIS_MODEL);
    }

    @Test
    public void testModelTest() {
        SimpleWekaModel model = new SimpleWekaModel(IRIS_MODEL);
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, -1);

        SimpleWekaResultTable resultTable = new SimpleWekaResultTable(data);

        for (int i = 0; i < data.numInstances(); i++) {
            Instance curr = data.get(i);
            SimpleWekaResult result = model.classifyInstanceHighest(curr);

            resultTable.addResult(curr, result);

            System.out.println(result.toString());
        }

        resultTable.computeResult();
        System.out.println(resultTable.toString());
    }
}
