package aorlov.ashdb.filereader.club;

import aorlov.ashdb.filereader.FileReader;
import aorlov.ashdb.filereader.FileReaderHelper;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.util.FileName;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mouse
 * Date: 23.12.13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class ParseClubs {

    public void doSmht() throws Exception {
        FileReader reader = new FileReaderImpl();
        XSSFSheet sheet = reader.getSheet(FileName.ASH_XLSX, 1);

        Row header = FileReaderHelper.getHeaderRow(sheet);
        Map fieldColumnMap = new HashMap<Integer, String>();

        Integer numOfCoulumns = FileReaderHelper.matchColumnToFieldMatcher(header,fieldColumnMap);

    }



}
