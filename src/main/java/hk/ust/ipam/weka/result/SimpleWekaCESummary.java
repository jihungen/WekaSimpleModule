package hk.ust.ipam.weka.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaCESummary {
    private List<SimpleWekaCEResult> listCEResults;
    private int idxInterest;

    /**
     * if inspect # of n / ceAtInterval, what percentage of target instances can be found?
     */
    private double[] ceAtInterval;

    /**
     * The position where all the target instances can be found
     */
    private double foundAllPostition;

    /**
     * The position where inspect instances with the budget of # of target instances
     */
    private double foundGivenBudgetPosition;

    public SimpleWekaCESummary(int idxInterest) {
        init(idxInterest);
    }

    public void init(int idxInterest) {
        clear();
        this.listCEResults = new ArrayList<SimpleWekaCEResult>();
        this.idxInterest = idxInterest;
    }

    public void clear() {
        if (this.listCEResults != null) {
            this.listCEResults.clear();
            this.listCEResults = null;
        }

        this.ceAtInterval = null;
    }

    public void addResult(SimpleWekaCEResult ceResult) {
        this.listCEResults.add(ceResult);
    }

    public void computeResult(int noIntervals) {
        if (this.idxInterest < 0)
            return;

        if (noIntervals <= 1)
            return;

        int noTotalInterests = countInterests(this.listCEResults, this.idxInterest);

        Collections.sort(this.listCEResults);

        this.ceAtInterval = new double[noIntervals - 1];
        this.foundGivenBudgetPosition = 0;
        this.foundAllPostition = 0;

        int noTotal = this.listCEResults.size();
        int noInterests = 0;

        int idxIntervals = 0;
        double unitIntervals = (double)noTotal / (double)noIntervals;

        for (int i = 0; i < noTotal; i++) {
            int currClassIdx = (int)this.listCEResults.get(i).getInstance().classValue();
            if (currClassIdx == this.idxInterest)
                noInterests++;

            if (i == (int)(unitIntervals * (idxIntervals + 1))) {
                this.ceAtInterval[idxIntervals] = (double) noInterests / noTotalInterests;
                idxIntervals++;
            }

            if ((i + 1) == noTotalInterests)
                this.foundGivenBudgetPosition = (double)noInterests / noTotalInterests;

            if (noInterests == noTotalInterests && this.foundAllPostition == 0)
                this.foundAllPostition = (double)i / noTotal;
        }
    }

    public double[] getCeAtInterval() {
        return ceAtInterval;
    }

    public double getFoundAllPostition() {
        return foundAllPostition;
    }

    public double getFoundGivenBudgetPosition() {
        return foundGivenBudgetPosition;
    }

    private static int countInterests(List<SimpleWekaCEResult> listCEResults, int idxInterest) {
        int noTotalInterests = 0;

        for (SimpleWekaCEResult curr: listCEResults) {
            int currClassIdx = (int)curr.getInstance().classValue();
            if (currClassIdx == idxInterest)
                noTotalInterests++;
        }

        return noTotalInterests;
    }

    @Override
    public String toString() {
        return "SimpleWekaCESummary{" +
                "ceAtInterval=" + Arrays.toString(ceAtInterval) +
                ", foundAllPostition=" + foundAllPostition +
                ", foundGivenBudgetPosition=" + foundGivenBudgetPosition +
                '}';
    }
}
