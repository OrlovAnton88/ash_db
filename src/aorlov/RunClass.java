package aorlov;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.core.geo.City;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.persist.ClubHelper;
import aorlov.ashdb.persist.ClubHelperImpl;
import aorlov.ashdb.persist.PersonHelperImpl;
import aorlov.ashdb.xmlreader.XMLReader;
import org.apache.log4j.Logger;

import java.util.*;

public class RunClass {

    private static Logger LOGGER = Logger.getLogger(RunClass.class);


    public static void main(String[] args) {


        FileReaderImpl fl = new FileReaderImpl();
        PersonHelperImpl helper = new PersonHelperImpl();

//       Map<String, City> cityMap =  XMLReader.getInstance().getCityMap();



        try{
            Collection <Event> events = fl.getEvents(10);

            for(Event event : events){
                LOGGER.debug(event.toString());
            }

        } catch(Exception ex){
            LOGGER.info("fuck up", ex);
        }





    }

}
