package aorlov.test.filereader;

import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.filereader.FileReaderHelper;
import junit.framework.TestCase;

public class FileReaderHelperTest extends TestCase {

    public void testDecodePersonalCode() throws Exception {

        Dancer dancer = new Dancer();
        dancer.setPersonalCode(123);
        assertEquals("00123", dancer.getPersonalCodeString());
    }

    public void testRemoveLineBreak() throws Exception{
        String tmp ="Текущ.  класс\n";
        FileReaderHelper fh = new FileReaderHelper();
        assertEquals("Текущ. класс", fh.removeLineBreak(tmp));
    }



}
