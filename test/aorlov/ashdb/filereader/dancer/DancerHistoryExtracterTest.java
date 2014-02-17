package aorlov.ashdb.filereader.dancer;

import aorlov.ashdb.core.ParticipationResult;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by mouse on 2/16/14.
 */
public class DancerHistoryExtracterTest extends TestCase {
    String string1 = "(А-В2)A2/4";
    String string2 = "A8/10";
    String string3 = "E2/16+1";
    DancerHistoryExtracter extracter;
    @Before
    public void setUp() throws Exception {
       extracter = new FakeExtractor();
    }

    public void testGenerateResult() throws Exception{
        ParticipationResult result2 = extracter.generateResult(string2);
        assertEquals('A', result2.getParticipationClass());
        assertEquals(8, result2.getPlace());
        assertEquals(10, result2.getTotalPairs());
        assertEquals(0, result2.getScoresEarned());

        ParticipationResult result3 = extracter.generateResult(string3);
        assertEquals('E', result3.getParticipationClass());
        assertEquals(2, result3.getPlace());
        assertEquals(16, result3.getTotalPairs());
        assertEquals(1, result3.getScoresEarned());
    }

    private class FakeExtractor extends DancerHistoryExtracter {
        protected FakeExtractor() throws Exception {
        }

        protected void init(){
            //to nothing
        }
    }
}
