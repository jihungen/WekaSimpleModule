package hk.ust.ipam.weka;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.evaluation.SimpleWekaEvaluation;
import hk.ust.ipam.weka.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.result.SimpleWekaStatResultTable;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 20/8/14.
 */
public class SimpleWekaEvaluationTest {
    public static String IRIS_ARFF    = "src/test/resources/iris.arff";
    public static String IRIS_MODEL    = "src/test/resources/iris.model";

    @Test
    public void trainingModelTest() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = null;
        int idxClassAttr = -1;
        Instances trainingData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        Instances testData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String interestingClassName = "Iris-setosa";
        int ceIntervals = 30;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.trainAndTest(classifierName, removeIdx, trainingData, testData, interestingClassName, ceIntervals);

        SimpleWekaStatResultTable resultTable = evaluation.getStatResultTable();
        System.out.println(resultTable.toString());

        SimpleWekaCESummary ceSummary = evaluation.getCeSummary();
        System.out.println(ceSummary.toString());
    }

    @Test
    public void nfoldCrossValidation() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = null;
        int nFolds = 10;
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String interestingClassName = "Iris-setosa";
        int ceIntervals = 20;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.nFoldCrossValidation(classifierName, removeIdx, nFolds, data, interestingClassName, ceIntervals);

        SimpleWekaStatResultTable resultTable = evaluation.getStatResultTable();
        System.out.println(resultTable.toString());

        SimpleWekaCESummary ceSummary = evaluation.getCeSummary();
        System.out.println(ceSummary.toString());
    }
}
