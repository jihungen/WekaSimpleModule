package hk.ust.ipam.weka;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaResult {
    private int attrIdx;
    private String attrName;
    private double score;

    public SimpleWekaResult(int attrIdx, String attrName, double score) {
        this.attrIdx = attrIdx;
        this.attrName = attrName;
        this.score = score;
    }

    public int getAttrIdx() {
        return attrIdx;
    }

    public String getAttrName() {
        return attrName;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SimpleWekaResult{" +
                "attrIdx=" + attrIdx +
                ", attrName='" + attrName + '\'' +
                ", score=" + score +
                '}';
    }
}
