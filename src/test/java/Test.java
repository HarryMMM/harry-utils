import cn.harryai.toolkit.table.read.AbstractTableReader;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        AbstractTableReader tableReader = AbstractTableReader.getTableReader("C:\\Users\\Harry\\Desktop\\abc.csv");
        System.out.println(tableReader.read(0));

    }

}
