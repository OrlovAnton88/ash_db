package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Vocabulary;
import aorlov.ashdb.filereader.club.ClubHelper;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class FileReaderHelper {
    private static Logger LOGGER = Logger.getLogger(FileReaderHelper.class);


    private static void columnToFieldMatcher(Map<Integer, String> map, String columnNameIn, int columnNumberIn) {
        Map<String, String> vocabulary = Vocabulary.getInstance().getVocabulary();
        String field = vocabulary.get(columnNameIn);
        if (field != null) {
            if (!map.containsValue(field)) {
                map.put(new Integer(columnNumberIn), field);
            }
        }
    }

    public static void constructClub(Club clubIn,String fieldName, Cell cellIn ) throws Exception{
        switch (fieldName) {
            case "club":
                ClubHelper.decodeClubName(clubIn,cellIn);
                break;
            default:
                LOGGER.error("Unable to decode parameter [" + cellIn.toString() + "]");
    }
    }
    public static void constructPerson(Dancer dancerIn, String fieldName, Cell cellIn) {
        switch (fieldName) {
            case "personalcode":
                dancerIn.setPersonalCode(decodePersonalCode(cellIn));
                break;
            case "gender":
                dancerIn.setGender(decodeGender(cellIn));
                break;
            case "surnameandname":
                decodeNameAndSurname(dancerIn, cellIn.toString());
                break;
            case "currentclass":
                dancerIn.setCurrentClass(decodeDanserClass(cellIn));
                break;
            case "club":
                dancerIn.setClub(cellIn.toString());
                break;
            case "familyname":
                dancerIn.setFamilyName(cellIn.toString());
                break;
            case "registrationdate":
                dancerIn.setRegistrationDate(decodeRegDate(cellIn));
                break;
            default:
                LOGGER.error("Unable to decode parameter [" + cellIn.toString() + "]");
        }
    }

    private static Date decodeRegDate(Cell cellIn) {
         if(cellIn.getCellType() == Cell.CELL_TYPE_NUMERIC)  {
           return cellIn.getDateCellValue();
         }
        return new Date();
    }

    private static char decodeDanserClass(Cell cellIn) {
        String parameterIn = cellIn.toString();
        char danseClass = parameterIn.trim().charAt(0);
        return danseClass;
    }

    private static void decodeNameAndSurname(Dancer dancerIn, String parameterIn) {
        String[] arr = parameterIn.split(" ");
        if (arr.length == 2) {
            String lastName = arr[0];
            String name = arr[1];
            dancerIn.setName(name.trim());
            dancerIn.setLastName(lastName.trim());
        }

    }

    private static char decodeGender(Cell cellIn) {
        String genderIn = cellIn.toString();
        char gender = genderIn.charAt(0);
        if ('м' == gender || 'М' == gender) {
            return Dancer.MALE;
        } else if ('ж' == gender || 'Ж' == gender) {
            return Dancer.FEMALE;
        } else {
            return 'Z';
        }
    }

    public static Integer decodePersonalCode(Cell cellIn) {
        int toReturn = 0;
        if (cellIn.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            Double value = cellIn.getNumericCellValue();
            toReturn = value.intValue();
        }
        return toReturn;

    }

    public static String removeLineBreak(String valueIn){
        String toReturn = valueIn;
        if(valueIn.contains("\n")){
            toReturn = valueIn.replace("\n", " ");
        }
        return toReturn;
    }

    public static Row getHeaderRow(XSSFSheet sheetIn){
        XSSFSheet sheet = sheetIn;
        Iterator<Row> rowIterator = sheet.iterator();
        Row header = rowIterator.next();
        return header;
    }

    /**
     *
     * @param headerIn
     * @param fieldColumnMap
     * @return    number of columns
     * @deprecated
     */
    public static Integer doColumnToFieldMatching(Row headerIn, Map<Integer, String> fieldColumnMap){
        Iterator<Cell> headerСellIterator = headerIn.cellIterator();
        int counter = 0;
        while (headerСellIterator.hasNext()) {
            Cell cell = headerСellIterator.next();
            String value = decodeHeaderValue(cell);
            columnToFieldMatcher(fieldColumnMap, value, counter);
            counter++;
        }
        return counter;
    }

    public static Map <Integer, String> getColumnToFiledMap(Row headerIn){
        Map<Integer, String> columnFieldMap = new HashMap<Integer, String>();
        Iterator<Cell> headerСellIterator = headerIn.cellIterator();
        int counter = 0;
        while (headerСellIterator.hasNext()) {
            Cell cell = headerСellIterator.next();
            String value = decodeHeaderValue(cell);
            columnToFieldMatcher(columnFieldMap, value, counter);
            counter++;
        }

    return columnFieldMap;
    }

    private static String decodeHeaderValue(Cell cellIn) {
        String value;
        int cellType = cellIn.getCellType();
        if (cellType == Cell.CELL_TYPE_STRING) {
            value = FileReaderHelper.removeLineBreak(cellIn.getStringCellValue().toLowerCase());

        } else {
            value = cellIn.toString().trim().toLowerCase();
        }
        return value;
    }


    /**
     * Get Excel Sheet by name
     * @param fileName
     * @param sheetName
     * @return
     * @throws Exception
     */
    public static XSSFSheet getSheet(String fileName, String sheetName) throws Exception {

        FileInputStream fileInputStream = getInputStream(fileName);
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(fileInputStream);
        } catch (IOException ex) {
            throw new Exception("Error in creating work book", ex);
        }
        int index = workbook.getSheetIndex(sheetName);
        LOGGER.debug("Sheet index: [" + index +']');

        XSSFSheet sheet = workbook.getSheetAt(index);
        return sheet;
    }


    public static XSSFSheet getSheet(String fileName, int sheetNumber) throws Exception {

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

    private static FileInputStream getInputStream(String fileName) throws Exception {
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
}
