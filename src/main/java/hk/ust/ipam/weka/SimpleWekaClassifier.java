package hk.ust.ipam.weka;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

/**
 * Manages available classifiers in this module
 * Created by jeehoonyoo on 12/8/14.
 */
public class SimpleWekaClassifier {

    /**
     * Available classifiers
     */
    public enum ClassifierName {
        RANDOM_FOREST,
        J48,
        NAIVE_BAYES
    }

    /**
     * Gets the classifier by name
     * @param classifierName    Classifier name to get
     * @return  Classifier from Weka
     */
    public static Classifier getClassifier(ClassifierName classifierName) {
        if (classifierName == ClassifierName.RANDOM_FOREST)
            return new RandomForest();
        else if (classifierName == ClassifierName.J48)
            return new J48();
        else if (classifierName == ClassifierName.NAIVE_BAYES)
            return new NaiveBayes();

        return null;
    }
}
