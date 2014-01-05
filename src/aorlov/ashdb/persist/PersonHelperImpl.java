package aorlov.ashdb.persist;


import aorlov.ashdb.core.Dancer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class PersonHelperImpl extends JDBCDAOImpl implements PersonHelper {

    private static final String INSERT_PERSON = "insert into dancer(id,name, lastname, familyname, gender, currentclass, clubid, registration_tms) \n" +
            "values(?,?,?,?,?,?,?,?);";

    JDBCDAOImpl jdbcdao = null;

    public PersonHelperImpl() {
        jdbcdao = new JDBCDAOImpl();
    }

    @Override
    public Dancer getPersonById() {
        return new Dancer();
    }

    @Override
    public Dancer getPersonByName(String name, String lastName, String familyName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean persistPerson(Dancer dancerIn) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PERSON);
        preparedStatement.setInt(1, dancerIn.getPersonalCode());
        preparedStatement.setString(2, dancerIn.getName());
        preparedStatement.setString(3, dancerIn.getLastName());
        preparedStatement.setString(4, dancerIn.getFamilyName());
        preparedStatement.setString(5, Character.toString(dancerIn.getGender()));
        preparedStatement.setString(6, Character.toString(dancerIn.getCurrentClass()));
        preparedStatement.setInt(7, 0);
        java.util.Date registrationDate= dancerIn.getRegistrationDate();
        if(registrationDate != null){
            preparedStatement.setDate(8, new java.sql.Date(registrationDate.getTime()));
        } else{
            preparedStatement.setDate(8, new java.sql.Date(new Date().getTime()));
        }
        int result = 0;
        try {
            result = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error during insert " + dancerIn.toString(), ex);
        }

        if (result == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deletePerson(int idIn) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Dancer amendPerson(int idIn) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
