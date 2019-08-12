package cn.harryai.toolkit.table.read;

import java.util.Collections;
import java.util.List;

/**
 * @author Harry
 * @since 2019/8/11 14:30
 */
public class UnsupportedTableReader extends AbstractTableReader {
    @Override
    protected List<String> readHead(String sheetName) {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected List<String> readHead(int sheetIndex) {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected List<List<String>> readContent(String sheetName) {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected List<List<String>> readContent(int sheetIndex) {
        return Collections.EMPTY_LIST;
    }
}
