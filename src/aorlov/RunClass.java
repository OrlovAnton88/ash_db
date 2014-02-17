package aorlov;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.core.geo.City;
import aorlov.ashdb.filereader.EventHelper;
import aorlov.ashdb.filereader.FileReader;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.filereader.dancer.DancerHistoryExtracter;
import aorlov.ashdb.lifecycle.InitialLoader;
import aorlov.ashdb.persist.*;
import aorlov.ashdb.xmlreader.XMLReader;
import org.apache.log4j.Logger;

import java.util.*;

public class RunClass {

    public RunClass() throws Exception{
    }

    private static Logger LOGGER = Logger.getLogger(RunClass.class);


    public static void main(String[] args) {

        try{
//            InitialLoader loader = new InitialLoader();
//            loader.rollLifeCycle();
            PersonHelper helper = new PersonHelperImpl();
           Dancer dancer =  helper.getPersonById(223);
            ArrayList<Dancer> dancers = new ArrayList<>();
            dancers.add(dancer);

            DancerHistoryExtracter.getInstance().getDancersResults(dancers);



        } catch(Exception ex){
            LOGGER.info("fuck up", ex);
        }





    }

}
