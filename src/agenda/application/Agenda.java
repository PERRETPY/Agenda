package agenda.application;

import agenda.application.interfaces.CrudDataInterface;
import agenda.application.interfaces.DisplayInterface;
import agenda.models.Event;
import agenda.models.Person;
import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Agenda implements Runnable {

    private DisplayInterface displayInterface;
    private CrudDataInterface crudDataInterface;

    private HashMap<String, Descripteur> descripteursPlugins;

    private final static PluginLoader LOADER = PluginLoader.getInstance();

    private static final String APP_NAME = "RDV Smarts";

    private List<Person> personList;
    private List<Event> eventList;

    public Agenda() {
        this.personList = new ArrayList<>();
        this.eventList = new ArrayList<>();

        descripteursPlugins = PluginLoader.getDescripteursFor(APP_NAME);
        displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
        crudDataInterface = (CrudDataInterface) PluginLoader.getLoadPluginByInterface(CrudDataInterface.class);
    }

    @Override
    public void run() {

        FrameWindow frameWindow = new FrameWindow();
        Thread t = new Thread((Runnable) frameWindow);
        t.start();

        System.out.println("Hello world!");
        System.out.println("Plugin : " + displayInterface.test());

        PluginLoader.loadPluginInList("Test Plugin 2");
        displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
        System.out.println("Plugin : " + displayInterface.test());

        this.personList = crudDataInterface.getAllPersonList();

        System.out.println(personList.toString());

        this.eventList = crudDataInterface.getAllEventList();

        System.out.println(eventList.toString());
        Person person = new Person("Testfirstname", "Testlastname", "Testfirstname.Testlastname@yopmail.com");
        crudDataInterface.addPerson(person);

        this.personList = crudDataInterface.getAllPersonList();

        System.out.println(personList.toString());
    }


}
