package hk.ust.ipam.weka;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class WekaUtil {
    public static Instances readData(String filename)
    {
        DataSource source = null;
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
            System.out.println("Cannot load file!");
            return null;
        }

        data.setClassIndex(data.numAttributes() - 1);
        return data;
    }
}
