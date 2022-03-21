package agenda.tiers;

import agenda.application.interfaces.CrudDataInterface;
import agenda.models.Event;
import agenda.models.ModelInterface;
import agenda.models.Person;
import agenda.models.TypeEvent;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadDataPlugin implements CrudDataInterface {
    private List<Person> personList;
    private List<Event> eventList;
    private String strPathToDataPerson = "inputData/Person.csv";
    private String strPathToDataEvent = "inputData/Event.csv";
    private String SEPARATOR = ";";

    public LoadDataPlugin() {
        this.personList = new ArrayList<>();
        this.eventList = new ArrayList<>();
    }

    @Override
    public List<Person> getAllPersonList() {
        List<Object> objList = loadData(strPathToDataPerson, Person.class);

        for(int i=0 ; i<objList.size() ; i++) {
            Object obj = objList.get(i);
            this.personList.add((Person) obj);
        }
        return this.personList;
    }

    @Override
    public Person getPersonById(int id) {
        Person person = null;
        for(int i=0 ; i<this.personList.size() ; i++) {
            if(this.personList.get(i).getId() == id) {
                person = personList.get(i);
                break;
            }
        }
        return person;
    }

    @Override
    public void addPerson(Person person) {
        String last = this.getLastObject(strPathToDataPerson);
        int lastId = Integer.parseInt(Arrays.asList(last.split("\\s*;\\s*")).get(0));
        lastId += 1;
        person.setId(lastId);

        this.addData(strPathToDataPerson, person);
    }

    @Override
    public List<Event> getAllEventList() {
        List<Object> objList = loadData(strPathToDataEvent, Event.class);
        this.eventList = new ArrayList<>();
        for(int i=0 ; i<objList.size() ; i++) {
            Object obj = objList.get(i);
            this.eventList.add((Event) obj);
        }
        return this.eventList;
    }

    @Override
    public List<Event> getEventListByTypeEvent(String typeEvent) {
        List<Event> allEventList = getAllEventList();
        List<Event> eventListByTypeEvent = new ArrayList<>();

        for (Event event : allEventList) {
            if (event.getTypeEvent().equals(typeEvent)) {
                eventListByTypeEvent.add(event);
            }
        }

        return eventListByTypeEvent;
    }

    @Override
    public List<Event> getEventListByOwnerId(int ownerId) {
        List<Event> allEventList = getAllEventList();
        List<Event> eventListByOwner = new ArrayList<>();

        for (Event event : allEventList) {
            if (event.getOwner().getId() == ownerId) {
                eventListByOwner.add(event);
            }
        }

        return eventListByOwner;
    }

    @Override
    public List<Event> getEventListByGuestId(int guestId) {
        List<Event> allEventList = getAllEventList();
        List<Event> eventListGuestId = new ArrayList<>();

        for (Event event : allEventList) {
            List<Person> guestList = event.getListGuestEvent();
            for(Person guest : guestList) {
                if(guest.getId() == guestId) {
                    eventListGuestId.add(event);
                    break;
                }
            }
        }

        return eventListGuestId;
    }

    @Override
    public Event getEventById(int id) {
        Event event = null;
        for(int i=0 ; i<this.eventList.size() ; i++) {
            if(this.eventList.get(i).getIdEvent() == id) {
                event = eventList.get(i);
                break;
            }
        }
        return event;
    }

    @Override
    public void addEvent(Event event) {
        String last = this.getLastObject(strPathToDataEvent);
        int lastId = Integer.parseInt(Arrays.asList(last.split("\\s*;\\s*")).get(0));
        lastId += 1;
        event.setIdEvent(lastId);

        this.addData(strPathToDataEvent, event);
    }


    private List<Object> loadData(String filename, Class<?> returnClass) {
        List<Object> fileDataList = new ArrayList<>();
        Path pathToFile = Paths.get(filename);


        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {
            // read the first line from the text file
            String line = br.readLine();
            //Escape header
            line = br.readLine();

            while (line != null) {
                String[] attributes = line.split(SEPARATOR);

                Object obj = returnClass.getDeclaredConstructor().newInstance();
                obj = createFromModel(obj.getClass().getName(), attributes);

                fileDataList.add(obj);

                line = br.readLine();
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return fileDataList;
    }

    private Object createFromModel(String model, String[] attributes) {
        if(model.equals("agenda.models.Person")) {
            Person person = new Person();
            person.setId(Integer.parseInt(attributes[0]));
            person.setFirstname(attributes[1]);
            person.setLastname(attributes[2]);
            person.setMail(attributes[3]);

            return person;
        }else if(model.equals("agenda.models.Event")) {
            Event event = new Event();
            Person person = new Person();
            event.setIdEvent(Integer.parseInt(attributes[0]));
            event.setTitleEvent(attributes[1]);
            event.setDateEvent(attributes[2]);
            event.setDurationEvent(Integer.parseInt(attributes[3]));
            event.setDescriptionEvent(attributes[4]);
            event.setTypeEvent(attributes[5]);

            person = this.getPersonById(Integer.parseInt(attributes[6]));
            event.setOwner(person);
            String personGuestStr = attributes[7];
            List<Person> personGuestList = new ArrayList<>();
            personGuestList = getPersonListByStrValues(personGuestStr);
            event.setListGuestEvent(personGuestList);

            return event;
        }else {
            return null;
        }

    }

    private List<Person> getPersonListByStrValues(String personStr) {
        List<String> personIdList = new ArrayList<>();
        List<Person> personList = new ArrayList<>();

        personIdList = Arrays.asList(personStr.split("\\s*,\\s*"));

        for(int i=0 ; i< personIdList.size() ; i++) {
            Person person = new Person();
            person = this.getPersonById(Integer.parseInt(personIdList.get(i)));
            personList.add(person);
        }

        return personList;
    }

    private void addData(String filename, ModelInterface obj) {
        try {
            FileWriter bw1 = new FileWriter(filename, true);
            bw1.write("\n");
            bw1.write(obj.toCSV());
            bw1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLastObject(String filename) {
        BufferedReader reader = null;
        String lastLine = "";
        try {
            reader = new BufferedReader(new FileReader(filename));
            String next, line = reader.readLine();
            for (boolean first = true, last = (line == null); !last; first = false, line = next) {
                last = ((next = reader.readLine()) == null);
                if (last) {
                    lastLine = line;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException logOrIgnore) {}
        }
        return lastLine;
    }

}
