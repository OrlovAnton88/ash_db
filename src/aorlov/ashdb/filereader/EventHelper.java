package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Event;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventHelper {

    private static Logger LOGGER = Logger.getLogger(EventHelper.class);

    public static final String EVENT_NAME_ROW = "EVENT_NAME";
    public static final String EVENT_NAME_START_CELL= "EVENT_NAME_START_CELL";
    public static final String EVENT_DATE = "EVENT_DATE";
    public static final String EVENT_E_CLASS = "EVENT_E_CLASS";
    public static final String EVENT_D_CLASS = "EVENT_D_CLASS";


    public static void decodeEventName(Event event, String dirtyName){
        String name = FileReaderHelper.removeLineBreak(dirtyName);
        LOGGER.info(name);
        String toExtract = name;
        String city = "Unknown";
        int cityIndex = toExtract.length();
        if (toExtract.contains("г.")) {
            cityIndex = toExtract.lastIndexOf("г.");
            city = toExtract.substring(cityIndex+2, toExtract.length());
            cityIndex = cityIndex-1;
        }
        name = name.substring(0,cityIndex);

        event.setName(name);
        event.setLocation(city);
//        LOGGER.info(dirtyName);
//        LOGGER.info(name + " - " + city);

    }

    public static Map<String,Integer> determineRows(XSSFSheet sheetIn) {

        //stub
        Map<String, Integer> map = new HashMap<>();
        map.put(EVENT_NAME_ROW,1);
        map.put(EVENT_DATE,2);
        map.put(EVENT_E_CLASS,3);
        map.put(EVENT_D_CLASS,4);
        map.put(EVENT_NAME_START_CELL,11);
        return map;

////       Iterator<Row> rowIterator = sheetIn.iterator();
//        int lastRow = sheetIn.getLastRowNum();
//        int counter = 0;
//        for (counter = 0; counter < lastRow; counter++) {
//
//            Row row = sheetIn.getRow(counter);
//            Iterator<Cell> cellIterator = row.iterator();
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//                int cellType = cell.getCellType();
//                if (Cell.CELL_TYPE_STRING == cellType) {
//
//                } else if (Cell.CELL_TYPE_NUMERIC == cellType) {
//
//                }
//            }
//        }
    }

}
