SimpleWekaModule
===========

SimpleWekaModule is a simple wrapper for original Weka library (http://www.cs.waikato.ac.nz/ml/weka/). This module focuses on using Weka in a simple way. It may be useful for Weka beginners. You are very welcome to contribute this project!

SimpleWekaModel Examples
-----
Training a model given dataset: It is same as trainingModelTest method in SimpleWekaModelTest class. IRIS_ARFF is a file path for given dataset, and IRIS_MODEL is a file path for saving trained model.
```java
    public void trainingModelTest() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        int[] removeIdx = null;

        SimpleWekaModel model = new SimpleWekaModel(classifierName);
        model.trainModel(data, removeIdx);

        model.saveModel(IRIS_MODEL);
    }
```

Testing a trained model on given dataset: It is same as testModelTest method in SimpleWekaModelTest class. The model is already trained by the other dataset. In this example, the model is loaded from the file IRIS_MODEL. In the end of the method, it computes the statistical measures (Precision, Recall and F-measure), then prints them.
```java
    public void testModelTest() {
        SimpleWekaModel model = new SimpleWekaModel(IRIS_MODEL);
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        int idxID = 0;

        SimpleWekaStatisticalResult statisticalResult = new SimpleWekaStatisticalResult(data.numClasses());

        for (int i = 0; i < data.numInstances(); i++) {
            Instance curr = data.get(i);

            SimpleWekaBinaryResult result = model.classifyInstance(curr, idxID);
            statisticalResult.addResult(result);

            System.out.println(result.toString());
        }

        statisticalResult.computeResult();
        System.out.println(statisticalResult.toString());
    }
```

Evaluation Examples
-----
Evaluate a model with training and test setting. This module can also compute the cost-effectiveness in evaluation. It shows three factors in cost-effectiveness:
* Given budget (the number of target instances in data), what percentage of target instances can be found?
* To find all the target instances, what percentage of instances need to be inspected?
* The percentage of target instances to be found with given intervals

The last parameter in trainAndTest method represents the index of attribute as an ID of each instance. If the negative values is passed, this module does not use ID. It is strongly guided to put idxID in removeIdx which is the list of indexes ignored during classification.
```java
    public void trainingModelTest() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = new int[1];
        int idxClassAttr = -1;
        Instances trainingData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        Instances testData = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String targetClassName = "Iris-setosa";
        int ceIntervals = 30;

        int idxID = 0;
        removeIdx[0] = idxID;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.trainAndTest(classifierName, removeIdx, trainingData, testData, idxID);

        SimpleWekaStatisticalResult statisticalResult = evaluation.computeStatisticalResult();
        System.out.println(statisticalResult.toString());

        SimpleWekaCESummary ceSummary = evaluation.computeCESummary(targetClassName, ceIntervals);
        System.out.println(ceSummary.toString());
    }
```

Evaluate a model with n-fold cross-validation setting.
```java
    public void nfoldCrossValidation() {
        SimpleWekaClassifier.ClassifierName classifierName = SimpleWekaClassifier.ClassifierName.RANDOM_FOREST;
        int[] removeIdx = new int[1];
        int nFolds = 10;
        int idxClassAttr = -1;
        Instances data = SimpleWekaUtil.readData(IRIS_ARFF, idxClassAttr);
        String targetClassName = "Iris-setosa";
        int ceIntervals = 20;

        int idxID = 0;
        removeIdx[0] = idxID;

        SimpleWekaEvaluation evaluation = new SimpleWekaEvaluation();
        evaluation.nFoldCrossValidation(classifierName, removeIdx, data, nFolds, idxID);

        SimpleWekaStatisticalResult statisticalResult = evaluation.computeStatisticalResult();
        System.out.println(statisticalResult.toString());

        SimpleWekaCESummary ceSummary = evaluation.computeCESummary(targetClassName, ceIntervals);
        System.out.println(ceSummary.toString());
    }
```

Developed By
------------
* Jeehoon Yoo
