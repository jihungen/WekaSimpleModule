package hk.ust.ipam.weka.result;

/**
 * Manages result with some useful information
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaBinaryResult {
    /**
     * The index of classified class
     */
    private int idxClassified;

    /**
     * The index of actual class of given instance
     */
    private int idxActual;

    /**
     * The score of classified class
     */
    private double scoreClassified;

    /**
     * The score of actual class
     */
    private double scoreActual;


    /**
     *
     * @param idxClassified The index of classified class
     * @param idxActual     The index of actual class of given instance
     * @param scoreClassified   The score of classified class
     * @param scoreActual   The score of actual class
     */
    public SimpleWekaBinaryResult(int idxClassified, int idxActual, double scoreClassified, double scoreActual) {
        this.idxClassified = idxClassified;
        this.idxActual = idxActual;
        this.scoreClassified = scoreClassified;
        this.scoreActual = scoreActual;
    }

    /**
     * Gets idxClassified. Please check idxClassified variable.
     * @return  idxClassified
     */
    public int getIdxClassified() {
        return idxClassified;
    }

    /**
     * Gets idxActual. Please check idxActual variable.
     * @return  idxActual
     */
    public int getIdxActual() {
        return idxActual;
    }

    /**
     * Gets scoreClassified. Please check scoreClassified variable.
     * @return  scoreClassified
     */
    public double getScoreClassified() {
        return scoreClassified;
    }

    /**
     * Gets scoreActual. Please check scoreActual variable.
     * @return  scoreActual
     */
    public double getScoreActual() {
        return scoreActual;
    }

    /**
     * Automatically generated toString method
     * @return The values of all the members
     */
    @Override
    public String toString() {
        return "SimpleWekaBinaryResult{" +
                "idxClassified=" + idxClassified +
                ", idxActual=" + idxActual +
                ", scoreClassified=" + scoreClassified +
                ", scoreActual=" + scoreActual +
                '}';
    }
}
