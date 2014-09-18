package hk.ust.ipam.weka.classification.result;

import hk.ust.ipam.weka.util.SimpleWekaUtil;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides cost-effectiveness evaluation on the binary classification results.
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

    /**
     * Initializes the member variables
     */
    public SimpleWekaCESummary() {
        init();
    }

    /**
     * Initializes the member variables
     */
    public void init() {
        this.ceAtInterval = null;
    }

    /**
     * Computes the cost-effectiveness values by the binary results.
     * @param binaryResults The binary results from classification
     * @param idxTarget The index of target class
     * @param noIntervals   The number of intervals representing cost-effectiveness
     */
    public void computeResult(List<SimpleWekaBinaryResult> binaryResults, int idxTarget, int noIntervals) {
        if (idxTarget < 0) {
            log.error("The index of target class is not valid");
            return;
        }

        if (noIntervals <= 1) {
            log.error("The number of intervals should be higher than 1");
            return;
        }

        int noTotalTargets = SimpleWekaUtil.countInstances(binaryResults, idxTarget);
        log.info("The number of target instances: " + noTotalTargets);

        Collections.sort(binaryResults, new SimpleWekaBinaryResultComparator(idxTarget));
        log.info("The binary results are sorted by the score of target index");

        this.ceAtInterval = new double[noIntervals - 1];
        this.foundGivenBudgetPosition = 0;
        this.foundAllPostition = 0;

        // The total number of instances
        int noTotal = binaryResults.size();
        log.info("The number of total instances: " + noTotal);

        // The count of target instances (to be counted)
        int noTargets = 0;

        int idxIntervals = 0;

        // To precisely calculate the interval by integer, keep the value by double
        double unitIntervals = (double)noTotal / (double)noIntervals;
        log.info("The size of each interval: " + unitIntervals);

        for (int i = 0; i < noTotal; i++) {
            if (binaryResults.get(i).getActualIdx() == idxTarget) {
                noTargets++;
            }

            // Puts the percentage of found instances in each interval
            if (i == (int)(unitIntervals * (idxIntervals + 1))) {
                this.ceAtInterval[idxIntervals] = (double) noTargets / noTotalTargets;
                idxIntervals++;
            }

            // If the number of inspected instances is same as the number of target instances
            if ((i + 1) == noTotalTargets)
                this.foundGivenBudgetPosition = (double)noTargets / noTotalTargets;

            // If all the target instances are found
            if (noTargets == noTotalTargets && this.foundAllPostition == 0)
                this.foundAllPostition = (double)i / noTotal;
        }
    }

    /**
     * Gets the cost-effectiveness values in each interval
     * @return  The cost-effectiveness values in each interval
     */
    public double[] getCeAtInterval() {
        return ceAtInterval;
    }

    /**
     * Gets the position where all the target instances can be found
     * @return  The position where all the target instances can be found
     */
    public double getFoundAllPostition() {
        return foundAllPostition;
    }

    /**
     * Gets the position where inspect instances with the budget of # of target instances
     * @return  The position where inspect instances with the budget of # of target instances
     */
    public double getFoundGivenBudgetPosition() {
        return foundGivenBudgetPosition;
    }

    /**
     * The purpose of this method is to find some instances within range.
     * It can be useful to identify which instances are not in top n.
     * @param binaryResults The binary results from classification
     * @param idxTarget The index of target class
     * @param bIncludeTarget    Whether find target class (true) or not (false). If it is false, this method
     *                          finds all the instances excluding target class
     * @param startPoint    The start point in search. If the value is greater than 1.0f, it is considered as
     *                      the index. Otherwise, it is the percentage of whole instances.
     * @param endPoint  The end point in search. The value is same as the start point.
     * @return  The list of selected binary results from search
     */
    public static List<SimpleWekaBinaryResult> getBinaryResultsRange(List<SimpleWekaBinaryResult> binaryResults,
                                                                     int idxTarget, boolean bIncludeTarget,
                                                                     double startPoint, double endPoint) {
        List<SimpleWekaBinaryResult> selectedBinaryResults = new ArrayList<SimpleWekaBinaryResult>();

        int idxStart = getIndex(binaryResults.size(), startPoint);
        int idxEnd = getIndex(binaryResults.size(), endPoint);

        if (idxStart < 0 || idxStart > idxEnd || idxEnd > binaryResults.size())
            return null;

        for (int i = idxStart; i < idxEnd; i++) {
            SimpleWekaBinaryResult curr = binaryResults.get(i);
            int actualIdx = curr.getActualIdx();

            // Checks whether the actual index of this instance is same as target index
            boolean bMatched = actualIdx == idxTarget;

            // It considers both true/false cases for searching
            if (bMatched == bIncludeTarget)
                selectedBinaryResults.add(curr);
        }

        return selectedBinaryResults;
    }

    /**
     * Gets the index given point
     * @param size  The number of instances
     * @param point The point to be used to calculate the index in the instances
     * @return  If the value is greater than 1.0f, it is considered as the index.
     *          Otherwise, it is the percentage of whole instances.
     */
    private static int getIndex(int size, double point) {
        if (point > 1.0f)
            return (int)point;
        else
            return (int)((double)size * point);
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWekaCESummary{" +
                "ceAtInterval=" + Arrays.toString(ceAtInterval) +
                ", foundAllPostition=" + foundAllPostition +
                ", foundGivenBudgetPosition=" + foundGivenBudgetPosition +
                '}';
    }
}
