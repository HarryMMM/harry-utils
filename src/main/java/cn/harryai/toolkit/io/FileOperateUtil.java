package cn.harryai.toolkit.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.Objects;

/**
 * 文件操作类，用来操作文件。
 *
 * @author Harry
 */
public class FileOperateUtil {

    /**
     * 文件下载
     *
     * @param response     http response(tomcat-servelet-api)
     * @param downloadPath 文件在服务器的存储路径
     * @param fileName     要下载的文件名
     * @throws IOException if an I/O error occurs
     */
    public static void download(HttpServletResponse response, String downloadPath, String fileName) throws IOException {
        FileUtils.forceMkdir(new File(downloadPath));
        try (FileInputStream fileInputStream = new FileInputStream(new File(concatPath(downloadPath, fileName)));
             ServletOutputStream outputStream = response.getOutputStream()) {
            String extension = FilenameUtils.getName(fileName);
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", String.format("attachment;fileName=%s", extension));
            transfer(fileInputStream, outputStream);
        }

    }

    /**
     * 文件上传
     *
     * @param request    http request （tomcat-servlet-api）
     * @param uploadPath 要上传的文件路径
     * @param partName   form表单中<input type="file" name="file">的name属性的值
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if the request is not multipart/form-data
     */
    public static void upload(HttpServletRequest request, String uploadPath, String partName) throws IOException, ServletException {
        FileUtils.forceMkdir(new File(uploadPath));
        // 根据partName获取上传的文件Part
        Part part = request.getPart(partName);
        // 获取原文件名
        String header = part.getHeader("content-disposition");
        String fileName = header.substring(header.indexOf("filename=") + 10, header.length() - 1);
        // 将文件流写入指定目录
        try (OutputStream outputStream = new FileOutputStream(concatPath(uploadPath, fileName))) {
            transfer(part.getInputStream(), outputStream);
        }
    }

    /**
     * 拼接路径
     *
     * @param paths 要拼接的路径
     * @return
     */
    public static String concatPath(String... paths) {
        if (Objects.isNull(paths) || paths.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int len = paths.length;
        for (int i = 0; i < len; i++) {
            String path = paths[i];
            builder.append(path);
            if (i == len - 1) break;
            builder.append(File.separator);
        }
        return builder.toString();
    }

    public static void transfer(InputStream in, OutputStream out) throws IOException {
        if (null == in) throw new NullPointerException("InputStream is null~");
        if (null == out) throw new NullPointerException("OutputStream is null~");
        int len;
        byte[] buffer = new byte[1024];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }


}
