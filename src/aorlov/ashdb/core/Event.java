package aorlov.ashdb.core;

import java.util.Date;

public class Event {


    private int id;
    private String fullName;
    private String name;
    private String location;
    private Date eventDate;
    private int numberOfParticipants;


    public Event(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public String toString(){
        StringBuffer i = new StringBuffer("Event id[");
        i.append(id);
        i.append("] fullName[");
        i.append(fullName);
        i.append("] date[");
        i.append(eventDate);
        i.append(']');
        return i.toString();
    }


}
