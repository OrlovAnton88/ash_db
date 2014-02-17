package aorlov.ashdb.core;

import java.util.Date;
import java.util.Map;

public class Event {

    public static final String EVENT_TYPE = "Event";


    private int id;
    private String fullName;
    private String name;
    private String location;
    private Date eventDate;
    private int eClassPairs;
    private int dClassPairs;
    private int cClassPairs;
    private int bClassPairs;
    private int aClassPairs;
    private Map<Character, Dancer> numberOfParticipantsByClass;
    private XSLMetaData metadata;

    /**
     * Default constructor
     */
    public Event(){

    }

    public Event(int id) {
        this.id = id;
    }


    public Map<Character, Dancer> getNumberOfParticipantsByClass() {
        return numberOfParticipantsByClass;
    }

    public void setNumberOfParticipantsByClass(Map<Character, Dancer> numberOfParticipantsByClass) {
        this.numberOfParticipantsByClass = numberOfParticipantsByClass;
    }

    public int geteClassPairs() {
        return eClassPairs;
    }

    public void seteClassPairs(int eClassPairs) {
        this.eClassPairs = eClassPairs;
    }

    public int getdClassPairs() {
        return dClassPairs;
    }

    public void setdClassPairs(int dClassPairs) {
        this.dClassPairs = dClassPairs;
    }

    public int getcClassPairs() {
        return cClassPairs;
    }

    public void setcClassPairs(int cClassPairs) {
        this.cClassPairs = cClassPairs;
    }

    public int getbClassPairs() {
        return bClassPairs;
    }

    public void setbClassPairs(int bClassPairs) {
        this.bClassPairs = bClassPairs;
    }

    public int getaClassPairs() {
        return aClassPairs;
    }

    public void setaClassPairs(int aClassPairs) {
        this.aClassPairs = aClassPairs;
    }


    public XSLMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(XSLMetaData metadata) {
        this.metadata = metadata;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString(){
        StringBuffer i = new StringBuffer("Event id[");
        i.append(id);
        i.append("] fullName[");
        i.append(fullName);
        i.append("] date[");
        i.append(eventDate);
        i.append("] eClassPairs[");
        i.append(eClassPairs);
        i.append("] dClassPairs[");
        i.append(dClassPairs);
        i.append("] cClassPairs[");
        i.append(cClassPairs);
        i.append("] bClassPairs[");
        i.append(bClassPairs);
        i.append("] aClassPairs[");
        i.append(aClassPairs);
        i.append(']');
        i.append(getMetadata().toString());
        return i.toString();
    }


}
