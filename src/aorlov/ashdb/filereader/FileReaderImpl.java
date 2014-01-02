package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.util.FileName;
import aorlov.ashdb.util.SheetName;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.*;


public class FileReaderImpl implements FileReader {

    private static Logger LOGGER = Logger.getLogger(FileReaderImpl.class);


    @Override
    public Collection<Dancer> getDancers() throws Exception {
        return null;
        //todo:
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
            if(counter>maxNumber){
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
}
