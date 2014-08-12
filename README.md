SimpleWekaModule
===========

SimpleWekaModule is a simple wrapper for original Weka library (http://www.cs.waikato.ac.nz/ml/weka/). This module focuses on using Weka in a simple way. It may be useful for Weka beginners. You are very welcome to contribute this project!

Simple Example
-----
Training a model given dataset (Same as the example code in SimpleWekaModelTest class). IRIS_ARFF is a file path for given dataset, and IRIS_MODEL is a file path for saving trained model.
```java
public void trainingModelTest() {
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, -1);

        SimpleWekaModel model = new SimpleWekaModel(SimpleWekaClassifier.ClassifierName.RANDOM_FOREST);
        model.trainModel(data, null, true);

        model.saveModel(IRIS_MODEL);
    }
```

Developed By
------------
* Jeehoon Yoo
