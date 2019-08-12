package cn.harryai.toolkit.table.read;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.harryai.toolkit.table.TableConstants.*;

/**
 * @author Harry
 * @since 2019/8/11 14:07
 * <p>
 * table 的基类。xls xlsx xlsm CVS
 */
public abstract class AbstractTableReader {
    protected File file;


    public static AbstractTableReader getTableReader(String path) throws IOException {
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        switch (FilenameUtils.getExtension(path)) {
            case TABLE_TYPE_XLS:
                return new XLSTableReader(fileInputStream);
            case TABLE_TYPE_CSV:
                return new CVSTableReader(file);
            case TABLE_TYPE_XLSX:
                return new XLSXTableReader(fileInputStream);
            default:
                return new UnsupportedTableReader();
        }
    }

    protected boolean isCheckHead() {
        return false;
    }

    public List<List<String>> read(String sheetName) {
        List<List<String>> res = new ArrayList<>();
        List<String> head = readHead(sheetName);
        if (isCheckHead()) {
            checkHead(head);
        }
        List<List<String>> content = readContent(sheetName);
        res.add(head);
        res.addAll(content);
        return res;
    }

    public List<List<String>> read(int sheetIndex) {
        List<List<String>> res = new ArrayList<>();
        List<String> head = readHead(sheetIndex);
        if (isCheckHead()) {
            checkHead(head);
        }
        List<List<String>> content = readContent(sheetIndex);
        res.add(head);
        res.addAll(content);
        return res;
    }

    protected boolean checkHead(List<String> head) {

        return true;
    }

    protected abstract List<String> readHead(String sheetName);

    protected abstract List<String> readHead(int sheetIndex);

    protected abstract List<List<String>> readContent(String sheetName);

    protected abstract List<List<String>> readContent(int sheetIndex);


}
