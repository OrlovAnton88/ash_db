package aorlov;

import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.persist.PersonHelperImpl;
import org.apache.log4j.Logger;

import java.util.List;

public class RunClass {

    private static Logger logger = Logger.getLogger(RunClass.class);


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
           List<Dancer> list = fl.testRead();
           for(Dancer dancer : list){
               logger.info(dancer.toString());
            }
        } catch(Exception ex){
            logger.info("fuck up", ex);
        }





    }

}
