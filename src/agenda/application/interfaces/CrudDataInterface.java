package agenda.application.interfaces;

import agenda.models.Event;
import agenda.models.Person;

import java.util.List;

public interface CrudDataInterface {
    List<Person> getAllPersonList();
    Person getPersonById(int id);
    void addPerson(Person person);

    List<Event> getAllEventList();
    List<Event> getEventListByTypeEvent(String typeEvent);
    List<Event> getEventListByOwnerId(int ownerId);
    List<Event> getEventListByGuestId(int guestId);
    Event getEventById(int id);
    void addEvent(Event event);
}
