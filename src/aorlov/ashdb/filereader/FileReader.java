package aorlov.ashdb.filereader;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Collection;
import java.util.Set;

public interface FileReader {

    public Collection<Dancer> getDancers() throws Exception;

    public Collection<Dancer> getDancers(int maxNumber) throws Exception;

    public Set<Club> getClubs() throws Exception;

}