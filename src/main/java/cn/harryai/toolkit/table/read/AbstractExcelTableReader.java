package cn.harryai.toolkit.table.read;

import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Harry
 * @since 2019/8/11 16:24
 */
abstract class AbstractExcelTableReader extends AbstractTableReader {
    protected InputStream in;
    protected Workbook wb;

    protected List<Sheet> getSheets(Workbook wb) {
        List<Sheet> sheets = new ArrayList<>();
        int numberOfSheets = wb.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(wb.getSheetAt(i));
        }
        return sheets;
    }

    @Override
    protected List<String> readHead(String sheetName) {


        return readSheetHead(wb.getSheet(sheetName));
    }


    @Override
    protected List<String> readHead(int sheetIndex) {
        return readSheetHead(wb.getSheetAt(sheetIndex));
    }


    @Override
    protected List<List<String>> readContent(String sheetName) {
        Sheet sheet = wb.getSheet(sheetName);
        return readSheetContent(sheet);
    }


    @Override
    protected List<List<String>> readContent(int sheetIndex) {

        Sheet sheet = wb.getSheetAt(sheetIndex);
        return readSheetContent(sheet);
    }


    private List<String> readSheetHead(Sheet sheet) {
        List<String> res = new ArrayList<>();
        Row row = sheet.getRow(0);
        return readRow(row);
    }


    private List<List<String>> readSheetContent(Sheet sheet) {
        List<List<String>> res = new ArrayList<>();
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            List<String> cellsOfRow = readRow(row);
            res.add(cellsOfRow);
        }
        return res;
    }

    private List<String> readRow(Row row) {
        int cellNub = row.getLastCellNum() - row.getFirstCellNum();
        List<String> cellsOfRow = new ArrayList<>();
        for (int j = 0; j < cellNub; j++) {
            Cell cell = row.getCell(j);
            cell.setCellType(CellType.STRING);
            cellsOfRow.add(cell.getStringCellValue());
        }
        return cellsOfRow;
    }

}
