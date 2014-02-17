package aorlov.ashdb.persist;

import aorlov.ashdb.core.Dancer;

import java.sql.SQLException;
import java.util.List;


public interface PersonHelper {

    public Dancer getPersonById(int id) throws SQLException;

    public Dancer getPersonByName(String name, String lastName, String familyName);

    public List<Dancer> getPersons() throws SQLException;

    public boolean persistPerson(Dancer dancerIn) throws SQLException;

    public boolean deletePerson(int idIn);

    public Dancer amendPerson(int idIn);

}