package hk.ust.ipam.weka.classification.result;

import java.util.Comparator;

/**
 * Compares SimpleWekaBinaryResult by the score.
 * Created by jeehoonyoo on 21/8/14.
 */
public class SimpleWekaBinaryResultComparator implements Comparator<SimpleWekaBinaryResult> {
    /**
     * The target index of classes
     */
    private int targetIdx = 0;

    /**
     * Sets the target index
     * @param targetIdx The target index of classes
     */
    public SimpleWekaBinaryResultComparator(int targetIdx) {
        this.targetIdx = targetIdx;
    }

    /**
     * Gets the target index
     * @return  targetIdx
     */
    public int getTargetIdx() {
        return targetIdx;
    }

    /**
     * Compares two SimpleWekaBinaryResult objects by the score
     * @param o1    SimpleWekaBinaryResult object to be compared
     * @param o2    SimpleWekaBinaryResult object to be compared
     * @return  Conventional result from the comparison
     */
    @Override
    public int compare(SimpleWekaBinaryResult o1, SimpleWekaBinaryResult o2) {
        // Gets the score of target class
        double o1Score = o1.getScoreAt(targetIdx);
        double o2Score = o2.getScoreAt(targetIdx);

        if (o1Score > o2Score)
            return -1;
        else if (o1Score == o2Score)
            return 0;
        else
            return 1;
    }
}
