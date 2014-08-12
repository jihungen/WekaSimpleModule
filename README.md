SimpleWekaModule
===========

SimpleWekaModule is a simple wrapper for original Weka library (http://www.cs.waikato.ac.nz/ml/weka/). This module focuses on using Weka in a simple way. It may be useful for Weka beginners. You are very welcome to contribute this project!

Examples
-----
Training a model given dataset: It is same as trainingModelTest method in SimpleWekaModelTest class. IRIS_ARFF is a file path for given dataset, and IRIS_MODEL is a file path for saving trained model.
```java
    public void trainingModelTest() {
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, -1);

        SimpleWekaModel model = new SimpleWekaModel(SimpleWekaClassifier.ClassifierName.RANDOM_FOREST);
        model.trainModel(data, null, true);

        model.saveModel(IRIS_MODEL);
    }
```

Testing a trained model on given dataset: It is same as testModelTest method in SimpleWekaModelTest class. The model is already trained by the other dataset. In this example, the model is loaded from the file IRIS_MODEL. In the end of the method, it computes the statistical measures (Precision, Recall and F-measure), then prints them.
```java
    public void testModelTest() {
        SimpleWekaModel model = new SimpleWekaModel(IRIS_MODEL);
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, -1);

        SimpleWekaResultTable resultTable = new SimpleWekaResultTable(data);

        for (int i = 0; i < data.numInstances(); i++) {
            Instance curr = data.get(i);
            SimpleWekaResult result = model.classifyInstanceHighest(curr);

            resultTable.addResult(curr, result);
        }

        resultTable.computeResult();
        System.out.println(resultTable.toString());
    }
```

Developed By
------------
* Jeehoon Yoo
