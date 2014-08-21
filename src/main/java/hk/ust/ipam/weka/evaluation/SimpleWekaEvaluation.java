package hk.ust.ipam.weka.evaluation;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.result.SimpleWekaStatisticalResult;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaEvaluation {
    public final static int NO_CLASS = -1;

    private List<SimpleWekaBinaryResult> binaryResults = null;
    private Attribute classAttribute = null;

    public SimpleWekaEvaluation() {
        init();
    }

    public void trainAndTest(SimpleWekaClassifier.ClassifierName classifierName, int[] removeIdx,
                                        Instances trainingData, Instances testData, int idxID) {
        SimpleWekaModel model = new SimpleWekaModel(classifierName);
        model.trainModel(trainingData, removeIdx);

        evaluateModel(model, testData, idxID);
    }

    public void evaluateModel(SimpleWekaModel model, Instances testData, int idxID) {
        for (int i = 0; i < testData.numInstances(); i++) {
            Instance curr = testData.get(i);

            SimpleWekaBinaryResult binaryResult = model.classifyInstance(curr, idxID);
            this.binaryResults.add(binaryResult);
        }

        setClassAttribute(testData.classAttribute());
    }

    public void nFoldCrossValidation(SimpleWekaClassifier.ClassifierName classifierName, int[] removeIdx,
                                     Instances data, int nFolds, int idxID) {
        if (nFolds <= 1)
            return;

        Random random = new Random(System.currentTimeMillis());
        data.randomize(random);
        data.stratify(nFolds);

        for (int i = 0; i < nFolds; i++) {
            Instances trainingData = data.trainCV(nFolds, i);
            Instances testData = data.testCV(nFolds, i);

            SimpleWekaModel model = new SimpleWekaModel(classifierName);
            model.trainModel(trainingData, removeIdx);

            evaluateModel(model, testData, idxID);
        }
    }

    private void setClassAttribute(Attribute classAttribute) {
        this.classAttribute = classAttribute;
    }

    private void init() {
        if (this.binaryResults != null) {
            this.binaryResults.clear();
            this.binaryResults = null;
        }

        this.binaryResults = new ArrayList<SimpleWekaBinaryResult>();

        this.classAttribute = null;
    }

    public SimpleWekaStatisticalResult computeStatisticalResult() {
        int noClasses = this.binaryResults.get(0).numClasses();
        SimpleWekaStatisticalResult statisticalResult = new SimpleWekaStatisticalResult(noClasses);

        for (SimpleWekaBinaryResult currResult: this.binaryResults)
            statisticalResult.addResult(currResult);

        statisticalResult.computeResult();
        return statisticalResult;
    }

    public SimpleWekaCESummary computeCESummary(String targetClassName, int ceIntervals) {
        if (this.classAttribute == null)
            return null;

        int idxTargetClass = findTargetClassIdx(targetClassName);
        SimpleWekaCESummary ceSummary = new SimpleWekaCESummary();
        ceSummary.computeResult(this.binaryResults, idxTargetClass, ceIntervals);

        return ceSummary;
    }

    private int findTargetClassIdx(String className) {
        if (className == null)
            return NO_CLASS;

        int idx = this.classAttribute.indexOfValue(className);

        if (idx == -1)
            return NO_CLASS;
        else
            return idx;
    }
}
