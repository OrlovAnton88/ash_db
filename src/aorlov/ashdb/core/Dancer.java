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

    private int clubId;

    private char currentClass;

    private int personalCode;

    private Map achievements;

    private Collection history;

    private Date registrationDate;

    private XSLMetaData metaData;

    public int getClubId() {
        return clubId;
    }

    public void setClubId(int clubId) {
        this.clubId = clubId;
    }

    public Dancer(){

    }

    public Dancer(int id){
        this.personalCode = id;
    }

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
     * return personal code of 6  digits as in ASH db-file
     * @return
     */
    public String getPersonalCodeString(){
        int lenght = String.valueOf(personalCode).length();
        if(lenght<5){
            int zeroToAdd = 5-lenght;
            StringBuffer value = new StringBuffer(String.valueOf(personalCode));
            for(int i = 0; i< zeroToAdd; i++){
                value.insert(0,'0');
            }
            return value.toString();
        }else {
            return String.valueOf(personalCode);
        }
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


    public XSLMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(XSLMetaData metaData) {
        this.metaData = metaData;
    }

    public String toString() {
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("Personal Code [");
        toReturn.append(getPersonalCodeString());
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
        toReturn.append("clubId [");
        toReturn.append(club);
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
