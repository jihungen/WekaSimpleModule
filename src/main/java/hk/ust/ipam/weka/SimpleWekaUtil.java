package hk.ust.ipam.weka;

import org.apache.log4j.Logger;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.InputStream;

/**
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaUtil {
    private static Logger log = Logger.getLogger(SimpleWekaUtil.class.getName());

    public static Instances readData(String filename, int idxClass)
    {
        DataSource source;
        try {
            source = new DataSource(filename);
        } catch (Exception e) {
            log.error("cannot read data file:", e);
            return null;
        }

        return readData(source, idxClass);
    }

    public static Instances readData(InputStream fileStream, int idxClass)
    {
        DataSource source = new DataSource(fileStream);
        return readData(source, idxClass);
    }

    private static Instances readData(DataSource source, int idxClass)
    {
        Instances data;

        try {
            data = source.getDataSet();
        } catch (Exception e) {
            log.error("cannot read data file:", e);
            return null;
        }

        if (data == null)
        {
            log.error("cannot load data file");
            return null;
        }

        if (idxClass < 0) {
            log.info("The class is automatically set to the last attribute");
            idxClass = data.numAttributes() - 1;
        }

        data.setClassIndex(idxClass);
        return data;
    }
}
