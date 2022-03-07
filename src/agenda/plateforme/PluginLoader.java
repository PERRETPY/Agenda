package agenda.plateforme;

import agenda.plateforme.models.Descripteur;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PluginLoader {
    private final String CONF_PATH = "config.yml";

    /** The descripteurs. */
    private HashMap<String, Descripteur> descripteurs;


    /** The Constant INSTANCE. */
    private static final PluginLoader INSTANCE = new PluginLoader();

    public HashMap<String, Descripteur> getLoadedPlugin() {
        HashMap<String, Descripteur> loadedPlugin = new HashMap<String, Descripteur>();
        HashMap<String, Descripteur> Descripteur = INSTANCE.getDescripteurs();

        for (Map.Entry<String, Descripteur> stringDescripteurEntry : Descripteur.entrySet()) {
            if(stringDescripteurEntry.getValue().isLoaded()) {
                loadedPlugin.put(stringDescripteurEntry.getKey(), stringDescripteurEntry.getValue());
            }
        }

        return loadedPlugin;
    }

    /**
     * Instantiates a new plugin loader.
     */
    public PluginLoader() {
    }

    /**
     * Gets the single instance of PluginLoader.
     *
     * @return single instance of PluginLoader
     */
    public static final PluginLoader getInstance() {
        return INSTANCE;
    }

    /**
     * Gets the descripteurs.
     *
     * @return the descripteurs
     */
    public HashMap<String, Descripteur> getDescripteurs() {
        return descripteurs;
    }

    /**
     * Sets the descripteurs.
     *
     * @param descripteurs the descripteurs
     */
    public void setDescripteurs(HashMap<String, Descripteur> descripteurs) {
        this.descripteurs = descripteurs;
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        PluginLoader loader = PluginLoader.getInstance();

        // Chargement des descripteurs
        loader.chargerDescripteurs();

        setDefaultPluginLoaded();

        // Lancement des plugins autorun
        loader.loadAutoRun();
        // loader.notifyInit();

    }

    /**
     * Parcours les descripteurs afin de lancer les plugins flaggés en autoRun dans
     * des threads.
     */
    private void loadAutoRun() {
        // Parcours des descripteurs chargés
        for (Descripteur d : descripteurs.values()) {
            // On regarde les plugins flaggés autorun
            if (d.isAutoRun()) {
                /*if (d.getRequirements() != null) {
                    for (Descripteur descripteurRequire : getRequirementDescripteur(d).values()) {
                        loadPlugin(descripteurRequire);
                    }

                }*/
                loadPlugin(d);
            }
        }
    }

    private void loadPlugin(Descripteur descripteur) {
        try {
            Thread t = new Thread((Runnable) this.recupererPlugin(descripteur, null));
            t.start();
        } catch (SecurityException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Méthode de chargement des descripteurs.
     *
     */
    public void chargerDescripteurs() {
        // Initialisation des descripteurs
        this.descripteurs = new HashMap<String, Descripteur>();

        try {
            // Récupération du fichier de conf
            Yaml yaml = new Yaml();

            HashMap configMap;

            InputStream inputStream = new FileInputStream(CONF_PATH);

            configMap = yaml.load(inputStream);

            // Parcours du fichier de conf
            Set<Integer> keys = configMap.keySet();
            for (Integer key : keys) {
                HashMap pluginMap = (HashMap) configMap.get(key);
                // Valorisation du descripteur
                Descripteur descripteur = new Descripteur();
                descripteur.setName((String) pluginMap.get("nom"));
                descripteur.setClassName((String) pluginMap.get("nomClasse"));
                descripteur.setInterfaceImpl((String) pluginMap.get("interface"));
                descripteur.setAutoRun((Boolean) pluginMap.get("autorun"));
                descripteur.setDefaultPlugin((Boolean) pluginMap.get("defaultPlugin"));
                List<String> reqs = (List<String>) pluginMap.get("requirements");
                if (reqs == null || !reqs.isEmpty()) {
                    descripteur.setRequirements(reqs);
                }
                // Gestion arguments constructeur par défaut
                List<String> args = (List<String>) pluginMap.get("params");
                if (args == null || !args.isEmpty()) {
                    descripteur.addArgs(args);
                }
                // Gestion des dépendences : valorisation du parent
                if (configMap.get("dependances") != null || pluginMap.get("dependances") != "") {
                    descripteur.setDependency((String) pluginMap.get("dependances"));
                }
                // Ajout à la liste des descripteurs
                this.descripteurs.put(descripteur.getName(), descripteur);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Récupération des plugins enfant du plugin entré en paramètre
     *
     * @param dependency the dependency
     * @return the descripteurs for
     */
    public static HashMap<String, Descripteur> getDescripteursFor(String dependency) {
        HashMap<String, Descripteur> descripteurs = new HashMap<String, Descripteur>();
        for (Descripteur d : INSTANCE.getDescripteurs().values()) {
            // On récupère tous les plugins enfant du plugin entré en paramètre
            if (d.getDependency() != null && d.getDependency().equals(dependency)) {
                descripteurs.put(d.getName(), d);
            }
        }
        return descripteurs;
    }

    public HashMap<String, Descripteur> getRequirementDescripteur(Descripteur plugin) {
        List<String> requirementList = plugin.getRequirements();
        HashMap<String, Descripteur> requirements = new HashMap<String, Descripteur>();
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();

        boolean found = false;
        int i=0;



        while(i<requirementList.size()) {

            Descripteur descripteur = descripteurList.get(requirementList.get(i));

            try {
                requirements.put(descripteur.getName(), descripteur);
            } catch (Exception e) {
                e.printStackTrace();
            }

            i++;
        }
        return requirements;
    }

    /**
     * Méthode de récupération d'instance du plugin passé en paramètre
     *
     * @param descripteur the descripteur du plugin
     * @param args        the args
     * @return the object
     */
    public static Object recupererPlugin(Descripteur descripteur, Object[] args) {
        PluginLoader instance = PluginLoader.getInstance();
        Class classe;
        Constructor constructor;
        Object plugin = null;
        try {
            // Instanciation de la classe avec le contructeur, avec ou sans paramètres
            classe = Class.forName(descripteur.getClassName());
            if (descripteur.getArgs() != null) {
                constructor = classe.getConstructor(descripteur.getArgs());
            } else {
                constructor = classe.getConstructor(null);
            }
            plugin = constructor.newInstance(args);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
                | SecurityException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return plugin;
    }

    public static Object getLoadPluginByInterface(Class<?> interfaceSearch) {
        HashMap<String, Descripteur> pluginLoad = INSTANCE.getLoadedPlugin();
        HashMap<String, Descripteur> descripteursByInterface = getDescripteurListByInterface(interfaceSearch);
        Descripteur loadPluginDescriptor = null;

        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteursByInterface.entrySet()) {
            Descripteur descripteur = stringDescripteurEntry.getValue();

            if(pluginLoad != null && pluginLoad.containsKey(descripteur.getName())) {
                loadPluginDescriptor = pluginLoad.get(descripteur.getName());
            }
        }

        if(loadPluginDescriptor != null) {
            Object result = null;

            Class<?> plClass;
            try {
                plClass = Class.forName(loadPluginDescriptor.getClassName());
                if (!interfaceSearch.isAssignableFrom(plClass)) {
                    return null;
                }
                result = plClass.getDeclaredConstructor().newInstance();

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return result;


        }else {
            return null;
        }
    }

    public static void loadPluginInList(String pluginName) {
        Descripteur pluginNeedToBeLoad = INSTANCE.descripteurs.get(pluginName);
        String interfaceName = pluginNeedToBeLoad.getInterfaceImpl();

        unloadPluginByInterface(interfaceName);


        if (pluginNeedToBeLoad != null) {
            pluginNeedToBeLoad.setLoaded(true);
            INSTANCE.descripteurs.put(pluginName, pluginNeedToBeLoad);
        }
    }

    public static void unloadPluginByInterface(String interfaceName) {
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
            if (stringDescripteurEntry.getValue().getInterfaceImpl().equals(interfaceName)
                && stringDescripteurEntry.getValue().isLoaded()) {
                Descripteur dsc = stringDescripteurEntry.getValue();
                dsc.setLoaded(false);
                descripteurList.put(dsc.getName(), dsc);
            }
        }
    }

    public static void setDefaultPluginLoaded() {
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        if(descripteurList != null) {
            for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
                if (stringDescripteurEntry.getValue().isDefaultPlugin()) {
                    loadPluginInList(stringDescripteurEntry.getKey());
                } else {
                }
            }
        }

    }

    public static HashMap<String, Descripteur> getDescripteurListByInterface(Class<?> interfaceSearch) {
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        HashMap<String, Descripteur> descripteurListByInterface = new HashMap<String, Descripteur>();

        String interfaceName = interfaceSearch.getName();

        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
            Descripteur descripteur = stringDescripteurEntry.getValue();
            if (descripteur.getInterfaceImpl() != null && descripteur.getInterfaceImpl().equals(interfaceName)) {
                descripteurListByInterface.put(descripteur.getName(), descripteur);
            }
        }
        return descripteurListByInterface;
    }

}
