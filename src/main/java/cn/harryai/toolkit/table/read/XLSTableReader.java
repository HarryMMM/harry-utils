package cn.harryai.toolkit.table.read;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Harry
 * @since 2019/8/11 14:30
 */
public class XLSTableReader extends AbstractExcelTableReader {
    public XLSTableReader(InputStream inputStream) throws IOException {
        this.in = inputStream;
        this.wb = new HSSFWorkbook(in);
    }


}
