package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.util.FileName;
import aorlov.ashdb.util.SheetName;
import com.sun.xml.internal.fastinfoset.util.CharArrayString;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileReaderImpl implements FileReader {

    private static Logger LOGGER = Logger.getLogger(FileReaderImpl.class);


    @Override
    public Collection<Dancer> getDancers() throws Exception {
        return getDancers(-1);
    }

    @Override
    public Collection<Dancer> getDancers(int maxNumber) throws Exception {
        //to control max munber of persons to return
        int counter = 0;
        List<Dancer> dancers = new ArrayList();
        XSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_XLSX, SheetName.DANCERS);
        Iterator<Row> rowIterator = sheet.iterator();
        Row header = rowIterator.next();

        int numOfColumns = header.getLastCellNum();
        LOGGER.debug("numberof cells: " + numOfColumns);

        //MAP with numbers of column and it's name
        Map<Integer, String> fieldColumnMap = FileReaderHelper.getColumnToFiledMap(header);

        LOGGER.debug(fieldColumnMap.toString());

        while (rowIterator.hasNext()) {
            if (counter > maxNumber) {
                break;
            }
            Row row = rowIterator.next();
            Dancer dancer = new Dancer();
            for (int i = 0; i < numOfColumns; i++) {
                String columnName = fieldColumnMap.get(i);
                Cell cell = row.getCell(i);
                if (columnName != null && cell.toString().length() > 0) {
                    FileReaderHelper.constructPerson(dancer, columnName, cell);
                }

            }
            counter++;
            dancers.add(dancer);
        }


        return dancers;
    }

    @Override
    public Set<Club> getClubs() throws Exception {
        Set<Club> clubs = new HashSet<Club>();

        XSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_XLSX, SheetName.CLUB_NAME);
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


    public Collection<Event> getEvents() throws Exception {
        Collection<Event> events = new ArrayList<>();

        XSSFSheet sheet = FileReaderHelper.getSheet(FileName.ASH_XLSX, SheetName.RATING);
        Map<String, Integer> rowMap = EventHelper.determineRows(sheet);
        int indexOfNamesRow = rowMap.get(EventHelper.EVENT_NAME_ROW);
        int indexOfDateRow = rowMap.get(EventHelper.EVENT_DATE);
        int cellNum = rowMap.get(EventHelper.EVENT_NAME_START_CELL);

        Row namesNow = sheet.getRow(indexOfNamesRow);
        Row datesRow = sheet.getRow(indexOfDateRow);

        Iterator<Cell> dateCellIterator = datesRow.iterator();
        Iterator<Cell> nameCellIterator = namesNow.iterator();

        //todo: WTH how it's possible?
        // 7 != 9

        skipCells(dateCellIterator, 9);
        skipCells(nameCellIterator, 7);

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
//            if(cellNum == 30){
//                break;
//            }
        }

        return null;
    }

    private boolean parseDateCell(Event eventIn, Cell dateCell) throws Exception {
        Date resultDate = null;
        if (Cell.CELL_TYPE_NUMERIC == dateCell.getCellType()) {
            String cellToString = dateCell.toString();
            LOGGER.debug("getDate() - Cell.toString: " + cellToString);
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

    @Deprecated
    private boolean parseDateCellOld(Event eventIn, Cell dateCell) {
        if (Cell.CELL_TYPE_STRING == dateCell.getCellType()) {
            LOGGER.debug("parseDateCell() - CELL_TYPE_STRING: " + dateCell.toString());
            return false;

        } else if (Cell.CELL_TYPE_NUMERIC == dateCell.getCellType()) {
            LOGGER.debug("parseDateCell() - CELL_TYPE_NUMERIC: " + dateCell.toString());
            try {
                Date date = dateCell.getDateCellValue();
                LOGGER.info("dateCell.getDateCellValue(): " + date);
            } catch (Exception ex) {
                LOGGER.error("");
            }
            eventIn.setEventDate(getDate(dateCell));
            return true;
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

    private void skipCells(Iterator iterator, int numOfCellsToSkip) {
        for (int counter = 0; counter < numOfCellsToSkip; counter++) {
            iterator.next();
        }
    }
}

