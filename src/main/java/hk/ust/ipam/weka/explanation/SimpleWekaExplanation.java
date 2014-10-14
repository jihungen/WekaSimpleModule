package hk.ust.ipam.weka.explanation;

import hk.ust.ipam.weka.attribute.SimpleWekaAttribute;
import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.model.SimpleWekaModel;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeehoonyoo on 18/9/14.
 */
public class SimpleWekaExplanation {
    public enum ContributionType {
        PROBABILITY,
        CLASS
    }

    private List<SimpleWekaAttribute> attributeList;
    private int m;  // To-do: need to check what is called in the paper
    private ContributionType contributionType;

    public SimpleWekaExplanation(Instances data) {
        // To-do: extract the ranges of values for each attributes
        this.attributeList = new ArrayList<SimpleWekaAttribute>();
        this.m = 100;
        this.contributionType = ContributionType.PROBABILITY;
    }

    private void initAttribute(Instances data) {
        int numAttributes = data.numAttributes();
        for (int i = 0; i < numAttributes; i++) {
            Attribute dataAttr = data.attribute(i);
            if (dataAttr.isNumeric())
                this.attributeList.add(new SimpleWekaAttribute(SimpleWekaAttribute.ATTR_TYPE.NUMERIC));
            else
                this.attributeList.add(new SimpleWekaAttribute(SimpleWekaAttribute.ATTR_TYPE.STRING));
        }
    }

    private void extractValues(Instances data) {
        int numInstances = data.numInstances();
        int numAttributes = data.numAttributes();
        for (int idxInst = 0; idxInst < numInstances; idxInst++) {
            Instance currInst = data.instance(idxInst);

            for (int idxAttr = 0; idxAttr < numAttributes; idxAttr++) {
                SimpleWekaAttribute currAttr = this.attributeList.get(idxAttr);
                if (currAttr.getType() == SimpleWekaAttribute.ATTR_TYPE.NUMERIC)
                    currAttr.add(currInst.value(idxAttr));
                else
                    currAttr.add(currInst.stringValue(idxAttr));
            }
        }
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    // To-do
    public SimpleWekaExplanationResult explainAttribute(SimpleWekaModel model, Instances data,
                                                        int idxTargetClass) {
        // Maybe I need to put them into one method
        this.initAttribute(data);
        this.extractValues(data);

        // To-do implementation
        return null;
    }

    public SimpleWekaExplanationResult explainInstance(SimpleWekaModel model, Instance instance,
                                                       Instances data, int idxTargetClass) {
        int numAttributes = data.numAttributes();
        int idxClass = data.classIndex();
        for (int i = 0; i < numAttributes; i++) {
            if (idxClass == i)
                continue;

            double contribution = explainAttributeInInstance(model, instance, i, data, idxTargetClass);
            // To-do: do something with contribution
        }

        // To-do: implement SimpleWekaExplanationResult
        return null;
    }

    private double explainAttributeInInstance(SimpleWekaModel model, Instance instance, int idxTargetAttr,
                                  Instances data, int idxTargetClass) {
        double contribution = 0.0f;
        for (int i = 0; i < this.getM(); i++)
            contribution += explainAttributeInInstanceMono(model, instance, idxTargetAttr, data, idxTargetClass);

        return contribution / (double)this.getM();
    }

    private double explainAttributeInInstanceMono(SimpleWekaModel model, Instance instance, int idxTargetAttr,
                                  Instances data, int idxTargetClass) {

        int numAttributes = data.numAttributes();
        double contribution = 0.0f;
        Instance y = getInstanceRandomly(data);
        List<Integer> randomIdxList = SimpleWekaUtil.getRandomIdxList(data.numAttributes() - 1);
        Instance x1 = new DenseInstance(y);
        Instance x2 = new DenseInstance(y);

        x1.setDataset(data);
        x2.setDataset(data);

        for (int i = 0; i < numAttributes; i++)
        {
            int index = randomIdxList.get(i);
            if (index == idxTargetAttr)
            {
                x1.setValue(index, instance.value(index));
                break;
            }

            x1.setValue(index, instance.value(index));
            x2.setValue(index, instance.value(index));
        }

        return calculateContribution(model, x1, x2, idxTargetClass);
    }

    private static Instance getInstanceRandomly(Instances data) {
        int idxRandom = SimpleWekaUtil.getRandomIdx(data.numInstances() - 1);
        return data.instance(idxRandom);
    }

    private double calculateContribution(SimpleWekaModel model, Instance x1, Instance x2,
                                                int idxTargetClass) {
        SimpleWekaBinaryResult result1 = model.classifyInstance(x1);
        SimpleWekaBinaryResult result2 = model.classifyInstance(x2);

        if (this.contributionType == ContributionType.PROBABILITY)
            return result1.getScoreAt(idxTargetClass) - result2.getScoreAt(idxTargetClass);
        else
            return result1.getClassifiedIdx() == result2.getClassifiedIdx()? 0 : 1;
    }
}
