package aorlov.ashdb.persist;

import aorlov.ashdb.core.Club;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ClubHelperImpl extends JDBCDAOImpl implements ClubHelper {

    private static final String INSERT_CLUB = "insert into club (name, country,city, website) values (?,?,?,?);";
    private static final String SELECT_ALL_CLUBS = "SELECT id, name, country,city, website from  club;";
    private static final String SELECT_CLUB_BY_NAME = "SELECT id, name, country,city, website from club where name =?;";


    @Override
    public boolean persistClub(Club clubIn) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLUB);
        preparedStatement.setString(1, clubIn.getName());
        preparedStatement.setString(2, "N/A");
        preparedStatement.setString(3, clubIn.getCity());
        preparedStatement.setString(4, "N/A");
        int result = 0;
        try {
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error during insert " + clubIn.toString(), ex);
        } finally {
            preparedStatement.close();
            connection.close();

        }
        if (result == 1) {
            return true;
        }

        return false;
    }

    @Override
    public void persistClubs(Set<Club> clubs) throws SQLException {
        for (Club club : clubs) {
            persistClub(club);
        }
    }

    @Override
    public Club getClubByName(String name) throws SQLException{
        Club club = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(SELECT_CLUB_BY_NAME);
            statement.setString(1,name);
            resultSet = statement.executeQuery();

            if(resultSet.next()){
                club = new Club();
                club.setId(resultSet.getInt(1));
                club.setName(name);
                club.setCountry(resultSet.getString(3));
                club.setCity(resultSet.getString(4));
                club.setWebSite(resultSet.getString(5));
            }
        }catch (SQLException ex){
            throw new SQLException("Error getting club for name ["+name+ ']', ex);

        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return club;
    }

    @Override
    public boolean removeClub() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Club amendClub(Club clubIn) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection getClubs() throws SQLException {
        Collection<Club> clubs = new ArrayList<Club>();
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_ALL_CLUBS);
            while (resultSet.next()) {
                Club club = new Club();
                club.setId(resultSet.getInt("id"));
                club.setName(resultSet.getString("name"));
                club.setCity(resultSet.getString("city"));
                club.setWebSite(resultSet.getString("website"));
                club.setCountry(resultSet.getString("country"));
                clubs.add(club);
            }

            statement = connection.createStatement();
        } catch (SQLException ex) {
            throw new SQLException("Error getting club from database ", ex);
        } finally {
            resultSet.close();
            statement.close();
            connection.close();
        }
        return clubs;


    }
}
