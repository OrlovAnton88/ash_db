package aorlov.ashdb.filereader.club;

import aorlov.ashdb.core.Club;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ClubHelperTest extends TestCase {
    public void testDecodeClubName() throws Exception {

       String input = "Sole Dance (г.Омск)";
        Club club  = new Club();

        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("Sample sheet");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(input);

        ClubHelper.decodeClubName(club,cell);
        assertEquals("Sole Dance",club.getName());
        assertEquals("Омск",club.getCity());


    }
}
