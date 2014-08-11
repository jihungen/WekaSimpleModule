package hk.ust.ipam.weka;

import org.apache.log4j.Logger;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;

import java.io.*;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaModel {
    private FilteredClassifier classifier = null;
    private static Logger log = Logger.getLogger(SimpleWekaModel.class.getName());

    /**
     *
     */
    public SimpleWekaModel(String modelPath) {
        if (this.loadModel(modelPath) == false)
            this.log.error("cannot load the model");
    }

    /**
     *
     * @param classifierName
     */
    public SimpleWekaModel(ClassifierName classifierName) {
        this.classifier = new FilteredClassifier();
        this.setClassifier(classifierName);
    }

    /**
     *
     * @param trainingData
     * @param removingIdx
     * @param bSetClassIdxAuto
     * @return
     */
    public boolean trainModel(Instances trainingData, int[] removingIdx, boolean bSetClassIdxAuto) {
        this.removeIdx(removingIdx);

        if (trainingData.classIndex() < 0) {
            if (bSetClassIdxAuto == true)
                trainingData.setClassIndex(trainingData.numAttributes() - 1);
            else
            {
                this.log.error("cannot identify the class index of training data");
                return false;
            }
        }

        try {
            this.classifier.buildClassifier(trainingData);
        } catch (Exception e) {
            this.log.error("fail to build the classifier:", e);
            return false;
        }

        return true;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public boolean saveModel(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.classifier);
            oos.close();
        } catch (FileNotFoundException e) {
            this.log.error("fail to find file:", e);
            return false;
        } catch (IOException e) {
            this.log.error("cannot write the model:", e);
            return false;
        }

        return true;
    }

    /**
     *
     * @param fileName
     * @return
     */
    public boolean loadModel(String fileName) {

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.classifier = (FilteredClassifier) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            this.log.error("fail to find file:", e);
            return false;
        } catch (IOException e) {
            this.log.error("cannot read the model:", e);
            return false;
        } catch (ClassNotFoundException e) {
            this.log.error("cannot load the model:", e);
            return false;
        }

        return true;
    }

    /**
     *
     * @param instance
     * @return
     */
    public double[] classifyInstance(Instance instance) {
        try {
            return this.classifier.distributionForInstance(instance);
        } catch (Exception e) {
            this.log.error("cannot classify the given instance:", e);
            return null;
        }
    }

    /**
     *
     * @param instance
     * @param idxInterest
     * @return
     */
    public double classifyInstance(Instance instance, int idxInterest) {
        double[] result = classifyInstance(instance);
        if (idxInterest < 0 || idxInterest >= result.length)
            return -1;

        return result[idxInterest];
    }

    /**
     *
     * @param instance
     * @return
     */
    public SimpleWekaResult classifyInstanceHighest(Instance instance) {
        double[] result = classifyInstance(instance);
        int idxHighest = 0;

        for (int i = 1; i < result.length; i++)
        {
            if (result[i] > result[idxHighest])
                idxHighest = i;
        }

        return new SimpleWekaResult(idxHighest, (int)instance.classValue(),
                instance.classAttribute().value(idxHighest), result[idxHighest]);
    }

    /**
     *
     * @param removingIdx
     */
    private void removeIdx(int[] removingIdx) {
        if (removingIdx == null)
            return;

        Remove rm = new Remove();
        rm.setAttributeIndicesArray(removingIdx);
        this.classifier.setFilter(rm);
    }

    /**
     *
     * @param classifierName
     */
    private void setClassifier(ClassifierName classifierName) {
        if (classifierName == ClassifierName.RANDOM_FOREST)
            this.classifier.setClassifier(new RandomForest());
        else if (classifierName == ClassifierName.J48)
            this.classifier.setClassifier(new J48());
        else if (classifierName == ClassifierName.NAIVE_BAYES)
            this.classifier.setClassifier(new NaiveBayes());
    }
}
