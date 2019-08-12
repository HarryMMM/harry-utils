package cn.harryai.toolkit.io.exception;

/**
 * @author Harry
 * @since 2019/8/11 16:18
 */
public class FileNotExistsException extends Exception {
    public FileNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotExistsException(String message) {
        super(message);
    }
}
