package hk.ust.ipam.weka;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.evaluation.SimpleWekaEvaluation;
import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.classification.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.classification.result.SimpleWekaStatisticalResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.junit.Test;
import weka.core.Instances;

import java.util.List;

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

        SimpleWekaStatisticalResult statisticalResult = evaluation.computeStatisticalResult();
        System.out.println(statisticalResult.toString());

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

        System.out.println("Statistical results:");
        SimpleWekaStatisticalResult statisticalResult = evaluation.computeStatisticalResult();
        System.out.println(statisticalResult.toString());

        System.out.println("Cost-effectiveness results:");
        SimpleWekaCESummary ceSummary = evaluation.computeCESummary(targetClassName, ceIntervals);
        System.out.println(ceSummary.toString());

        int noTargets = SimpleWekaUtil.countInstances(data, targetClassName);
        System.out.println("The number of target instances: " + noTargets);

        System.out.println("closed issues in the outside of given budget:");
        List<SimpleWekaBinaryResult> issuesBelowBudgets = evaluation.getBinaryResultsRange(targetClassName, true, noTargets, data.numInstances() - 1);
        for (SimpleWekaBinaryResult curr: issuesBelowBudgets)
            System.out.println(curr.toString());

        System.out.println("open issues in given budget:");
        List<SimpleWekaBinaryResult> opponentIssuesAboveBudgets = evaluation.getBinaryResultsRange(targetClassName, false, 0, noTargets);
        for (SimpleWekaBinaryResult curr: opponentIssuesAboveBudgets)
            System.out.println(curr.toString());
    }
}
