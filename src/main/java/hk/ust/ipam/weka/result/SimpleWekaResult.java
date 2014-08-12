package hk.ust.ipam.weka.result;

/**
 * Manages result with some useful information
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaResult {
    /**
     * The index of classified class
     */
    private int classIdx;

    /**
     * The index of actual class of given instance
     */
    private int actualIdx;

    /**
     * The name of classified class
     */
    private String className;

    /**
     * The score of classified class
     */
    private double score;

    /**
     * All the necessary information of result. They will not be set by other methods
     * @param classIdx  The index of classified class
     * @param actualIdx The index of actual class of given instance
     * @param className The name of classified class
     * @param score The score of classified class
     */
    public SimpleWekaResult(int classIdx, int actualIdx, String className, double score) {
        this.classIdx = classIdx;
        this.actualIdx = actualIdx;
        this.className = className;
        this.score = score;
    }

    /**
     * Gets classIdx. Please check classIdx variable.
     * @return  classIdx
     */
    public int getClassIdx() {
        return classIdx;
    }

    /**
     * Gets actualIdx. Please check actualIdx variable.
     * @return  actualIdx
     */
    public int getActualIdx() {
        return actualIdx;
    }

    /**
     * Gets className. Please check className variable.
     * @return  className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets score. Please check score variable.
     * @return  score
     */
    public double getScore() {
        return score;
    }

    /**
     * Automatically generated toString method
     * @return  The values of all the members
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
