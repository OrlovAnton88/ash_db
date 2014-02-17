package aorlov.ashdb.core;


public class ParticipationResult {

    private String cellValue;
    private Event event;
    private char participationClass;
    private int place;
    private int totalPairs;
    private int scoresEarned;

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue;
    }

    public int getTotalPairs() {
        return totalPairs;
    }

    public void setTotalPairs(int totalPairs) {
        this.totalPairs = totalPairs;
    }

    public int getScoresEarned() {
        return scoresEarned;
    }

    public void setScoresEarned(int scoresEarned) {
        this.scoresEarned = scoresEarned;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public char getParticipationClass() {
        return participationClass;
    }

    public void setParticipationClass(char participationClass) {
        this.participationClass = participationClass;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
