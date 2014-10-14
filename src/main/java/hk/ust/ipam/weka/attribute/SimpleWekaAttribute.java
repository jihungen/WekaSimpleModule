package hk.ust.ipam.weka.attribute;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jeehoonyoo on 18/9/14.
 */
public class SimpleWekaAttribute {
    public enum ATTR_TYPE {
        STRING,
        NUMERIC
    }

    private ATTR_TYPE type;
    private Set<String> rangeString;
    private Set<Double> rangeNumeric;

    public SimpleWekaAttribute(ATTR_TYPE type) {
        this.type = type;

        this.rangeString = null;
        this.rangeNumeric = null;

        if (this.type == ATTR_TYPE.STRING)
            this.rangeString = new HashSet<String>();
        else if (this.type == ATTR_TYPE.NUMERIC)
            this.rangeNumeric = new HashSet<Double>();
    }

    public void add(String value) {
        if (this.type != ATTR_TYPE.STRING)
            return;

        this.rangeString.add(value);
    }

    public void add(double value) {
        if (this.type != ATTR_TYPE.NUMERIC)
            return;

        this.rangeNumeric.add(value);
    }

    public ATTR_TYPE getType() {
        return type;
    }

    public Set<String> getRangeString() {
        return rangeString;
    }

    public Set<Double> getRangeNumeric() {
        return rangeNumeric;
    }

    @Override
    public String toString() {
        return "SimpleWekaAttribute{" +
                "type=" + type +
                ", rangeString=" + rangeString +
                ", rangeNumeric=" + rangeNumeric +
                '}';
    }
}
