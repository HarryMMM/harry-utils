package cn.harryai.toolkit.io;


import cn.harryai.toolkit.collection.ListUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 从文档中提取文本处理类
 *
 * @author Harry
 */
public class FileHandling extends BaseIO {


    // 提取英文常量
    public final static String REGEX_ENGLISH = "[^a-zA-Z]+";
    // 英语词性
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
        String originText = ReadFileUtil.readText(path);
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

        return ListUtil.convertToString(extractEnglishCharacterList(path));
    }

    /**
     * 提取英文文本集合
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static List<String> extractEnglishCharacterList(String path) throws IOException {
        List<String> charList = extractCharacterList(path, FileHandling.REGEX_ENGLISH);
        //去掉词性
        charList.removeAll(Arrays.asList(properties));
        //删除长度为1的单词
        return ListUtil.delectStrLengthOf(charList, 1);
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
            WriteFileUtil.WriteToText(content, toPath);
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
        BufferedReader buffReader = null;
        BufferedWriter bufferedWriter = null;

        try {
            File originFile = new File(fromFilePath);
            int fileContentLineNum = ReadFileUtil.getFileContentLineNum(originFile);
            // 计算每个文本多少行
            int fileLineNum = fileContentLineNum % fileCount == 0 ? fileContentLineNum / fileCount
                    : fileContentLineNum / fileCount + 1;

            buffReader = new BufferedReader(new FileReader(originFile));

            String str;
            for (int i = 1; i <= fileCount; i++) {
                String fileName = toDirectoryPath
                        + originFile.getName().substring(0, originFile.getName().lastIndexOf(".")) + i + ".txt";
                bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                for (int j = 1; j <= fileLineNum; j++) {
                    if ((str = buffReader.readLine()) != null) {
                        bufferedWriter.write(str + "\n");
                    }

                }
                bufferedWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(buffReader);
        }

        return true;

    }


    /**
     * 将一份txt文件拆分为多个txt文件,使用
     * 此方法源文件可以午无换行
     *
     * @param fromFilePath    源文件路径
     * @param splitRegex      源文件词语分隔符
     * @param toDirectoryPath 存放新文件的目录
     * @param fileCount       要拆分为多少个
     * @return
     */
    public static boolean splitTxtFileContentNoLinefeed(String fromFilePath, String splitRegex, String toDirectoryPath, int fileCount) {

        BufferedWriter buffwriter = null;

        try {
            File originFile = new File(fromFilePath);
            List<String> charList = ReadFileUtil.readTextToList(originFile, splitRegex);

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

}
