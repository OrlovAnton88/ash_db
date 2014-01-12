package aorlov.ashdb.filereader.club;

import aorlov.ashdb.core.Club;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;

public class ClubHelper {

    private static Logger LOGGER = Logger.getLogger(ClubHelper.class);

    public static void decodeClubName(Club clubIn, Cell cellIn) throws Exception {
        String value = "";
        if (cellIn.getCellType() == Cell.CELL_TYPE_STRING) {
            value = cellIn.getStringCellValue();
        } else {
            throw new Exception("Unable to getStringCellValue");
        }

        value = value.replace(")", "");
        String[] params = value.split("\\(");

        String clubName = params[0].trim();
        clubIn.setName(clubName);
        if (params.length > 1) {
            String dirtyCity = params[1].trim();
            String city = dirtyCity;
            if (dirtyCity.contains("г.")) {
                int index = dirtyCity.lastIndexOf("г.");
                city = dirtyCity.substring(index + 2, dirtyCity.length());
            }
            clubIn.setCity(city);
        }
        if (params.length > 2) {
            LOGGER.warn("Not standart value" + value);
        }

    }
}
