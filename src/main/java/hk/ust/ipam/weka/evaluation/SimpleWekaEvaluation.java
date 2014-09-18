package hk.ust.ipam.weka.evaluation;

import hk.ust.ipam.weka.code.SimpleWekaCode;
import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.classification.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.classification.result.SimpleWekaStatisticalResult;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Provides evaluation settings such as training/test and n-fold cross-validation
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaEvaluation {
    /**
     * The list of binary results from classification. SimpleWekaEvaluation class will use
     * these results to compute statistical results and cost-effectiveness values.
     */
    private List<SimpleWekaBinaryResult> binaryResults = null;

    /**
     * Attribute object of class in target instances.
     */
    private Attribute classAttribute = null;

    /**
     *
     */
    public SimpleWekaEvaluation() {
        init();
    }

    /**
     *
     * @param classifierName
     * @param removeIdx
     * @param trainingData
     * @param testData
     * @param idxID
     */
    public void trainAndTest(SimpleWekaClassifier.ClassifierName classifierName, int[] removeIdx,
                                        Instances trainingData, Instances testData, int idxID) {
        SimpleWekaModel model = new SimpleWekaModel(classifierName);
        model.trainModel(trainingData, removeIdx);

        evaluateModel(model, testData, idxID);
    }

    /**
     *
     * @param model
     * @param testData
     * @param idxID
     */
    public void evaluateModel(SimpleWekaModel model, Instances testData, int idxID) {
        for (int i = 0; i < testData.numInstances(); i++) {
            Instance curr = testData.get(i);

            SimpleWekaBinaryResult binaryResult = model.classifyInstance(curr, idxID);
            this.binaryResults.add(binaryResult);
        }

        setClassAttribute(testData.classAttribute());
    }

    /**
     *
     * @param classifierName
     * @param removeIdx
     * @param data
     * @param nFolds
     * @param idxID
     */
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

    /**
     *
     * @param classAttribute
     */
    private void setClassAttribute(Attribute classAttribute) {
        this.classAttribute = classAttribute;
    }

    /**
     *
     */
    private void init() {
        if (this.binaryResults != null) {
            this.binaryResults.clear();
            this.binaryResults = null;
        }

        this.binaryResults = new ArrayList<SimpleWekaBinaryResult>();

        this.classAttribute = null;
    }

    /**
     *
     * @return
     */
    public SimpleWekaStatisticalResult computeStatisticalResult() {
        if (this.classAttribute == null)
            return null;

        SimpleWekaStatisticalResult statisticalResult = new SimpleWekaStatisticalResult(this.classAttribute);

        for (SimpleWekaBinaryResult currResult: this.binaryResults)
            statisticalResult.addResult(currResult);

        statisticalResult.computeResult();
        return statisticalResult;
    }

    /**
     *
     * @param targetClassName
     * @param ceIntervals
     * @return
     */
    public SimpleWekaCESummary computeCESummary(String targetClassName, int ceIntervals) {
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(this.classAttribute, targetClassName);
        if (idxTargetClass == SimpleWekaCode.NO_CLASS)
            return null;

        SimpleWekaCESummary ceSummary = new SimpleWekaCESummary();
        ceSummary.computeResult(this.binaryResults, idxTargetClass, ceIntervals);

        return ceSummary;
    }

    /**
     *
     * @param targetClassName
     * @param bIncludeTarget
     * @param startPoint
     * @param endPoint
     * @return
     */
    public List<SimpleWekaBinaryResult> getBinaryResultsRange(String targetClassName, boolean bIncludeTarget,
                                                              double startPoint, double endPoint) {
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(this.classAttribute, targetClassName);
        if (idxTargetClass == SimpleWekaCode.NO_CLASS)
            return null;

        return SimpleWekaCESummary.getBinaryResultsRange(this.binaryResults, idxTargetClass, bIncludeTarget,
                startPoint, endPoint);
    }

    /**
     *
     * @param targetClassName
     * @param bIncludeTarget
     * @param startPoint
     * @return
     */
    public List<SimpleWekaBinaryResult> getBinaryResultsRange(String targetClassName, boolean bIncludeTarget,
                                                              double startPoint) {
        int idxTargetClass = SimpleWekaUtil.findTargetClassIdx(this.classAttribute, targetClassName);
        if (idxTargetClass == SimpleWekaCode.NO_CLASS)
            return null;

        return SimpleWekaCESummary.getBinaryResultsRange(this.binaryResults, idxTargetClass, bIncludeTarget,
                startPoint, 1.0f);
    }
}
