package agenda.application.interfaces;

import java.util.List;

import javax.swing.Box;

import agenda.models.Event;

public interface DisplayInterface {
    String test();
    public Box displayEventList(List<Event> eventList);
}
