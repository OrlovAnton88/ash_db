package aorlov;

import aorlov.ashdb.core.Club;
import aorlov.ashdb.core.Dancer;
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
//            Set<Club> list = fl.getClubs();
//            Collection list = Arrays.asList(clubs.toArray());

            ClubHelper helper1 = new ClubHelperImpl();
//            helper1.persistClubs(list);
//           Collection<Club> clubs = helper1.getClubs();
//            for(Club club : clubs){
//                LOGGER.debug(club.toString());
//            }

            Collection<Dancer> list = fl.getDancers();

           PersonHelperImpl personHelper = new PersonHelperImpl();
           personHelper.persistPersons(list);

//            fl.getEvents ();
        } catch(Exception ex){
            LOGGER.info("fuck up", ex);
        }





    }

}
