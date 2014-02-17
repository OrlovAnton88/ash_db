package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Row;

import java.util.Collection;
import java.util.Set;

public interface FileReader {

    public Collection<Dancer> getDancers() throws Exception;

    public Collection<Dancer> getDancers(int maxNumber) throws Exception;

    public Collection<Event> getEvents() throws Exception;

    public Collection<Event> getEvents(int maxNum) throws Exception;

    public Set<Club> getClubs() throws Exception;

    public Collection getDancerHistory(Dancer dancer) throws Exception;

    public int[][] getScoresMatrix() throws Exception;

}