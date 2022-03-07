package agenda.application;

import agenda.application.interfaces.DisplayInterface;
import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Agenda implements Runnable {

    private DisplayInterface displayInterface;

    private HashMap<String, Descripteur> descripteursPlugins;

    private final static PluginLoader LOADER = PluginLoader.getInstance();

    private static final String APP_NAME = "RDV Smarts";

    public Agenda() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        descripteursPlugins = PluginLoader.getDescripteursFor(APP_NAME);
        Object obj = PluginLoader.getLoadPluginByInterface(DisplayInterface.class);

        displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
    }

    @Override
    public void run() {
        System.out.println("Hello world!");
        System.out.println("Plugin : " + displayInterface.test());

        PluginLoader.loadPluginInList("Test Plugin 2");
        displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
        System.out.println("Plugin : " + displayInterface.test());
    }


}
