package hk.ust.ipam.weka;

import org.junit.Test;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaModelTest {
    public static String CONTACT_LENSES_ARFF    = "src/test/resources/contactLenses.arff";
    public static String CONTACT_LENSES_MODEL    = "src/test/resources/contactLenses.model";

    @Test
    public void trainingModelTest() {
        Instances data = SimpleWekaUtil.readData(CONTACT_LENSES_ARFF, -1);

        SimpleWekaModel model = new SimpleWekaModel(ClassifierName.RANDOM_FOREST);
        model.trainModel(data, null, true);

        model.saveModel(CONTACT_LENSES_MODEL);
    }

    @Test
    public void testModelTest() {
        SimpleWekaModel model = new SimpleWekaModel(CONTACT_LENSES_MODEL);
        Instances data = SimpleWekaUtil.readData(CONTACT_LENSES_ARFF, -1);

        for (int i = 0; i < data.numInstances(); i++) {
            Instance curr = data.get(i);
            SimpleWekaResult result = model.classifyInstanceHighest(curr);

            System.out.println(result.toString());
        }
    }
}
