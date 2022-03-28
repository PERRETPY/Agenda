package agenda.application;

import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;

import java.util.HashMap;

public class Agenda implements Runnable {



    private HashMap<String, Descripteur> descripteursPlugins;

    private final static PluginLoader LOADER = PluginLoader.getInstance();

    private static final String APP_NAME = "RDV Smarts";


    public Agenda() {

        //descripteursPlugins = PluginLoader.getDescripteursFor(APP_NAME);
    }

    @Override
    public void run() {

        FrameWindow frameWindow = new FrameWindow();
        Thread t = new Thread((Runnable) frameWindow);
        t.start();

        System.out.println("Hello world!");

    }


}
