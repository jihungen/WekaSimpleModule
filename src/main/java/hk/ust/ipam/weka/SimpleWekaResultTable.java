package hk.ust.ipam.weka;

import weka.core.Instance;
import weka.core.Instances;

import java.util.Arrays;

/**
 * Created by jeehoonyoo on 11/8/14.
 */
public class SimpleWekaResultTable {

    private int[][] resultTable;

    private double[] precision;
    private double[] recall;
    private double[] fmeasure;

    /**
     *
     * @param instances
     */
    public SimpleWekaResultTable(Instances instances) {
        int noClasses = instances.get(0).numClasses();
        initResult(noClasses);
    }

    public SimpleWekaResultTable(int noClasses) {
        initResult(noClasses);
    }

    /**
     *
     * @param noClasses
     */
    public void initResult(int noClasses) {
        this.resultTable = null;
        this.resultTable = new int[noClasses][noClasses];

        this.precision = null;
        this.precision = new double[noClasses];

        this.recall = null;
        this.recall = new double[noClasses];

        this.fmeasure = null;
        this.fmeasure = new double[noClasses];
    }

    /**
     *
     * @param instance
     * @param result
     */
    public void addResult(Instance instance, SimpleWekaResult result) {
        int idxActual = (int)instance.classValue();
        int idxResult = result.getClassIdx();

        this.resultTable[idxActual][idxResult]++;
    }

    /**
     *
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
     *
     * @param idxActual
     * @return
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
     *
     * @param idxActual
     * @return
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
     *
     * @return
     */
    public double[] getPrecision() {
        return precision;
    }

    /**
     *
     * @return
     */
    public double[] getRecall() {
        return recall;
    }

    /**
     *
     * @return
     */
    public double[] getFmeasure() {
        return fmeasure;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "SimpleWekaResultTable{" +
                "precision=" + Arrays.toString(precision) +
                ", recall=" + Arrays.toString(recall) +
                ", fmeasure=" + Arrays.toString(fmeasure) +
                '}';
    }
}
