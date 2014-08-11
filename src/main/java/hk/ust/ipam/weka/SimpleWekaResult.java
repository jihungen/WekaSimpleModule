package hk.ust.ipam.weka;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaResult {
    private int classIdx;
    private int actualIdx;
    private String className;
    private double score;

    /**
     *
     * @param classIdx
     * @param actualIdx
     * @param className
     * @param score
     */
    public SimpleWekaResult(int classIdx, int actualIdx, String className, double score) {
        this.classIdx = classIdx;
        this.actualIdx = actualIdx;
        this.className = className;
        this.score = score;
    }

    /**
     *
     * @return
     */
    public int getClassIdx() {
        return classIdx;
    }

    /**
     *
     * @return
     */
    public int getActualIdx() {
        return actualIdx;
    }

    /**
     *
     * @return
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     * @return
     */
    public double getScore() {
        return score;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "SimpleWekaResult{" +
                "classIdx=" + classIdx +
                ", actualIdx=" + actualIdx +
                ", className='" + className + '\'' +
                ", score=" + score +
                '}';
    }
}
