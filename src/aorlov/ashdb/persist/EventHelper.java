package aorlov.ashdb.persist;

import aorlov.ashdb.core.Event;
import aorlov.ashdb.core.XSLMetaData;

import java.sql.SQLException;

public interface EventHelper {

    public void persistEvent(Event event) throws SQLException;

    public Event getEventById(int id) throws SQLException;

    public Event getEventByMetaData(XSLMetaData metaData) throws SQLException;
}
