package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.util.FileName;
import aorlov.ashdb.util.SheetName;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.*;


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

        skipCells(dateCellIterator, cellNum);
        skipCells(nameCellIterator, cellNum);

        while (nameCellIterator.hasNext() && dateCellIterator.hasNext()) {
            Event event = new Event(cellNum);
            Cell nameCell = nameCellIterator.next();
            Cell dateCell = dateCellIterator.next();
            if (!parseNameCell(event, nameCell)) {
                continue;
            }
            if (!parseDateCell(event, dateCell)) {
                continue;
            }

            events.add(event);
            LOGGER.debug(event.toString());
            cellNum++;
        }

        return null;
    }

    private boolean parseDateCell(Event eventIn, Cell dateCell) {
        if (Cell.CELL_TYPE_STRING == dateCell.getCellType()) {
            LOGGER.error("NOT DATE");
            return false;

        } else if (Cell.CELL_TYPE_NUMERIC == dateCell.getCellType()) {
//            LOGGER.debug("DateCell type is Numeric");
//            Date cellValue = dateCell.getDateCellValue();
//            LOGGER.debug("Cell value [" + cellValue + ']');
            eventIn.setEventDate(getDate(dateCell));
            return true;
        }
        return false;
    }

    private Date getDate(Cell dateCell) {
        String cellToString = dateCell.toString();
        if (cellToString.indexOf('-') > 0) {
            return dateCell.getDateCellValue();
        } else {
            try {
                Date dateToReturn = new Date();
                Comment comment = dateCell.getCellComment();
                parseCommentToGetDate(comment);

                cellToString = cellToString.trim();

            } catch (Exception ex) {
                //do nothing
                return null;
            }
        }
    }

    public Date parseCommentToGetDate(Comment comment){
        String str = comment.getString().toString();


        int year = Integer.valueOf(cellToString.substring(0, 3));
        int month = Integer.valueOf(cellToString.substring(4, 5));
        int day = Integer.valueOf(cellToString.substring(6, 7));
        Calendar calendar = new GregorianCalendar(year, month, day);
        return calendar.getTime();


             return new Date();
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

