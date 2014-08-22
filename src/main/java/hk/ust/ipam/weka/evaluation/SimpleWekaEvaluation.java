package hk.ust.ipam.weka.evaluation;

import hk.ust.ipam.weka.code.SimpleWekaReturnCode;
import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.result.SimpleWekaStatisticalResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
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
        if (this.classAttribute == null)
            return null;

        SimpleWekaStatisticalResult statisticalResult = new SimpleWekaStatisticalResult(this.classAttribute);

        for (SimpleWekaBinaryResult currResult: this.binaryResults)
            statisticalResult.addResult(currResult);

        statisticalResult.computeResult();
        return statisticalResult;
    }

    public SimpleWekaCESummary computeCESummary(String targetClassName, int ceIntervals) {
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(this.classAttribute, targetClassName);
        if (idxTargetClass == SimpleWekaReturnCode.NO_CLASS)
            return null;

        SimpleWekaCESummary ceSummary = new SimpleWekaCESummary();
        ceSummary.computeResult(this.binaryResults, idxTargetClass, ceIntervals);

        return ceSummary;
    }

    public List<SimpleWekaBinaryResult> getBinaryResultsRange(String targetClassName, double startPoint, double endPoint) {
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(this.classAttribute, targetClassName);
        if (idxTargetClass == SimpleWekaReturnCode.NO_CLASS)
            return null;

        return SimpleWekaCESummary.getBinaryResultsRange(this.binaryResults, idxTargetClass, startPoint, endPoint);
    }


}
