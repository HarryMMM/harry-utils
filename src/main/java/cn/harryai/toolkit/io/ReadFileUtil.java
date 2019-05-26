package cn.harryai.toolkit.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFileUtil extends BaseIO {

    /**
     * 读取txt文件，返回字符串
     *
     * @param file 文件对象
     * @return 文件内容
     * @throws IOException
     */
    public static String readText(File file) throws IOException {
        if (file == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        try (BufferedReader bufferReader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = bufferReader.readLine()) != null) {
                sb.append(str);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        }
        return sb.toString();
    }

    /**
     * 读取txt文件，返回字符串集合 需指定分割字符串规则
     *
     * @param file
     * @param splitRegex
     * @return
     * @throws IOException
     */
    public static List<String> readTextToList(File file, String splitRegex) throws IOException {
        String[] charArray = readText(file).split(splitRegex);
        List<String> charList = new ArrayList<String>();
        for (String string : charArray) {
            charList.add(string);
        }

        return charList;
    }

    /**
     * 读取txt文件，返回字符串
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static String readText(String path) throws IOException {
        File file = new File(path);
        return readText(file);

    }


    /**
     * 读取txt文件，返回字符串list集合
     *
     * @param path 文件路径
     * @return 文件内容
     * @throws IOException
     */
    public static List<String> readText(String path, String splitRegex) throws IOException {
        File file = new File(path);
        return readTextToList(file, splitRegex);

    }


    /**
     * 获得文件内容的总行数
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static int getFileContentLineNum(File file) throws IOException {

        LineNumberReader lr = null;
        try {
            lr = new LineNumberReader(new FileReader(file));
            lr.skip(Long.MAX_VALUE);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            lr.close();
        }
        return lr.getLineNumber() + 1;

    }
}
