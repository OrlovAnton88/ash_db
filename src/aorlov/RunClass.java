package aorlov;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.geo.City;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.filereader.FileReaderImplOld;
import aorlov.ashdb.persist.PersonHelperImpl;
import aorlov.ashdb.xmlreader.XMLReader;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RunClass {

    private static Logger logger = Logger.getLogger(RunClass.class);


    public static void main(String[] args) {


        FileReaderImpl fl = new FileReaderImpl();
        FileReaderImplOld flOld = new FileReaderImplOld();
        PersonHelperImpl helper = new PersonHelperImpl();

       Map<String, City> cityMap =  XMLReader.getInstance().getCityMap();


        try{
//            Set<Club> clubs = fl.getClubs();
//            for(Club club: clubs){
//                logger.info(club.toString());
//                String clubCity = club.getCity();
//                if(cityMap.containsKey(clubCity)){
//                    logger.debug("MATCH!!! ");
//                }
//
//            }
//            flOld.getDancers(100);
//           List<Dancer> list = (List) fl.getDancers(100);
//           for(Dancer dancer : list){
//               logger.info(dancer.toString());
//            }

//            fl.getEvents ();
        } catch(Exception ex){
            logger.info("fuck up", ex);
        }





    }

}
