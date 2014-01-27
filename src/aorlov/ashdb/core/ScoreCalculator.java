package aorlov.ashdb.core;

import aorlov.ashdb.filereader.FileReader;
import aorlov.ashdb.filereader.FileReaderImpl;
import org.apache.log4j.Logger;

/**
 * Created by mouse on 1/25/14.
 */
public class ScoreCalculator {

    public static ScoreCalculator instance;
    int [][] matrix = null;

    public ScoreCalculator(){
        init();
    }

    private void init(){
        try{
        FileReader fileReader = new FileReaderImpl();
        matrix = fileReader.getScoresMatrix();
        }catch (Exception ex ){
            LOGGER.debug("error in getting score matrix");
        }
    }

    private static Logger LOGGER = Logger.getLogger(ScoreCalculator.class);


    public int getScores(int place, int totalPairs){
        int column = totalPairs;
        int score = 0;
        if(totalPairs % 2 == 0){
            LOGGER.debug(this.getClass().getName() + ".getScores: Number is even");
        } else{
            LOGGER.debug(this.getClass().getName() + ".getScores: Number is odd");
            column--;
        }

        if(column == 3 && place == 1) {
          score = 1;
        }else {
           score =  matrix[place-1][(column/2)-1];
        }
        return score;
    }


    public static ScoreCalculator getInstance(){
        if(instance == null){
            instance = new ScoreCalculator();
        }
        return instance;
    }
}
