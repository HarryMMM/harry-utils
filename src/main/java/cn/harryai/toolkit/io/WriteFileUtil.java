package cn.harryai.toolkit.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 向文本写入类
 *
 * @author Harry
 */
public class WriteFileUtil extends BaseIO {

    /**
     * 向文本中写入内容
     *
     * @param content
     * @param file
     * @throws IOException
     */
    public static void writeToText(String content, File file) throws IOException {
        if (file == null)
            return;

        try( BufferedWriter bw=new BufferedWriter(new FileWriter(file))){
            bw.write(content);
        }

    }

    /**
     * 向文本中写入内容
     *
     * @param content
     * @param path    写入文本的路径
     * @throws IOException
     */
    public static void WriteToText(String content, String path) throws IOException {
        File file = new File(path);
        writeToText(content, file);
    }

}
