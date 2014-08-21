package hk.ust.ipam.weka.result;

import java.util.Comparator;

/**
 * Created by jeehoonyoo on 21/8/14.
 */
public class SimpleWekaBinaryResultComparator implements Comparator<SimpleWekaBinaryResult> {
    private int targetIdx = 0;

    public SimpleWekaBinaryResultComparator(int targetIdx) {
        this.targetIdx = targetIdx;
    }

    public int getTargetIdx() {
        return targetIdx;
    }

    @Override
    public int compare(SimpleWekaBinaryResult o1, SimpleWekaBinaryResult o2) {
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
