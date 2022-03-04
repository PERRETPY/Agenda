package agenda.application;

import agenda.application.interfaces.DisplayInterface;
import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;

import java.util.HashMap;

public class Agenda implements Runnable {

    private DisplayInterface displayInterface;

    private HashMap<String, Descripteur> descripteursPlugins;

    private final static PluginLoader LOADER = PluginLoader.getInstance();

    private static final String APP_NAME = "RDV Smarts";

    public Agenda() {
        descripteursPlugins = LOADER.getDescripteursFor(APP_NAME);
        displayInterface = (DisplayInterface) LOADER.getLoadPluginByInterface(DisplayInterface.class);

    }

    @Override
    public void run() {
        System.out.println("Hello world!");
        System.out.println("Plugin : " + displayInterface.test());
    }


}
