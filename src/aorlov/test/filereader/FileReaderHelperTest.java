package aorlov.test.filereader;

import aorlov.ashdb.core.Dancer;
import junit.framework.TestCase;

public class FileReaderHelperTest extends TestCase {

    public void testDecodePersonalCode() throws Exception {
        String value1 = "1.0";
//        Cell cell = new XSSFCell();
//
//        //   protected XSSFCell(org.apache.poi.xssf.usermodel.XSSFRow row, org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCell cell)
//
//        Integer result = FileReaderHelper.decodePersonalCode(value1);
//        assertEquals(result, new Integer(1));

        Dancer dancer = new Dancer();
        dancer.setPersonalCode(123);
        System.out.println(dancer.getPersonalCodeString());
    }



}
