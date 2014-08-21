package hk.ust.ipam.weka;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.evaluation.SimpleWekaEvaluation;
import hk.ust.ipam.weka.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.result.SimpleWekaStatisticalResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 20/8/14.
 */
public class SimpleWekaEvaluationTest {
    public static String IRIS_ARFF    = "src/test/resources/iris.arff";

    @Test
    public void trainingModelTest() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = new int[1];
        int idxClassAttr = -1;
        Instances trainingData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        Instances testData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String targetClassName = "Iris-setosa";
        int ceIntervals = 30;

        int idxID = 0;
        removeIdx[0] = idxID;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.trainAndTest(classifierName, removeIdx, trainingData, testData, idxID);

        SimpleWekaStatisticalResult resultTable = evaluation.computeStatisticalResult();
        System.out.println(resultTable.toString());

        SimpleWekaCESummary ceSummary = evaluation.computeCESummary(targetClassName, ceIntervals);
        System.out.println(ceSummary.toString());
    }

    @Test
    public void nfoldCrossValidation() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = new int[1];
        int nFolds = 10;
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String targetClassName = "Iris-setosa";
        int ceIntervals = 20;

        int idxID = 0;
        removeIdx[0] = idxID;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.nFoldCrossValidation(classifierName, removeIdx, data, nFolds, idxID);

        SimpleWekaStatisticalResult resultTable = evaluation.computeStatisticalResult();
        System.out.println(resultTable.toString());

        SimpleWekaCESummary ceSummary = evaluation.computeCESummary(targetClassName, ceIntervals);
        System.out.println(ceSummary.toString());
    }
}
