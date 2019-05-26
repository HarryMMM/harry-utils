package cn.harryai.toolkit.io;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * io工具基础类
 *
 * @author Harry
 */
public class BaseIO {
    private  static  final Logger LOGGER= LoggerFactory.getLogger(BaseIO.class);

    protected static void closeAll(Closeable... closeables) {
        if (null == closeables || closeables.length == 0) {
            return;
        }
        for (Closeable closeable : closeables) {
            close(closeable);
        }
    }



    protected static void close(Closeable closeable)  {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOGGER.error("An error occurred while closing {} ,cause: {} ",closeable.getClass().getSimpleName(),e);
            }
        }
    }


}
