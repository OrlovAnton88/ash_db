package aorlov.ashdb.filereader;

import aorlov.ashdb.core.*;
import aorlov.ashdb.util.FileName;
import aorlov.ashdb.util.SheetName;
import com.sun.xml.internal.fastinfoset.util.CharArrayString;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReaderImpl implements FileReader {

    private static Logger LOGGER = Logger.getLogger(FileReaderImpl.class);

    public int[][] getScoresMatrix() throws Exception{
        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.SCORES, 0);
        Iterator<Row> rowIterator = sheet.iterator();
        skip(rowIterator,4);
        int numOfRows = sheet.getPhysicalNumberOfRows();
        LOGGER.debug("Phusical num of rows: " + numOfRows);
        int [][] matrix = new int [numOfRows-4][];
        int counter =0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator <Cell> cellIterator = row.iterator();
            skip(cellIterator,1);
            int numOfCells =  row.getPhysicalNumberOfCells();
            matrix[counter] = new int [numOfCells];
            int innerCounter = 0;
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                  Double doubleValue = new Double(cell.getNumericCellValue());
                    int value = doubleValue.intValue();
                    matrix[counter][innerCounter] = value;
//                    LOGGER.debug("counter["+counter+"]inner["+innerCounter+']');
                }
                innerCounter++;
//                innerCounter++;

            }
            counter++;
        }
/**
        for (int i=0; i < matrix.length; i++ ){
                LOGGER.debug("matrix.length: "+matrix.length +"i: " + i);
            for(int x = 0; x < matrix[i].length; x++){
                   LOGGER.debug("matrix[i].length: "+matrix[i].length + "x: "+x) ;
                System.out.print(matrix[i][x] + " ");
            }
            System.out.println();
        }
        **/


        return matrix;
    }

    /**
     * Get dancers score by name and personal code
     *
     * @param dancer
     * @return
     * @throws Exception
     */
    public Collection getDancerHistory(Dancer dancer) throws Exception {
        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_TEST_XLSX, SheetName.RATING);
        Iterator<Row> rowIterator = sheet.iterator();
        boolean startSearch = false;
        int collumnToSearch = -1;

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (!startSearch) {
                Iterator<Cell> cellIterator = row.iterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                        String cellContent = cell.getStringCellValue();
                        if (cellContent.indexOf(Vocabulary.LAST_NAME_RUS) > -1) {
                            startSearch = true;
                            collumnToSearch = cell.getColumnIndex();
                            break;
                        }
                    }
                }
            } else {
                Cell cell = row.getCell(collumnToSearch);
                if (Cell.CELL_TYPE_FORMULA == cell.getCellType()) {
                    String dancerString = cell.getStringCellValue();
                    String name = dancer.getName();
                    String lastName = dancer.getLastName();
                    if (dancerString.indexOf(name) > -1 && dancerString.indexOf(lastName) > -1) {
                        LOGGER.debug("PersonFound: " + dancerString);
                        //iterate row and get scores

                    }
                }
            }

        }
        return new ArrayList();
    }

    @Override
    public Collection<Dancer> getDancers() throws Exception {
        return getDancers(-1);
    }

    @Override
    public Collection<Dancer> getDancers(int maxNumber) throws Exception {
        //to control max munber of persons to return
        int counter = 0;
        List<Dancer> dancers = new ArrayList();
        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_TEST_XLSX, SheetName.DANCERS);
        Iterator<Row> rowIterator = sheet.iterator();
        Row updateDate = rowIterator.next();
        Row header = rowIterator.next();

        int numOfColumns = header.getLastCellNum();
        LOGGER.debug("numberof cells: " + numOfColumns);

        //MAP with numbers of column and it's name
        Map<Integer, String> fieldColumnMap = FileReaderHelper.getColumnToFiledMap(header);

        LOGGER.debug(fieldColumnMap.toString());

        while (rowIterator.hasNext()) {
            //if more than 50 rows one by one are empty - stop
            int stop = 0;
            if ((counter > maxNumber && maxNumber != -1) || stop > 50) {
                break;
            }

            Row row = rowIterator.next();
            Dancer dancer = new Dancer();
            for (int i = 0; i < numOfColumns; i++) {
                String columnName = fieldColumnMap.get(i);
                Cell cell = row.getCell(i);
                if (cell != null) {
                    if (columnName != null && cell.toString().length() > 0) {
                        FileReaderHelper.constructPerson(dancer, columnName, cell);
                    }
                } else {
                    stop++;
                }

            }
            counter++;
            FileReaderHelper.matchAndSetClubId(dancer);
            LOGGER.debug(dancer.toString());
            dancers.add(dancer);
        }


        return dancers;
    }

    @Override
    public Set<Club> getClubs() throws Exception {
        Set<Club> clubs = new HashSet<Club>();

        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_TEST_XLSX, SheetName.CLUB_NAME);
        Iterator<Row> rowIterator = sheet.iterator();
        Row header = rowIterator.next();
        Map<Integer, String> fieldColumnMap = new HashMap<Integer, String>();

        //todo:  int numOfColumns = header.getLastCellNum();

        Integer numOfColumns = FileReaderHelper.doColumnToFieldMatching(header, fieldColumnMap);
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            Club club = new Club();
            for (int i = 0; i < numOfColumns; i++) {
                String columnName = fieldColumnMap.get(i);
                Cell cell = row.getCell(i);
                if (columnName != null && cell.toString().length() > 0) {
                    FileReaderHelper.constructClub(club, columnName, cell);
                }
                clubs.add(club);
            }

        }

        return clubs;
    }


    /**
     * get all events from xls doc
     *
     * @return
     * @throws Exception
     */
    public Collection<Event> getEvents() throws Exception {
        return getEvents(-1);
    }

    /**
     * get all events
     *
     * @param maxNum
     * @return
     * @throws Exception
     */
    public Collection<Event> getEvents(int maxNum) throws Exception {

        Collection<Event> events = new ArrayList<>();

        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_TEST_XLSX, SheetName.RATING);
        Map<String, Integer> rowMap = EventHelper.determineRows(sheet);
        int indexOfNamesRow = rowMap.get(EventHelper.EVENT_NAME_ROW);
        int indexOfDateRow = rowMap.get(EventHelper.EVENT_DATE);
        int eClassNumRow = rowMap.get(EventHelper.EVENT_E_CLASS);
        int dClassNumRow = rowMap.get(EventHelper.EVENT_D_CLASS);
        int cClassNumRow = rowMap.get(EventHelper.EVENT_C_CLASS);
        int bClassNumRow = rowMap.get(EventHelper.EVENT_B_CLASS);
        int aClassNumRow = rowMap.get(EventHelper.EVENT_A_CLASS);

