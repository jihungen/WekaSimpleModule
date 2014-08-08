package hk.ust.ipam.weka;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaUtil {
    public static Instances readData(String filename, int idxClass)
    {
        DataSource source;
        Instances data = null;

        try {
            source = new DataSource(filename);
            data = source.getDataSet();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (data == null)
        {
            System.out.println("Cannot load data file!");
            return null;
        }

        if (idxClass < 0)
            idxClass = data.numAttributes() - 1;

        data.setClassIndex(idxClass);

        return data;
    }
}
