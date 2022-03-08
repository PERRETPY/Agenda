package agenda.models;

import java.util.Date;
import java.util.List;

public class Event implements ModelInterface {
    private int idEvent;
    private String titleEvent;
    private String dateEvent;
    private int durationEvent;
    private String descriptionEvent;
    private String typeEvent;
    private Person owner;
    private List<Person> listGuestEvent;

    public Event(String titleEvent, String dateEvent, int durationEvent, String descriptionEvent, String typeEvent, Person owner, List<Person> listGuestEvent) {
        this.titleEvent = titleEvent;
        this.dateEvent = dateEvent;
        this.durationEvent = durationEvent;
        this.descriptionEvent = descriptionEvent;
        this.typeEvent = typeEvent;
        this.owner = owner;
        this.listGuestEvent = listGuestEvent;
    }

    public Event(int idEvent, String titleEvent, String dateEvent, int durationEvent, String descriptionEvent, String typeEvent, List<Person> listGuestEvent) {
        this.idEvent = idEvent;
        this.titleEvent = titleEvent;
        this.dateEvent = dateEvent;
        this.durationEvent = durationEvent;
        this.descriptionEvent = descriptionEvent;
        this.typeEvent = typeEvent;
        this.listGuestEvent = listGuestEvent;
    }

    public Event() {

    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
    }

    public int getDurationEvent() {
        return durationEvent;
    }

    public void setDurationEvent(int durationEvent) {
        this.durationEvent = durationEvent;
    }

    public String getDescriptionEvent() {
        return descriptionEvent;
    }

    public void setDescriptionEvent(String descriptionEvent) {
        this.descriptionEvent = descriptionEvent;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public List<Person> getListGuestEvent() {
        return listGuestEvent;
    }

    public void setListGuestEvent(List<Person> listGuestEvent) {
        this.listGuestEvent = listGuestEvent;
    }

    @Override
    public String toCSV() {
        return null;
    }
}
