package aorlov.ashdb.core;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public class Dancer {

    public static final char MALE = 'M';
    public static final char FEMALE = 'F';

    private String name;
    private String lastName;
    private String familyName;

    private char gender;
    private String club;

    private char currentClass;

    private int personalCode;

    private Map achievements;

    private Collection history;

    private Date registrationDate;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * @return the club
     */
    public String getClub() {
        return club;
    }

    /**
     * @param club the club to set
     */
    public void setClub(String club) {
        this.club = club;
    }

    /**
     * @return the currentClass
     */
    public char getCurrentClass() {
        return currentClass;
    }

    /**
     * @param currentClass the currentClass to set
     */
    public void setCurrentClass(char currentClass) {
        this.currentClass = currentClass;
    }

    /**
     * @return the personalCode
     */
    public int getPersonalCode() {
        return personalCode;
    }

    /**
     * @param personalCode the personalCode to set
     */
    public void setPersonalCode(int personalCode) {
        this.personalCode = personalCode;
    }

    /**
     * @return the achievements
     */
    public Map getAchievements() {
        return achievements;
    }

    /**
     * @param achievements the achievements to set
     */
    public void setAchievements(Map achievements) {
        this.achievements = achievements;
    }

    /**
     * @return the history
     */
    public Collection getHistory() {
        return history;
    }

    /**
     * @param history the history to set
     */
    public void setHistory(Collection history) {
        this.history = history;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String toString() {
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("Personal Code [");
        toReturn.append(personalCode);
        toReturn.append(']');
        toReturn.append("Name [");
        toReturn.append(name);
        toReturn.append(']');
        toReturn.append("Last name [");
        toReturn.append(lastName);
        toReturn.append(']');
        toReturn.append("Family name [");
        toReturn.append(familyName);
        toReturn.append(']');
        toReturn.append("Gender [");
        toReturn.append(gender);
        toReturn.append(']');
        toReturn.append("Current Class [");
        toReturn.append(currentClass);
        toReturn.append(']');
        toReturn.append("club [");
        toReturn.append(club);
        toReturn.append(']');
        toReturn.append("Reg.Date [");
        toReturn.append(registrationDate);
        toReturn.append(']');
        return toReturn.toString();

    }


}
