package test.filereader;

import aorlov.ashdb.core.ScoreCalculator;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by mouse on 1/26/14.
 */
public class ScoreCalculatorTest  extends TestCase{
    @Test
    public void testGetScores() throws Exception {

        int result = ScoreCalculator.getInstance().getScores(2, 11);
        assertEquals(3, result);
        result = ScoreCalculator.getInstance().getScores(5, 30);
        assertEquals(3, result);
        result = ScoreCalculator.getInstance().getScores(2, 45);
        assertEquals(5, result);
        result = ScoreCalculator.getInstance().getScores(75, 151);
        assertEquals(1, result);
    }
}
