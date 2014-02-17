package aorlov.ashdb.util;

/**
 * Created by mouse on 1/29/14.
 */
public class Constants {
    //D13/26
    public static final String REGEX1 = "^(E|D|C|B|A)\\d+/\\d+$";
    //D13/26+1
    public static final String REGEX2 = "^(E|D|C|B|A)\\d+/\\d+\\+\\d+$";
   // (ДнД Е-Д34-40)D36/44
    public static final String REGEX3 = "^\\(ДнД\\sЕ\\-Д\\d+\\-\\d+\\)|E|D|C|B|A|\\d+\\/\\d+$";
   //(ДнД Е-Д36-43)E10/65+1
   public static final String REGEX4 = "^\\(ДнД\\sЕ\\-Д\\d+\\-\\d+\\)|E|D|C|B|A|\\d+\\/\\d+\\+\\d+$";
    //(ДнД ДСБ06)C5/30+1
    public static final String REGEX5 = "^\\(ДнД\\sДСБ\\d+\\)|E|D|C|B|A|\\d+\\/\\d+\\+\\d$";
    //(Д-А38-40)C8/18+1
    //(Д-А19-21)B12/23

    //(В-А1)B1/5+2
    //(Д-В1)B1/3+1
    //(Д-А17-18)D6/6

}
