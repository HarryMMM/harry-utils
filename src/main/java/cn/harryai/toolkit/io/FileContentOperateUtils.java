package cn.harryai.toolkit.io;


import cn.harryai.toolkit.collection.ListUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 从文档中提取文本处理类
 *
 * @author Harry
 */
public class FileContentOperateUtils extends BaseIO {


    /**
     * 英文
     */
    public final static String REGEX_ENGLISH = "[a-zA-Z]+";
    /**
     * 非英文
     */
    public final static String REGEX_NOT_ENGLISH = "[^a-zA-Z]+";
    /**
     * 中文
     */
    public final static String REGEX_CHINESE = "[\\u4e00-\\u9fa5]";
    /**
     * 非中文
     */
    public final static String REGEX_NOT_CHINESE = "[^\\u4e00-\\u9fa5]";
    /**
     * 双字节字符（包含中文）
     */
    public final static String REGEX_SINGLE_BYTE_CHARACTERS = "[^\\x00-\\xff]";

    /**
     * 单字节字符
     */
    public final static String REGEX_DOUBLE_BYTE_CHARACTERS = "[\\x00-\\xff]";

    /**
     * 英文词性
     */
    public final static String[] properties = {"prep", "pron", "n", "v", "conj", "s", "sc", "o", "oc", "vi", "vt",
            "aux", "adj", "adv", "art", "num", "int", "u", "c", "pl"};

    /**
     * 提取文件中的指定字符串
     *
     * @param path            文件地址
     * @param replaceStrRegex 不需要的文本正则
     * @return 需要的字符串集合
     * @throws IOException
     */
    public static List<String> extractCharacterList(String path, String replaceStrRegex) throws IOException {
        List<String> charList = new ArrayList<String>();
        String text = extractCharacterString(path, replaceStrRegex);

        String[] chars = text.split(" ");
        for (String str : chars) {
            charList.add(str);
        }

        return charList;

    }

    /**
     * 提取文件中的指定字符串集合
     *
     * @param path            文件地址
     * @param replaceStrRegex 不需要的文本正则
     * @return 需要的字符串
     * @throws IOException
     */

    public static String extractCharacterString(String path, String replaceStrRegex) throws IOException {

        // 读取原始文本
        String originText = readText(path);
        // 将非英文的文本替换成空格
        String newText = originText.replaceAll(replaceStrRegex, " ");

        return newText;
    }

    /**
     * 提取英文文本字符串
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String extractEnglishCharacterString(String path) throws IOException {

        return ListUtils.convertToString(extractEnglishCharacterList(path));
    }

    /**
     * 提取英文文本集合
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> extractEnglishCharacterList(String path) throws IOException {
        List<String> charList = extractCharacterList(path, FileContentOperateUtils.REGEX_NOT_ENGLISH);
        //去掉词性
        charList.removeAll(Arrays.asList(properties));
        //删除长度为1的单词
        return ListUtils.delectStrLengthOf(charList, 1);
    }

    /**
     * 提取一个文本中的英文单词到领一个文本中
     *
     * @param fromPath
     * @param toPath
     * @return
     */
    public static boolean extractEnglishToAnotherFile(String fromPath, String toPath) {

        try {
            String content = extractEnglishCharacterString(fromPath);
            WriteToText(content, toPath);
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 将一份txt文件拆分为多个txt文件,使用
     * 此方法源文件必须有换行
     *
     * @param fromFilePath    源文件路径
     * @param toDirectoryPath 存放新文件的目录
     * @param fileCount       要拆分为多少个
     * @return
     */
    public static boolean splitTxtFileContentHasLinefeed(String fromFilePath, String toDirectoryPath, int fileCount) {
        File originFile = new File(fromFilePath);
        try (BufferedReader buffReader = new BufferedReader(new FileReader(originFile))) {
            int fileContentLineNum = getFileContentLineNum(originFile);
            // 计算每个文本多少行
            int fileLineNum = fileContentLineNum % fileCount == 0 ? fileContentLineNum / fileCount
                    : fileContentLineNum / fileCount + 1;


            String str;
            for (int i = 1; i <= fileCount; i++) {
                String fileName = toDirectoryPath
                        + originFile.getName().substring(0, originFile.getName().lastIndexOf(".")) + i + ".txt";
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
                    for (int j = 1; j <= fileLineNum; j++) {
                        if ((str = buffReader.readLine()) != null) {
                            bufferedWriter.write(str + "\n");
                        }

                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }


    /**
     * 将一份txt文件拆分为多个txt文件,使用
     * 此方法源文件可以无换行
     *
     * @param fromFilePath    源文件路径
     * @param splitRegex      源文件词语分隔符
     * @param toDirectoryPath 存放新文件的目录
     * @param fileCount       要拆分为多少个
     * @return 返回操作结果
     */
    public static boolean splitTxtFileContentNoLinefeed(String fromFilePath, String splitRegex, String
            toDirectoryPath, int fileCount) {

        BufferedWriter buffwriter = null;

        try {
            File originFile = new File(fromFilePath);
            List<String> charList = readTextToList(originFile, splitRegex);

            int wordCount = charList.size();
            // 计算每个文本多少个单词
            int fileLineNum = wordCount % fileCount == 0 ? wordCount / fileCount
                    : wordCount / fileCount + 1;

            int index = 0;
            for (int i = 1; i <= fileCount; i++) {
                String fileName = toDirectoryPath
                        + originFile.getName().substring(0, originFile.getName().lastIndexOf(".")) + i + ".txt";
                buffwriter = new BufferedWriter(new FileWriter(fileName));

                for (int j = 1; j <= fileLineNum; j++) {
                    if (index < wordCount) {
                        buffwriter.write(charList.get(index++) + " ");
                    }

                }
                buffwriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 向文本中写入内容
     *
     * @param content 要写入的内容
     * @param file    目标文件
     * @throws IOException If an I/O error occurs
     */
    public static void writeToText(String content, File file) throws IOException {
        if (file == null)
            return;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        }

    }

    /**
     * 向文本中写入内容
     *
     * @param content    要写入的内容
     * @param targetPath 目标文件路径
     * @throws IOException If an I/O error occurs
     */
    public static void WriteToText(String content, String targetPath) throws IOException {
        File file = new File(targetPath);
        writeToText(content, file);
    }


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
