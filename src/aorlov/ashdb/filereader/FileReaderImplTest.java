package aorlov.ashdb.filereader;

import junit.framework.TestCase;
import org.junit.Test;

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
}