//        int cellNum = rowMap.get(EventHelper.EVENT_NAME_START_CELL);

        Row namesNow = sheet.getRow(indexOfNamesRow);
        Row datesRow = sheet.getRow(indexOfDateRow);
        Row eClassRow = sheet.getRow(eClassNumRow);
        Row dClassRow = sheet.getRow(dClassNumRow);
        Row cClassRow = sheet.getRow(cClassNumRow);
        Row bClassRow = sheet.getRow(bClassNumRow);
        Row aClassRow = sheet.getRow(aClassNumRow);


        boolean start = false;

        int length = namesNow.getLastCellNum();

        if (maxNum != -1) {
            length = maxNum + 13;
        }

        for (int i = 0; i < length; i++) {
            if (start) {
                Event event = new Event(i);
                Cell nameCell = namesNow.getCell(i);
                Cell dateCell = datesRow.getCell(i);
                Cell eClass = eClassRow.getCell(i);
                Cell dClass = dClassRow.getCell(i);
                Cell cClass = cClassRow.getCell(i);
                Cell bClass = bClassRow.getCell(i);
                Cell aClass = aClassRow.getCell(i);
                if (!parseNameCell(event, nameCell)) {
                    continue;
                }

                parseDateCell(event, dateCell);

                parseParticipantsCell(event, eClass, HustleClass.CLASS_E);
                parseParticipantsCell(event, dClass, HustleClass.CLASS_D);
                parseParticipantsCell(event, cClass, HustleClass.CLASS_C);
                parseParticipantsCell(event, bClass, HustleClass.CLASS_B);
                parseParticipantsCell(event, aClass, HustleClass.CLASS_A);
                events.add(event);
            } else {
                Cell cell = eClassRow.getCell(i);
                if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                    String cellValue = cell.getStringCellValue();
//                    Matcher matcher = Pattern.compile(EventHelper.numOfEPairs).matcher(cellValue);

                    if (EventHelper.numOfEPairs.equals(cellValue)) {
                        start = true;
                    }
                }
            }
        }

        return events;
    }


    private void parseParticipantsCell(Event eventIn, Cell cellIn, char classId) {
        int toReturn = 0;

        if (cellIn != null && Cell.CELL_TYPE_STRING == cellIn.getCellType()) {
            String cellValue = cellIn.getStringCellValue();

            Matcher matcher = Pattern.compile(EventHelper.PARTICIPANTS_CELL_PATTERN)
                    .matcher(cellValue);

            if (matcher.find()) {
                String toParse = matcher.group();
                Matcher matcher2 = Pattern.compile("\\d{2}")
                        .matcher(toParse);
                if (matcher2.find()) {
                    String num = matcher2.group();
                    try {
                        toReturn = Integer.valueOf(num);
                    } catch (NumberFormatException ex) {
                        LOGGER.error("Error parsing number: " + ex.getMessage());
                    }
                }

            }
        }
        switch (classId){
            case HustleClass.CLASS_E:
                eventIn.seteClassPairs(toReturn);
                break;
            case HustleClass.CLASS_D:
                eventIn.setdClassPairs(toReturn);
                break;
            case HustleClass.CLASS_C:
                eventIn.setcClassPairs(toReturn);
                break;
            case HustleClass.CLASS_B:
                eventIn.setbClassPairs(toReturn);
                break;
            case HustleClass.CLASS_A:
                eventIn.setaClassPairs(toReturn);
                break;
            default:
                //do nothing;
        }

    }


    /**
     * Get all events from rating sheet
     *
     * @return
     * @throws Exception
     * @deprecated
     */
    public Collection<Event> getEventsOld(int maxNum) throws Exception {
        int counter = 0;
        Collection<Event> events = new ArrayList<>();

        HSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_TEST_XLSX, SheetName.RATING);
        Map<String, Integer> rowMap = EventHelper.determineRows(sheet);
        int indexOfNamesRow = rowMap.get(EventHelper.EVENT_NAME_ROW);
        int indexOfDateRow = rowMap.get(EventHelper.EVENT_DATE);
        int eClassNumRow = rowMap.get(EventHelper.EVENT_E_CLASS);
        int dClassNumRow = rowMap.get(EventHelper.EVENT_D_CLASS);
        int cClassNumRow = rowMap.get(EventHelper.EVENT_C_CLASS);
        int cellNum = rowMap.get(EventHelper.EVENT_NAME_START_CELL);

        Row namesNow = sheet.getRow(indexOfNamesRow);
        Row datesRow = sheet.getRow(indexOfDateRow);
        Row eClassRow = sheet.getRow(eClassNumRow);
        Row dClassRow = sheet.getRow(dClassNumRow);
        Row cClassRow = sheet.getRow(cClassNumRow);

        Iterator<Cell> dateCellIterator = datesRow.iterator();
        Iterator<Cell> nameCellIterator = namesNow.iterator();
        Iterator<Cell> eClassCellIterator = eClassRow.iterator();
        Iterator<Cell> dClassCellIterator = eClassRow.iterator();
        Iterator<Cell> cClassCellIterator = eClassRow.iterator();

        //todo: WTH how it's possible?
        // 7 != 9

        skip(dateCellIterator, 9);
        skip(nameCellIterator, 7);

        while (nameCellIterator.hasNext() && dateCellIterator.hasNext()) {
            Event event = new Event(cellNum);
            Cell nameCell = nameCellIterator.next();
            Cell dateCell = dateCellIterator.next();
            if (!parseNameCell(event, nameCell)) {
                continue;
            }
            if (!parseDateCell(event, dateCell)) {
//                continue;
            }

            events.add(event);
            LOGGER.debug(event.toString());
            cellNum++;
            if (maxNum != -1 && counter > maxNum) {
                break;
            }
        }

        return events;
    }

    private boolean parseDateCell(Event eventIn, Cell dateCell) throws Exception {
        Date resultDate = null;
        if (Cell.CELL_TYPE_NUMERIC == dateCell.getCellType()) {
            String cellToString = dateCell.toString();
//            LOGGER.debug("getDate() - Cell.toString: " + cellToString);
            if (cellToString.indexOf('-') > 0) {
                resultDate = dateCell.getDateCellValue();
            } else if (cellToString.contains("E")) {
                StringBuffer toReturn = new StringBuffer();
                CharSequence charSequence = new CharArrayString(cellToString);
                for (int i = 0; i < charSequence.length(); i++) {
                    char ch = charSequence.charAt(i);
                    if (ch == '.') {
                        continue;
                    } else if (ch == 'E') {
                        break;
                    }
                    toReturn.append(ch);

                }
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                try {
                    resultDate = df.parse(toReturn.toString());
                } catch (ParseException ex) {
                    String error = "Error in parsing double to date";
                    LOGGER.error(error);
//                    throw new Exception(error, ex);
                }
                if (resultDate == null) {
                    Comment comment = dateCell.getCellComment();
                    resultDate = parseCommentToGetDate(comment);
                }
            }
            if (resultDate != null) {
                eventIn.setEventDate(resultDate);
                return true;
            }
        } else {
            LOGGER.error("DateCell is not numeric type.");
        }
        return false;
    }

    private Date getDate(Cell dateCell) {
        String cellToString = dateCell.toString();
        LOGGER.debug("getDate() - Cell.toString: " + cellToString);
        if (cellToString.indexOf('-') > 0) {
            return dateCell.getDateCellValue();
        } else {
            try {
                Comment comment = dateCell.getCellComment();
                return parseCommentToGetDate(comment);

            } catch (Exception ex) {
                LOGGER.error("Error in getting date from cell " + ex.getMessage());
                return null;
            }
        }
    }

    public Date parseCommentToGetDate(Comment comment) {
        String str = comment.getString().toString();
        String pattern1 = "\\d{4}.\\d{2}.\\d{2}";
        String pattern2 = "\\d{2}.\\d{2}.\\d{4}";
        String pattern3 = "\\d{1}.\\d{2}.\\d{4}";
        Map<String, String> patterDateMaskMap = new HashMap();
        patterDateMaskMap.put(pattern1, "yyyy.MM.dd");
        patterDateMaskMap.put(pattern2, "dd.MM.yyyy");
        patterDateMaskMap.put(pattern3, "d.MM.yyyy");
        Set<String> patternSet = patterDateMaskMap.keySet();

        String resultDate = "";
        String resultPattern = "";
        for (String pattern : patternSet) {
            Matcher matcher = Pattern.compile(pattern).matcher(str);
            if (matcher.find()) {
                resultDate = matcher.group();
                resultPattern = pattern;
                LOGGER.info("Date found: " + resultDate);
                break;
            }
        }
        if (resultDate.length() == 0) {
            LOGGER.error("Unable to get date from string: " + str);
        }

        DateFormat df = new SimpleDateFormat(patterDateMaskMap.get(resultPattern));
        Date result = null;
        try {
            result = df.parse(resultDate);
        } catch (ParseException ex) {
            LOGGER.error("Error in parsing date string to Date object " + ex);
        }

        return result;
    }

    private boolean parseNameCell(Event eventIn, Cell nameCell) {
        if (Cell.CELL_TYPE_STRING == nameCell.getCellType()) {
            String eventName = FileReaderHelper.removeLineBreak(nameCell.getStringCellValue());
            if (eventName != null && eventName.length() > 0) {
                eventIn.setFullName(eventName);
                return true;
            }
        }
        return false;
    }

    private void skip(Iterator iterator, int num) {
        for (int counter = 0; counter < num; counter++) {
            iterator.next();
        }
    }

}

