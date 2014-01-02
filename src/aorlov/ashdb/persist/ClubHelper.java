package aorlov.ashdb.persist;

import aorlov.ashdb.core.Club;

public interface ClubHelper {

    public Club addClub();

    public Club getClubByName();

    public boolean removeClub();

    public Club amendClub();
}