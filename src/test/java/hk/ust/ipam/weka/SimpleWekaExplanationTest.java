package hk.ust.ipam.weka;

import hk.ust.ipam.weka.explanation.SimpleWekaExplanation;
import hk.ust.ipam.weka.explanation.SimpleWekaExplanationResult;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 18/9/14.
 */
public class SimpleWekaExplanationTest {
    public static String IRIS_ARFF    = "src/test/resources/iris.arff";
    public static String IRIS_MODEL    = "src/test/resources/iris.model";

    @Test
    public void explainInstance() {
        SimpleWekaModel model = new SimpleWekaModel(IRIS_MODEL);
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        int idxID = 0;

        String targetClassName = "Iris-setosa";
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(data, targetClassName);

        SimpleWekaExplanation explanation = new SimpleWekaExplanation(data);

        for (int i = 0; i < data.numInstances(); i++) {
            Instance currInst = data.get(i);
            SimpleWekaBinaryResult binaryResult = model.classifyInstance(currInst, idxID);

            // we are not interested in the instance to be classified as target class
            if (binaryResult.getClassifiedIdx() == idxTargetClass)
                continue;

            // follow the TDD, but not implemented yet
            SimpleWekaExplanationResult explanationResult = explanation.explainInstance(model, currInst, data, idxTargetClass);
            System.out.println(explanationResult.toString());
        }
    }
}
