package aorlov.ashdb.persist;


import aorlov.ashdb.core.Event;
import aorlov.ashdb.core.XSLMetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EventHelperImpl extends JDBCDAOImpl implements EventHelper {
    private static final String INSERT_EVENT = "insert into event(" +
            "name, " +
            "event_date, " +
            "location_id, " +
            "e_class_pairs, " +
            "d_class_pairs, " +
            "c_class_pairs, " +
            "b_class_pairs, " +
            "a_class_pairs" +
            ") " +
            "values(?,?,?,?,?,?,?,?);";

    private static final String INSERT_EVENT_METADATA = "insert into metadata(" +
            "ref_id, " +
            "meta_type, " +
            "meta_key," +
            "meta_value " +
            ") " +
            "values(?,?,?,?);";

    @Override
    public void persistEvent(Event event) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet tableKeys = null;
        connection.setAutoCommit(false);
        int id = 0;
        try{
        preparedStatement = connection.prepareStatement(INSERT_EVENT, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, event.getName());
            //todo: refactor on parisng level
            //date like 20080120,27
        Date eventDate = event.getEventDate();
            if(eventDate !=null){
        preparedStatement.setDate(2, new java.sql.Date(event.getEventDate().getTime()));
            } else{
                preparedStatement.setDate(2, null);
            }
        //todo: implement location id
        preparedStatement.setInt(3, 0);
        preparedStatement.setInt(4, event.geteClassPairs());
        preparedStatement.setInt(5, event.getdClassPairs());
        preparedStatement.setInt(6, event.getcClassPairs());
        preparedStatement.setInt(7, event.getbClassPairs());
        preparedStatement.setInt(8, event.getaClassPairs());
        preparedStatement.executeUpdate();
        tableKeys = preparedStatement.getGeneratedKeys();
        tableKeys.next();
        id = tableKeys.getInt(1);
        }catch (SQLException ex){
            throw new SQLException("Error persisting Event"+event.toString()+ " : ",ex);
        }finally {
            preparedStatement.close();
            tableKeys.close();
        }

        //persist column index
        PreparedStatement metaPreparedStatement1 = null;
        try {
        metaPreparedStatement1 = connection.prepareStatement(INSERT_EVENT_METADATA);
        metaPreparedStatement1.setInt(1, id);
        metaPreparedStatement1.setString(2, Event.EVENT_TYPE);
        metaPreparedStatement1.setString(3,XSLMetaData.COLUMN);
        metaPreparedStatement1.setString(4, String.valueOf(event.getMetadata().getColumnNum()));
        metaPreparedStatement1.executeUpdate();
            connection.commit();
        } catch (SQLException ex){
            connection.rollback();
            throw new SQLException("Error persisting metadata: ", ex);
        } finally {
            metaPreparedStatement1.close();
            connection.close();


        }
    }

    @Override
    public Event getEventById(int id) throws SQLException {
        return null;
    }

    @Override
    public Event getEventByMetaData(XSLMetaData metaData) throws SQLException {
        return null;
    }
}
