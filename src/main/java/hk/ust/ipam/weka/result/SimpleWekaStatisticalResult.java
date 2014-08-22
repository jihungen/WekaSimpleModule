package hk.ust.ipam.weka.result;

import weka.core.Attribute;

import java.util.Arrays;

/**
 * Weka library computes the statistical results, but provides them in specific ways.
 * For example, I want to test a trained classifier on some dataset.
 * But, Weka does not allow it. Weka provides only one method for it by Evaluation class.
 * Created by jeehoonyoo on 11/8/14.
 */
public class SimpleWekaStatisticalResult {

    /**
     * Result table stores the count of FP, FN, TP and TN for all the classes
     */
    private int[][] resultTable;

    /**
     * Class names
     */
    private String[] className;

    /**
     * Precision for all the classes
     */
    private double[] precision;

    /**
     * Recall for all the classes
     */
    private double[] recall;

    /**
     * F-measure for all the classes
     */
    private double[] fmeasure;

    /**
     * Initializes the member variables by the number of classes
     * @param classAttribute class attribute of target instances
     */
    public SimpleWekaStatisticalResult(Attribute classAttribute) {
        initResult(classAttribute);
    }

    /**
     * Initializes the member variables by the number of classes
     * @param classAttribute class attribute of target instances
     */
    public void initResult(Attribute classAttribute) {
        clear();

        int noClasses = classAttribute.numValues();

        this.className = new String[noClasses];
        for (int i = 0; i < noClasses; i++)
            this.className[i] = classAttribute.value(i);

        this.resultTable = new int[noClasses][noClasses];
        this.precision = new double[noClasses];
        this.recall = new double[noClasses];
        this.fmeasure = new double[noClasses];
    }

    /**
     * Clears all the objects
     */
    public void clear() {
        this.resultTable = null;
        this.className = null;
        this.precision = null;
        this.recall = null;
        this.fmeasure = null;
    }

    /**
     * Adds one result to the result table
     * @param result    SimpleWekaBinaryResult object for given instance
     */
    public void addResult(SimpleWekaBinaryResult result) {
        this.resultTable[result.getActualIdx()][result.getClassifiedIdx()]++;
    }

    /**
     * Compute the statistical results
     */
    public void computeResult() {
        int noClasses = this.precision.length;

        for (int idxActual = 0; idxActual < noClasses; idxActual++) {
            int tp = this.resultTable[idxActual][idxActual];
            int fp = this.calculateFP(idxActual);
            int fn = this.calculateFN(idxActual);

            this.precision[idxActual] = (double)tp / (double)(tp + fp);
            this.recall[idxActual] = (double)tp / (double)(tp + fn);
            this.fmeasure[idxActual] = 2 * this.precision[idxActual] * this.recall[idxActual] /
                    (this.precision[idxActual] + this.recall[idxActual]);
        }
    }

    /**
     * Calculates FP (False Positive)
     * @param idxActual The target index of class
     * @return  FP (False Positive) for a given class
     */
    private int calculateFP(int idxActual) {
        int fp = 0;
        for (int i = 0; i < this.resultTable.length; i++) {
            if (i == idxActual)
                continue;

            fp += this.resultTable[i][idxActual];
        }

        return fp;
    }

    /**
     * Calculates FN (False Negative)
     * @param idxActual The target index of class
     * @return  FN (False Negative) for a given class
     */
    private int calculateFN(int idxActual) {
        int fn = 0;
        for (int i = 0; i < this.resultTable.length; i++) {
            if (i == idxActual)
                continue;

            fn += this.resultTable[idxActual][i];
        }

        return fn;
    }

    /**
     * Gets precision
     * @return  precision
     */
    public double[] getPrecision() {
        return precision;
    }

    /**
     * Gets recall
     * @return  recall
     */
    public double[] getRecall() {
        return recall;
    }

    /**
     * Gets F-measure
     * @return  fmeasure
     */
    public double[] getFmeasure() {
        return fmeasure;
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWekaStatisticalResult{" +
                "className=" + Arrays.toString(className) +
                ", precision=" + Arrays.toString(precision) +
                ", recall=" + Arrays.toString(recall) +
                ", fmeasure=" + Arrays.toString(fmeasure) +
                '}';
    }
}
