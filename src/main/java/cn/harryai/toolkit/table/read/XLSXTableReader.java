package cn.harryai.toolkit.table.read;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Harry
 * @since 2019/8/11 14:30
 */
public class XLSXTableReader extends AbstractExcelTableReader {
    public XLSXTableReader(InputStream inputStream) throws IOException {
        this.in = inputStream;
        this.wb = new XSSFWorkbook(in);
    }


}
