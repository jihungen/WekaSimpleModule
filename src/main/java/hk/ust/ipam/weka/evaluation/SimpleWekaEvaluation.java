package hk.ust.ipam.weka.evaluation;

import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.result.SimpleWekaCEResult;
import hk.ust.ipam.weka.result.SimpleWekaCESummary;
import hk.ust.ipam.weka.result.SimpleWekaStatResultTable;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Random;

/**
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaEvaluation {
    public final static int NO_CLASS = -1;

    private SimpleWekaStatResultTable statResultTable;
    private SimpleWekaCESummary ceSummary;

    public SimpleWekaEvaluation() {
        this.statResultTable = null;
        this.ceSummary = null;
    }

    public void trainAndTest(SimpleWekaClassifier.ClassifierName classifierName, int[] removeIdx,
                                        Instances trainingData, Instances testData,
                                        String interestClassName, int ceIntervals) {
        SimpleWekaModel model = new SimpleWekaModel(classifierName);
        model.trainModel(trainingData, removeIdx);

        int idxInterest = findInterestClassIdx(testData, interestClassName);
        init(testData.numClasses(), idxInterest);

        evaluateModel(model, testData, idxInterest);
        computeResult(ceIntervals);
    }

    public void evaluateModel(SimpleWekaModel model, Instances testData, String interestClassName, int ceIntervals) {
        int idxInterest = findInterestClassIdx(testData, interestClassName);
        init(testData.numClasses(), idxInterest);

        evaluateModel(model, testData, idxInterest);
        computeResult(ceIntervals);
    }

    public void nFoldCrossValidation(SimpleWekaClassifier.ClassifierName classifierName, int[] removeIdx, int nFolds,
                                     Instances data, String interestClassName, int ceIntervals) {
        if (nFolds <= 1)
            return;

        int idxInterest = findInterestClassIdx(data, interestClassName);
        init(data.numClasses(), idxInterest);

        Random random = new Random(System.currentTimeMillis());
        data.randomize(random);
        data.stratify(nFolds);

        for (int i = 0; i < nFolds; i++) {
            Instances trainingData = data.trainCV(nFolds, i);
            Instances testData = data.testCV(nFolds, i);

            SimpleWekaModel model = new SimpleWekaModel(classifierName);
            model.trainModel(trainingData, removeIdx);

            evaluateModel(model, testData, idxInterest);
        }

        computeResult(ceIntervals);
    }

    public SimpleWekaStatResultTable getStatResultTable() {
        return statResultTable;
    }

    public SimpleWekaCESummary getCeSummary() {
        return ceSummary;
    }

    private void evaluateModel(SimpleWekaModel model, Instances testData, int idxInterest) {
        for (int i = 0; i < testData.numInstances(); i++) {
            Instance curr = testData.get(i);
            double[] score = model.classifyInstanceScores(curr);

            SimpleWekaBinaryResult binaryResult = SimpleWekaModel.getResult(curr, score);
            this.statResultTable.addResult(binaryResult);

            if (idxInterest >= 0)
            {
                SimpleWekaCEResult ceResult = SimpleWekaModel.getResult(curr, score, idxInterest);
                this.ceSummary.addResult(ceResult);
            }
        }
    }

    private int findInterestClassIdx(Instances instances, String className) {
        if (className == null)
            return NO_CLASS;

        Attribute classAttr = instances.classAttribute();
        int idx = classAttr.indexOfValue(className);

        if (idx == -1)
            return NO_CLASS;
        else
            return idx;
    }

    private void init(int noClasses, int idxInterest) {
        clear();

        this.statResultTable = new SimpleWekaStatResultTable(noClasses);
        this.ceSummary = new SimpleWekaCESummary(idxInterest);
    }

    private void clear() {
        if (this.statResultTable != null) {
            this.statResultTable.clear();
            this.statResultTable = null;
        }

        if (this.ceSummary != null) {
            this.ceSummary.clear();
            this.ceSummary = null;
        }
    }

    private void computeResult(int ceIntervals) {
        this.statResultTable.computeResult();
        this.ceSummary.computeResult(ceIntervals);
    }
}
