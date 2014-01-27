package aorlov.test.filereader;

import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.filereader.FileReaderImpl;
import com.sun.xml.internal.fastinfoset.util.CharArray;
import com.sun.xml.internal.fastinfoset.util.CharArrayString;
import junit.framework.TestCase;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.ss.usermodel.Comment;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Mouse
 * Date: 04.01.14
 * Time: 3:48
 * To change this template use File | Settings | File Templates.
 */
public class FileReaderImplTest extends TestCase {


    public void testGetDancerHistory() throws Exception{
        Dancer dancer = new Dancer();
        dancer.setName("Григорий");
        dancer.setLastName("Аверин");
        dancer.setFamilyName("Анатольевич");

        FileReaderImpl fl = new FileReaderImpl();
        fl.getDancerHistory(dancer);

    }
    @Test
    public void testParseCommentToGetDate() throws Exception {
        String str = "Название: Зимний Кубок Спб \n" +
                "Дата: 2010.01.23\n" +
                "Организатор: Санкт-Петербург\n" +
                "Место: Центр Спортивного Танца";
        Matcher matcher = Pattern.compile("\\d{4}.\\d{2}.\\d{2}").matcher(str);
        matcher.find();
        System.out.println(matcher.group());

    }

    @Test
    public void testParseDateFromString() throws Exception{
        String str = "2010.01.6";

        DateFormat df = new SimpleDateFormat("yyyy.MM.d");
        Date result =  df.parse(str);

        System.out.println(result);

    }

    @Test
    public void testDoubleToDate() throws Exception{

//       - getDate() - Cell.toString: 2.0080525E7
//       - Unable to get date from string:  Кубок Столицы (АСХ, Движение)

        String str = "2.0080525E7";
        StringBuffer toReturn = new StringBuffer();
        CharSequence charSequence = new CharArrayString(str);
        for(int i=0; i<charSequence.length();i++){
            char ch = charSequence.charAt(i);
            if(ch == '.'){
                continue;
            }else if(ch == 'E'){
                break;
            }
            toReturn.append(ch);

        }
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date result  =  df.parse(toReturn.toString());
        System.out.println(result);
    }

    public void testGetEvents() throws Exception{
        Event toTest = new Event();
        toTest.setEventDate(new Date());
        toTest.setName("N/A");
        FileReaderImpl fl = new FileReaderImpl();
        Collection<Event> events = fl.getEvents(10);
        for(Event event: events){
            if("Открытый  турнир  г.Красноярск".equals(event.getFullName().trim())){
                toTest = event;
                break;
            }
        }
        Calendar calendar = new GregorianCalendar(2013,11,8);
        Date date = new Date(calendar.getTimeInMillis());
        assertEquals(date, toTest.getEventDate());


    }



    public void testGetScrores() throws Exception{
        FileReaderImpl fl = new FileReaderImpl();
        fl.getScoresMatrix();

    }
}

