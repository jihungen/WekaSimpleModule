package hk.ust.ipam.weka.model;

import hk.ust.ipam.weka.classification.result.SimpleWekaBinaryResult;
import hk.ust.ipam.weka.classifier.SimpleWekaClassifier;
import hk.ust.ipam.weka.util.SimpleWekaUtil;
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
        Remove rm = SimpleWekaUtil.removeIdx(removingIdx);
        if (rm != null)
            this.classifier.setFilter(rm);

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
     * Classifies an instance by given model.
     * @param instance  The instance to be classified
     * @param idxID The index of class attribute
     * @return  SimpleWekaBinaryResult object. Please check SimpleWekaBinaryResult class
     */
    public SimpleWekaBinaryResult classifyInstance(Instance instance, int idxID) {
        double[] score = classifyInstanceScores(instance);
        int idxActual = (int)instance.classValue();

        double id = SimpleWekaBinaryResult.NO_ID;
        if (idxID >= 0)
            id = instance.value(idxID);

        return new SimpleWekaBinaryResult(idxActual, score, id);
    }

    /**
     *
     * @param instance
     * @return
     */
    public SimpleWekaBinaryResult classifyInstance(Instance instance) {
        return classifyInstance(instance, -1);
    }

    /**
     * Set classifiers by given classifier name
     * @param classifierName    Classifier name to be set to this model
     */
    private void setClassifier(SimpleWekaClassifier.ClassifierName classifierName) {
        this.classifier.setClassifier(SimpleWekaClassifier.getClassifier(classifierName));
    }

    /**
     * Classifies an instance by given model
     * @param instance  The instance to be classified
     * @return  The scores of all classes
     */
    private double[] classifyInstanceScores(Instance instance) {
        try {
            return this.classifier.distributionForInstance(instance);
        } catch (Exception e) {
            log.error("cannot classify the given instance:", e);
            return null;
        }
    }
}
