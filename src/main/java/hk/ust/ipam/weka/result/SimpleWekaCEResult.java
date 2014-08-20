package hk.ust.ipam.weka.result;

import weka.core.Instance;

/**
 * Created by jeehoonyoo on 19/8/14.
 */
public class SimpleWekaCEResult implements Comparable<SimpleWekaCEResult> {
    private Instance instance;
    private double score;

    public SimpleWekaCEResult(Instance instance, double score) {
        this.instance = instance;
        this.score = score;
    }

    public Instance getInstance() {
        return instance;
    }

    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SimpleWekaCEInstance{" +
                "instance=" + instance +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(SimpleWekaCEResult o) {
        if (this.score > o.score)
            return -1;
        else if (this.score == o.score)
            return 0;
        else
            return 1;
    }
}
