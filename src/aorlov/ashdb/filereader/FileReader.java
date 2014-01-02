package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Set;

public interface FileReader {

    public Object testRead() throws Exception;

    public XSSFSheet getSheet(String fileName, int sheetNumber) throws Exception ;

    public Dancer constructPerson(Row header, Row row);

    public Set<Club> getClubs() throws Exception;

}