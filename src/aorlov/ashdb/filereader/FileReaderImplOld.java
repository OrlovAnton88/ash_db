package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.util.FileName;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FileReaderImplOld implements FileReader {

    private static Logger LOGGER = Logger.getLogger(FileReaderImplOld.class);

    private Map<String, Integer> coulumnMatching = new HashMap();



    public Set<Club> getClubs() throws Exception {
      return null;
    }

    public List<Dancer> getDancers() throws Exception{
        return null;

    }


    public List<Dancer> getDancers(int maxNumber) throws Exception{

        List<Dancer> personsList = new ArrayList();

        //Get first sheet from the workbook
        XSSFSheet sheet = getSheet(FileName.ASH_XLSX, 0);;

        //Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();

        Row header = rowIterator.next();

        Map<Integer, String> fieldColumnMap = new HashMap<Integer, String>();

        Integer numOfColumns = FileReaderHelper.doColumnToFieldMatching(header, fieldColumnMap);

        LOGGER.debug("OLD numberof cells: " + numOfColumns);

//        while (rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//
//            Dancer dancer = new Dancer();
//            for (int i = 0; i < numOfColumns; i++) {
//                String columnName = fieldColumnMap.get(i);
//                Cell cell = row.getCell(i);
//                if (columnName != null && cell.toString().length() > 0) {
//                    FileReaderHelper.constructPerson(dancer, columnName, cell);
//                }
//
//            }
//
//            personsList.add(dancer);
//        }

        return personsList;

    }




    public Dancer constructPerson(Row header, Row row) {
        Dancer dancer = new Dancer();
        int numberOfCells = header.getPhysicalNumberOfCells();

        for (int i = 0; i < numberOfCells; i++) {
            System.out.println("in");
            String value = row.getCell(i).toString();
            System.out.println("value: " + value);
//            dancer.setPersonalCode(N.valueOf(value));

        }
        return dancer;
    }

    private FileInputStream getInputStream(String fileName) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException ex) {
            String error = "File " + fileName + "not found";
            LOGGER.error(error);
            throw new Exception("File " + fileName + "not found");
        }
        return fileInputStream;
    }

    public XSSFSheet getSheet(String fileName, int sheetNumber) throws Exception {

        FileInputStream fileInputStream = getInputStream(fileName);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException ex) {
            throw new Exception("Error in creating work book", ex);
        }
        XSSFSheet sheet = workbook.getSheetAt(sheetNumber);
        return sheet;
    }
}