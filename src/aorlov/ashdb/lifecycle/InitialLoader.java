package aorlov.ashdb.lifecycle;


import aorlov.ashdb.core.Dancer;
import aorlov.ashdb.core.Event;
import aorlov.ashdb.filereader.FileReader;
import aorlov.ashdb.filereader.FileReaderImpl;
import aorlov.ashdb.persist.*;
import org.apache.log4j.Logger;

import java.util.Collection;

public class InitialLoader {

    private static Logger LOGGER = Logger.getLogger(InitialLoader.class);

    FileReader fileReader;
    PersonHelper personHelper;
    ClubHelper clubHelper;
    EventHelper eventHelper;

   public InitialLoader() {
        fileReader = new FileReaderImpl();
        personHelper = new PersonHelperImpl();
        clubHelper = new ClubHelperImpl();
        eventHelper = new EventHelperImpl();
    }

    public void rollLifeCycle() {
        try {
//            LOGGER.info("Starting parsing dancers");
//
//            Collection<Dancer> dancers = fileReader.getDancers();
//
//            LOGGER.info("Parsing finished successfully");
//            LOGGER.info("Starting persisting dancers");
//
//            for (Dancer dancer : dancers) {
//                personHelper.persistPerson(dancer);
//            }
//
//            LOGGER.info("Dancers have persisted");
            LOGGER.info("Starting parsing events");
            Collection<Event> events = fileReader.getEvents();
            LOGGER.info("Events have parsed successfully");
            LOGGER.info("Starting persisting events");
            for(Event event: events){
                LOGGER.debug(event.toString());
            eventHelper.persistEvent(event);
            }
            LOGGER.info("Events persisted successfully");

        } catch (Exception ex) {
            LOGGER.error(ex + ex.getMessage());
        }
    }

}
