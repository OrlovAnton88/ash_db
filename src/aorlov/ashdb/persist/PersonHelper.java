package aorlov.ashdb.persist;

import aorlov.ashdb.core.Dancer;

import java.sql.SQLException;


public interface PersonHelper {

    public Dancer getPersonById();

    public Dancer getPersonByName(String name, String lastName, String familyName);

    public boolean persistPerson(Dancer dancerIn) throws SQLException;

    public boolean deletePerson(int idIn);

    public Dancer amendPerson(int idIn);

}