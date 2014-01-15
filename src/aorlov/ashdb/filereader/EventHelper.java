package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Event;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventHelper {

    private static Logger LOGGER = Logger.getLogger(EventHelper.class);

    public static final String EVENT_NAME_ROW = "EVENT_NAME";
    public static final String EVENT_NAME_START_CELL = "EVENT_NAME_START_CELL";
    public static final String EVENT_DATE = "EVENT_DATE";
    public static final String EVENT_E_CLASS = "EVENT_E_CLASS";
    public static final String EVENT_D_CLASS = "EVENT_D_CLASS";
    public static final String EVENT_C_CLASS = "EVENT_C_CLASS";
    public static final String EVENT_B_CLASS = "EVENT_B_CLASS";
    public static final String EVENT_A_CLASS = "EVENT_A_CLASS";

    public static final String numOfEPairs = "\"E\" хх пар";
    public static final String PARTICIPANTS_CELL_PATTERN = "\"[A-Z]{1}\" \\d{2} пар";


    public static void decodeEventName(Event event, String dirtyName) {
        String name = FileReaderHelper.removeLineBreak(dirtyName);
        LOGGER.info(name);
        String toExtract = name;
        String city = "Unknown";
        int cityIndex = toExtract.length();
        if (toExtract.contains("г.")) {
            cityIndex = toExtract.lastIndexOf("г.");
            city = toExtract.substring(cityIndex + 2, toExtract.length());
            cityIndex = cityIndex - 1;
        }
        name = name.substring(0, cityIndex);

        event.setName(name);
        event.setLocation(city);
//        LOGGER.info(dirtyName);
//        LOGGER.info(name + " - " + city);

    }

    public static Map<String, Integer> determineRows(HSSFSheet sheetIn) {

        //stub
        Map<String, Integer> map = new HashMap<>();
        map.put(EVENT_NAME_ROW, 1);
        map.put(EVENT_DATE, 2);
        map.put(EVENT_E_CLASS, 3);
        map.put(EVENT_D_CLASS, 4);
        map.put(EVENT_C_CLASS,5);
        map.put(EVENT_B_CLASS,6);
        map.put(EVENT_A_CLASS,7);
        map.put(EVENT_NAME_START_CELL, 11);
        return map;

    }

}
