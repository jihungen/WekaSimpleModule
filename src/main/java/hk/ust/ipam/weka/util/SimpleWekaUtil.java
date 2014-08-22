package hk.ust.ipam.weka.util;

import hk.ust.ipam.weka.code.SimpleWekaReturnCode;
import hk.ust.ipam.weka.result.SimpleWekaBinaryResult;
import org.apache.log4j.Logger;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.InputStream;
import java.util.List;

/**
 * Utility static methods
 * Created by jeehoonyoo on 8/8/14.
 */
public class SimpleWekaUtil {
    /**
     * Logger
     */
    private static Logger log = Logger.getLogger(SimpleWekaUtil.class.getName());

    /**
     * Reads ARFF files
     * @param filename  ARFF file path
     * @param idxClass  The index of class attribute
     * @return  Read instances
     */
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

    /**
     * Reads ARFF stream
     * @param fileStream    ARFF file stream
     * @param idxClass  The index of class attribute
     * @return  Read instances
     */
    public static Instances readData(InputStream fileStream, int idxClass)
    {
        DataSource source = new DataSource(fileStream);
        return readData(source, idxClass);
    }

    /**
     * Reads ARFF file from DataSource. For detailed information, please check DataSource Weka document
     * @param source    ARFF file in DataSource format
     * @param idxClass  The index of class attribute
     * @return  Read instances
     */
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

    /**
     *
     * @param data
     * @param idxTargetClass
     * @return
     */
    public static int countInstances(Instances data, int idxTargetClass) {
        if (data.classIndex() < 0)
            return SimpleWekaReturnCode.NO_CLASS_DEFINED;

        int noTargets = 0;
        for (int i = 0; i < data.numInstances(); i++)
        {
            if (idxTargetClass == (int)data.get(i).classValue())
                noTargets++;
        }

        return noTargets;
    }

    /**
     *
     * @param data
     * @param targetClassName
     * @return
     */
    public static int countInstances(Instances data, String targetClassName) {
        if (data.classIndex() < 0)
            return SimpleWekaReturnCode.NO_CLASS_DEFINED;

        int idxTargetClass = findTargetClassIdx(data.classAttribute(), targetClassName);
        if (idxTargetClass == SimpleWekaReturnCode.NO_CLASS)
            return SimpleWekaReturnCode.NO_CLASS_DEFINED;

        return countInstances(data, idxTargetClass);
    }

    /**
     *
     * @param binaryResults
     * @param idxTargetClass
     * @return
     */
    public static int countInstances(List<SimpleWekaBinaryResult> binaryResults, int idxTargetClass) {
        int noTargets = 0;
        for (SimpleWekaBinaryResult curr: binaryResults) {
            if (curr.getActualIdx() == idxTargetClass)
                noTargets++;
        }

        return noTargets;
    }

    /**
     *
     * @param classAttribute
     * @param className
     * @return
     */
    public static int findTargetClassIdx(Attribute classAttribute, String className) {
        if (classAttribute == null || className == null)
            return SimpleWekaReturnCode.NO_CLASS;

        int idx = classAttribute.indexOfValue(className);

        if (idx == -1)
            return SimpleWekaReturnCode.NO_CLASS;
        else
            return idx;
    }
}
