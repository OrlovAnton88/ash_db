package aorlov.ashdb.persist;

import aorlov.ashdb.core.Club;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public interface ClubHelper {

    public boolean persistClub(Club clubIn) throws SQLException;

    public void persistClubs(Set<Club> clubs) throws SQLException;

    public Club getClubByName(String name)  throws SQLException;

    public boolean removeClub();

    public Club amendClub(Club clubIn);

    public Collection getClubs() throws SQLException;
}