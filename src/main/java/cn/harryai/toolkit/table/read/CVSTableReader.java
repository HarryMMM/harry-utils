package cn.harryai.toolkit.table.read;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cn.harryai.toolkit.character.StringUtils.COMMA;

/**
 * @author Harry
 * @since 2019/8/11 14:30
 */
public class CVSTableReader extends AbstractTableReader {
    private List<String> content;

    public CVSTableReader(File file) throws IOException {
        this.file = file;
        content = FileUtils.readLines(file);
    }

    @Override
    protected List<String> readHead(String sheetName) {
        return Arrays.asList(StringUtils.split(content.get(0), COMMA));
    }

    @Override
    protected List<String> readHead(int sheetIndex) {
        return readHead(null);
    }

    @Override
    protected List<List<String>> readContent(String sheetName) {
        List<List<String>> res = new ArrayList<>();
        for (int i = 1; i < content.size(); i++) {
            res.add(Arrays.asList(StringUtils.split(content.get(i), COMMA)));
        }
        return res;
    }

    @Override
    protected List<List<String>> readContent(int sheetIndex) {
        return readContent(null);
    }


}
