package cn.harryai.toolkit.yaml;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static cn.harryai.toolkit.character.StringUtils.DOT;

/**
 * @author Harry
 * @since 2019/8/12 15:06
 */
public final class YamlUtils {
    private static final Yaml yaml = new Yaml();

    /**
     * 加载yaml
     *
     * @param yamlFilePath
     * @param clazz
     * @param <T>
     * @return
     * @throws FileNotFoundException
     */
    public static <T> T loadYaml(String yamlFilePath, Class<T> clazz) throws FileNotFoundException {
        File file = new File(yamlFilePath);
        return yaml.loadAs(new FileInputStream(file), clazz);
    }

    /**
     * 加载yaml
     *
     * @param in
     * @param clazz
     * @param <T>
     * @return
     * @throws FileNotFoundException
     */
    public static <T> T loadYaml(InputStream in, Class<T> clazz) {
        return yaml.loadAs(in, clazz);
    }

    /**
     * 加载yaml
     *
     * @param reader
     * @param clazz
     * @param <T>
     * @return
     * @throws FileNotFoundException
     */
    public static <T> T loadYaml(Reader reader, Class<T> clazz) {
        return yaml.loadAs(reader, clazz);
    }


    /**
     * 转换yaml 为 properties格式
     * aaa:
     * bbb: ccc
     * -> aaa.bbb=ccc
     *
     * @param yamlFilePath
     * @return
     * @throws FileNotFoundException
     */
    public static Map<String, Object> loadProperties(String yamlFilePath) throws FileNotFoundException {
        return loadProperties(new FileInputStream(yamlFilePath));
    }


    /**
     * 转换 map格式 的yaml 为 properties格式
     * aaa:
     * bbb: ccc
     * -> aaa.bbb=ccc
     *
     * @param in
     * @return
     * @throws FileNotFoundException
     */
    public static Map<String, Object> loadProperties(InputStream in) throws FileNotFoundException {
        return loadProperties(new UnicodeReader(in));
    }

    /**
     * 转换 map格式 yaml 为 properties格式
     * aaa:
     * bbb: ccc
     * -> aaa.bbb=ccc
     *
     * @param reader
     * @return
     * @throws FileNotFoundException
     */
    public static Map<String, Object> loadProperties(Reader reader) throws FileNotFoundException {
        Map<String, Object> properties = loadYaml(reader, Map.class);
        Map<String, Object> res = new LinkedHashMap<>(9);
        parse(StringUtils.EMPTY, properties, res);
        return res;
    }

    /**
     * 写yaml 按map的格式
     *
     * @param obj
     * @param path
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeYamlAsMap(T obj, String path, String encoding) throws IOException {
        writeYamlAsMap(obj, new File(path), encoding);
    }

    /**
     * 写yaml 按map的格式
     *
     * @param obj
     * @param path
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeYamlAsMap(T obj, File file, String encoding) throws IOException {
        String dump = yaml.dumpAsMap(obj);
        FileUtils.write(file, dump, encoding);
    }

    /**
     * 写入文件会带文件头
     * 比如：dump User 那么文件头为！！User
     *
     * @param <T>
     * @param obj
     * @param path
     * @throws IOException
     */
    public static <T> void writeYamlAsSerialization(T obj, String path, String encoding) throws IOException {
        writeYamlAsSerialization(obj, new File(path), encoding);
    }

    /**
     * 写入文件会带文件头
     * 比如：dump User 那么文件头为！！User
     *
     * @param obj
     * @param path
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeYamlAsSerialization(T obj, File file, String encoding) throws IOException {
        String dump = yaml.dump(obj);
        FileUtils.write(file, dump, encoding);
    }


    private static void parse(String previousKey, Map<String, Object> properties, Map<String, Object> res) {
        properties.forEach((key, value) -> {
            final String previousKeyPass = StringUtils.isEmpty(previousKey) ? key : String.format("%s%s%s", previousKey, DOT, key);
            if (value instanceof Map) {
                parse(previousKeyPass, (Map) value, res);
            } else {

                res.put(previousKeyPass, value);
            }
        });
    }


}
