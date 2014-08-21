package hk.ust.ipam.weka.result;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaCESummary {
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

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(SimpleWekaCESummary.class.getName());

    public SimpleWekaCESummary() {
        init();
    }

    public void init() {
        this.ceAtInterval = null;
    }

    public void computeResult(List<SimpleWekaBinaryResult> binaryResults, int idxTarget, int noIntervals) {
        if (idxTarget < 0)
            return;

        if (noIntervals <= 1)
            return;

        int noTotalTargets = countTargets(binaryResults, idxTarget);
        Collections.sort(binaryResults, new SimpleWekaBinaryResultComparator(idxTarget));

        for (SimpleWekaBinaryResult curr: binaryResults)
            System.out.println(curr);

        this.ceAtInterval = new double[noIntervals - 1];
        this.foundGivenBudgetPosition = 0;
        this.foundAllPostition = 0;

        int noTotal = binaryResults.size();
        int noTargets = 0;

        int idxIntervals = 0;
        double unitIntervals = (double)noTotal / (double)noIntervals;

        for (int i = 0; i < noTotal; i++) {
            if (binaryResults.get(i).getActualIdx() == idxTarget)
                noTargets++;

            if (i == (int)(unitIntervals * (idxIntervals + 1))) {
                this.ceAtInterval[idxIntervals] = (double) noTargets / noTotalTargets;
                idxIntervals++;
            }

            if ((i + 1) == noTotalTargets)
                this.foundGivenBudgetPosition = (double)noTargets / noTotalTargets;

            if (noTargets == noTotalTargets && this.foundAllPostition == 0)
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

    public static List<SimpleWekaBinaryResult> getBinaryResultsBelow(List<SimpleWekaBinaryResult> binaryResults,
                                                                     int idxTarget, double point) {
        List<SimpleWekaBinaryResult> selectedBinaryResults = new ArrayList<SimpleWekaBinaryResult>();
        int idxStart = (int)((double)binaryResults.size() * point);

        for (int i = idxStart; i < binaryResults.size(); i++) {
            SimpleWekaBinaryResult curr = binaryResults.get(i);
            if (curr.getActualIdx() == idxTarget)
                selectedBinaryResults.add(curr);
        }

        return selectedBinaryResults;
    }

    private static int countTargets(List<SimpleWekaBinaryResult> binaryResults, int idxTarget) {
        int noTotalTargets = 0;

        for (SimpleWekaBinaryResult curr: binaryResults) {
            int currClassIdx = curr.getActualIdx();
            if (currClassIdx == idxTarget)
                noTotalTargets++;
        }

        return noTotalTargets;
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
