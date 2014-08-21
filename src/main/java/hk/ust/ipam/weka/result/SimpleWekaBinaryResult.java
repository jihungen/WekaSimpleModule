package hk.ust.ipam.weka.result;

import java.util.Arrays;

/**
 * Manages result with some useful information
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaBinaryResult {
    public final static double NO_ID   = -1.0f;

    /**
     * The index of actual class of given instance
     */
    private int actualIdx;

    /**
     * scores for classes
     */
    private double[] scores;

    /**
     * ID to identify the instance. It should be positive.
     */
    private double id;

    /**
     *
     * @param actualIdx     The index of actual class of given instance
     * @param scores   The score of actual class
     * @param id    ID for the instance
     */
    public SimpleWekaBinaryResult(int actualIdx, double[] scores, double id) {
        this.actualIdx = actualIdx;
        this.scores = scores;

        this.id = id;
        if (id < 0)
            this.id = NO_ID;
    }

    /**
     * Gets the index of classified class.
     * @return  the index of classified class
     */
    public int getClassifiedIdx() {
        int highestIdx = 0;
        for (int i = 1; i < this.scores.length; i++) {
            if (scores[i] > scores[highestIdx])
                highestIdx = i;
        }

        return highestIdx;
    }

    /**
     * Gets actualIdx. Please check actualIdx variable.
     * @return  actualIdx
     */
    public int getActualIdx() {
        return actualIdx;
    }

    /**
     * Gets the score for classified class.
     * @return  the score for classified class.
     */
    public double getClassifiedScore() {
        double highestScore = this.scores[0];
        for (int i = 1; i < this.scores.length; i++) {
            if (scores[i] > highestScore)
                highestScore = scores[i];
        }

        return highestScore;
    }

    /**
     * Gets the score for actual class.
     * @return  the score for actual class
     */
    public double getScoreActual() {
        return this.scores[actualIdx];
    }

    /**
     * Gets the score for given index
     * @param index the index of class
     * @return  the score for given index
     */
    public double getScoreAt(int index) {
        if (index < 0 || index >= this.scores.length)
            return -1;

        return this.scores[index];
    }

    /**
     * Gets the scores for all the classes
     * @return  scores
     */
    public double[] getScores() {
        return scores;
    }

    /**
     * Gets the number of classes.
     * @return  the number of classes
     */
    public int numClasses() {
        return this.scores.length;
    }

    /**
     * Gets ID of the instance
     * @return  id of the instance
     */
    public double getId() {
        return id;
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWekaBinaryResult{" +
                "actualIdx=" + actualIdx +
                ", scores=" + Arrays.toString(scores) +
                ", id=" + id +
                '}';
    }
}
