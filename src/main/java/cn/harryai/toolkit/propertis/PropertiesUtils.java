package cn.harryai.toolkit.propertis;


import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Harry
 * @since 2019/8/12 17:45
 */
public final class PropertiesUtils {
    private Properties properties = new Properties();

    private PropertiesUtils() {
    }

    public static PropertiesUtils load(InputStream in) throws IOException {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        propertiesUtils.properties.load(in);
        return propertiesUtils;
    }

    public static PropertiesUtils load(String path) throws IOException {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        propertiesUtils.properties.load(new FileInputStream(path));
        return propertiesUtils;
    }


    public static PropertiesUtils load(Reader reader) throws IOException {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        propertiesUtils.properties.load(reader);
        return propertiesUtils;
    }


    public static PropertiesUtils loadFromXML(InputStream in) throws IOException {
        PropertiesUtils propertiesUtils = new PropertiesUtils();
        propertiesUtils.properties.loadFromXML(in);
        return propertiesUtils;
    }

    public static PropertiesUtils loadFromXML(String path) throws IOException {
        return loadFromXML(new FileInputStream(path));
    }

    public <T> T get(Object key, T defaultValue) {
        return (T) properties.get(key);
    }

    public <T> T getOrDefault(Object key, T defaultValue) {
        return (T) properties.getOrDefault(key, defaultValue);

    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }

    public String getProperties(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }


    public void write(OutputStream out, String comments) throws IOException {
        properties.store(out, comments);
    }

    public void write(String path, String comments) throws IOException {
        properties.store(new FileOutputStream(path), comments);
    }

    public void write(Writer writer, String comments) throws IOException {
        properties.store(writer, comments);
    }

    public void writeToXML(OutputStream out, String comments) throws IOException {
        properties.storeToXML(out, comments);
    }

    public void writeToXML(OutputStream out, String comments, String encoding) throws IOException {
        properties.storeToXML(out, comments, encoding);
    }

    public void writeToXML(String path, String comments, String encoding) throws IOException {
        properties.storeToXML(new FileOutputStream(path), comments, encoding);
    }

    public void setProperties(String key, String value) {
        properties.setProperty(key, value);
    }

    public Map<Object, Object> getAllProperties() {
        return new HashMap<>(properties);
    }

}
