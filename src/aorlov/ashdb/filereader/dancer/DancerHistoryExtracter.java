package aorlov.ashdb.filereader.dancer;


import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.ParticipationResult;
import aorlov.ashdb.core.Vocabulary;
import aorlov.ashdb.core.XSLMetaData;
import aorlov.ashdb.filereader.FileReaderHelper;
import aorlov.ashdb.persist.PersonHelper;
import aorlov.ashdb.persist.PersonHelperImpl;
import aorlov.ashdb.util.Constants;
import aorlov.ashdb.util.SheetName;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DancerHistoryExtracter {
    private static Logger LOGGER = Logger.getLogger(DancerHistoryExtracter.class);

    private static DancerHistoryExtracter instance = null;
    PersonHelper personHelper;
    HSSFSheet ratingSheet;
    int personalCodeColumnId;

    protected DancerHistoryExtracter() throws Exception{
init();
    }

    protected void init() throws Exception{
        personHelper = new PersonHelperImpl();
        ratingSheet = FileReaderHelper.getSheet(SheetName.RATING);
        personalCodeColumnId = getPersonalCodeColumnId(ratingSheet);
    }

    public Collection <ParticipationResult> getDancersResults(Collection<Dancer> dancers) throws Exception{
        for(Dancer dancer : dancers){
            int personalId = dancer.getPersonalCode();
            Iterator<Row> rowIterator = ratingSheet.iterator();
            while(rowIterator.hasNext()){
              Row row = rowIterator.next();
                if(isAppropriateRow(row, personalId)){
                    getParticipationResults(row, personalId);
                }
            }
        }
        return null;
    }

    private Collection<ParticipationResult> getParticipationResults(Row row, int personalCode) throws Exception{
        Iterator<Cell> cellIterator = row.iterator();
        //skip first cell
        cellIterator.next();
        while(cellIterator.hasNext()){
            Cell cell = cellIterator.next();
            if(Cell.CELL_TYPE_BLANK != cell.getCellType() &&
                    Cell.CELL_TYPE_FORMULA != cell.getCellType()){
                LOGGER.debug("cell index["+cell.getColumnIndex()+']'+ cell.toString());
                if(Cell.CELL_TYPE_STRING == cell.getCellType()){
                    String cellValue = cell.getStringCellValue();
                generateResult(cellValue, cell.getColumnIndex());
                }else{
                    LOGGER.error("getParticipationResults - cell type is not String ");
                }
            }
        }
        return null;
    }

    public ParticipationResult generateResult(String cellValue, int cellIndex) throws Exception{
        ParticipationResult result = new ParticipationResult();
        result.setCellValue(cellValue);
        String toParse = cellValue;
        char ashClass=' ';
        String takenPlace="";
        String totalPairs="";
            //like D13/26
            Matcher matcherRegex1 = Pattern.compile(Constants.REGEX1).matcher(toParse);
            Matcher matcherRegex2 = Pattern.compile(Constants.REGEX2).matcher(toParse);
            if (matcherRegex1.find()) {
                toParse = matcherRegex1.group();
                ashClass = toParse.charAt(0);
                toParse = toParse.substring(1,toParse.length());
                String places=toParse;
                String [] nums = places.split("/");
                takenPlace = nums[0];
                totalPairs =  nums[1];
                int takenPlaceInt;
                int totalPairsInt;
                try{
                takenPlaceInt = Integer.valueOf(takenPlace);
                totalPairsInt = Integer.valueOf(totalPairs);
                }catch (NumberFormatException ex){
                    throw new Exception("Error in parsing places", ex);
                }
                result.setParticipationClass(ashClass);
                result.setTotalPairs(totalPairsInt);
                result.setPlace(takenPlaceInt);
                result.setScoresEarned(0);

            }else if(matcherRegex2.find()){
                toParse = matcherRegex2.group();
                ashClass = toParse.charAt(0);
                toParse = toParse.substring(1,toParse.length());
                String [] placesAndScore = toParse.split("\\+");
                String  places = placesAndScore[0];
                String  score = placesAndScore[1];
                String [] nums = places.split("/");
                takenPlace = nums[0];
                totalPairs =  nums[1];
                int takenPlaceInt;
                int totalPairsInt;
                int scroreInt;
                try{
                    takenPlaceInt = Integer.valueOf(takenPlace);
                    totalPairsInt = Integer.valueOf(totalPairs);
                    scroreInt = Integer.valueOf(score);
                }catch (NumberFormatException ex){
                    throw new Exception("Error in parsing strings", ex);
                }
                result.setParticipationClass(ashClass);
                result.setTotalPairs(totalPairsInt);
                result.setPlace(takenPlaceInt);
                result.setScoresEarned(scroreInt);
            }


    return result;
    }


    private boolean isAppropriateRow(Row row, int personalCodeIn){
        Cell cell = row.getCell(personalCodeColumnId);
        if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
            Double id = cell.getNumericCellValue();
            int personalCode = id.intValue();
            if(personalCode == personalCodeIn){
                LOGGER.debug("For personal code ["+personalCode + "]" +
                        "row index is [" +cell.getRowIndex()+']');
                return true;
            }
        }
        return false;
    }

    private int getPersonalCodeColumnId(HSSFSheet sheet) throws Exception{
        int columnNumber = 999;
        Iterator <Row> iterator = sheet.iterator();
        while(iterator.hasNext()){
            Row row = iterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(Cell.CELL_TYPE_STRING == cell.getCellType() && !cell.getStringCellValue().isEmpty()){
                   String value =  cell.getStringCellValue().trim();
//                    String peronalCodeRus = Vocabulary.getInstance().getVocabulary().get(Vocabulary.PERSONAL_CODE_KEY);
                    if(value.equalsIgnoreCase(Vocabulary.PERSONAL_CODE_RUS)){
                        columnNumber = cell.getColumnIndex();
                        LOGGER.debug("Column number is: " + columnNumber);
                    }
                }
            }
        }
        if(columnNumber == 999){
            throw new Exception("Unable to find personal code column");
        }

        return columnNumber;
    }



//    private Collection<Dancer> getDancers(){
//        try {
//          return  personHelper.getPersons();
//        } catch (Exception ex){
//            LOGGER.error(ex);
//        }
//        return null;
//    }


    public static DancerHistoryExtracter getInstance() throws Exception{

        if(instance == null) {
            return new DancerHistoryExtracter();
        }
        return instance;
    }



}
