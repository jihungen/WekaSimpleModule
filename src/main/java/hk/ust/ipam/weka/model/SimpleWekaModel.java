package hk.ust.ipam.weka.model;

import hk.ust.ipam.weka.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.result.SimpleWekaCEResult;
import org.apache.log4j.Logger;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;

import java.io.*;

/**
 * Manages Weak Classifier. It trains and tests the instances, and saves and loads the trained classifier.
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaModel {
    /**
     * Weka classifier will not be exposed to the outside.
     */
    private FilteredClassifier classifier = null;

    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(SimpleWekaModel.class.getName());

    /**
     * Loads model from the file.
     * @param modelPath the path of model file.
     */
    public SimpleWekaModel(String modelPath) {
        if (!this.loadModel(modelPath))
            log.error("cannot load the model");
    }

    /**
     * Sets the type of classifier
     * @param classifierName the name of classifier. Please check available classifier in SimpleWekaClassifier class.
     */
    public SimpleWekaModel(SimpleWekaClassifier.ClassifierName classifierName) {
        this.classifier = new FilteredClassifier();
        this.setClassifier(classifierName);
    }

    /**
     * Trains the model with given data, trainingData. Also, can exclude some attributes in training process.
     * @param trainingData  training data
     * @param removingIdx   attributes will be excluded in training
     * @return  whether trains the model successfully or not.
     */
    public boolean trainModel(Instances trainingData, int[] removingIdx) {
        this.removeIdx(removingIdx);

        if (trainingData.classIndex() < 0)
            trainingData.setClassIndex(trainingData.numAttributes() - 1);

        try {
            this.classifier.buildClassifier(trainingData);
        } catch (Exception e) {
            log.error("fail to build the classifier:", e);
            return false;
        }

        return true;
    }

    /**
     * Saves the current model to the file
     * @param fileName  the path of file to be saved
     * @return  whether saves the model successfully or not.
     */
    public boolean saveModel(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.classifier);
            oos.close();
        } catch (FileNotFoundException e) {
            log.error("fail to find file:", e);
            return false;
        } catch (IOException e) {
            log.error("cannot write the model:", e);
            return false;
        }

        return true;
    }

    /**
     * Loads the model from the file
     * @param fileName  the path of model file
     * @return  whether loads the model successfully or not.
     */
    public boolean loadModel(String fileName) {

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.classifier = (FilteredClassifier) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            log.error("fail to find file:", e);
            return false;
        } catch (IOException e) {
            log.error("cannot read the model:", e);
            return false;
        } catch (ClassNotFoundException e) {
            log.error("cannot load the model:", e);
            return false;
        }

        return true;
    }

    /**
     * Classifies an instance by given model
     * @param instance  The instance to be classified
     * @return  The scores of all classes
     */
    public double[] classifyInstanceScores(Instance instance) {
        try {
            return this.classifier.distributionForInstance(instance);
        } catch (Exception e) {
            log.error("cannot classify the given instance:", e);
            return null;
        }
    }

    /**
     * Classifies an instance by given model, but returns the score for only interesting class
     * @param instance  The instance to be classified
     * @param idxInterest   The index of interesting class
     * @return  The score of only interesting class
     */
    public double classifyInstance(Instance instance, int idxInterest) {
        double[] score = classifyInstanceScores(instance);
        if (idxInterest < 0 || idxInterest >= score.length)
            return -1;

        return score[idxInterest];
    }

    /**
     * Classifies an instance by given model, but SimpleWekaBinaryResult for the class with the highest score
     * @param instance  The instance to be classified
     * @return  SimpleWekaBinaryResult object. Please check SimpleWekaBinaryResult class
     */
    public SimpleWekaBinaryResult classifyInstance(Instance instance) {
        double[] score = classifyInstanceScores(instance);
        return getResult(instance, score);
    }

    /**
     *
     * @param instance
     * @param score
     * @param idxInterest
     * @return
     */
    public static SimpleWekaCEResult getResult(Instance instance, double[] score, int idxInterest) {
        if (idxInterest < 0 || idxInterest >= score.length)
            return null;

        return new SimpleWekaCEResult(instance, score[idxInterest]);
    }

    /**
     *
     * @param instance
     * @param score
     * @return
     */
    public static SimpleWekaBinaryResult getResult(Instance instance, double[] score) {
        int idxClassified = 0;

        for (int i = 1; i < score.length; i++)
        {
            if (score[i] > score[idxClassified])
                idxClassified = i;
        }

        int idxActual = (int)instance.classValue();
        return new SimpleWekaBinaryResult(idxClassified, idxActual, score[idxClassified], score[idxActual]);
    }

    /**
     * Excludes some attributes in training
     * @param removingIdx   The array of indexes of attributes to be excluded
     */
    private void removeIdx(int[] removingIdx) {
        if (removingIdx == null)
            return;

        Remove rm = new Remove();
        rm.setAttributeIndicesArray(removingIdx);
        this.classifier.setFilter(rm);
    }

    /**
     * Set classifiers by given classifier name
     * @param classifierName    Classifier name to be set to this model
     */
    private void setClassifier(SimpleWekaClassifier.ClassifierName classifierName) {
        this.classifier.setClassifier(SimpleWekaClassifier.getClassifier(classifierName));
    }
}
