package agenda.plateforme;

import agenda.plateforme.models.Descripteur;
import agenda.plateforme.listener.Status;
import agenda.application.FrameWindow;
import agenda.moniteur.Moniteur;
import agenda.plateforme.listener.Observer;
import agenda.plateforme.listener.Subject;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PluginLoader implements Subject, Observer {
    private final String CONF_PATH = "config.yml";

    /** The descripteurs. */
    private HashMap<String, Descripteur> descripteurs;


    /** The Constant INSTANCE. */
    private static final PluginLoader INSTANCE = new PluginLoader();
    List<Observer> suscribers;

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
    	this.suscribers = new ArrayList<Observer>();
    	
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
        loader.checkRequirement();
        // Lancement des plugins autorun
        loader.loadAutoRun();
        
        loader.notifyInit();

    }

    /**
     * Parcours les descripteurs afin de lancer les plugins flaggÃ©s en autoRun dans
     * des threads.
     */
    private void loadAutoRun() {
       //on crée un tableau pour stocker les descripteurs des plugins en autorun
    	List <Descripteur> DescripteurAutoRunList = new ArrayList <Descripteur>();
    	 
     // Parcours la liste des descripteurs chargés
        for (Descripteur descripteurLoaded : getLoadedPlugin().values()) {
            // On regarde les plugins flaggÃ©s autorun
        	
            if (descripteurLoaded.isAutoRun()) {
            	
                // on recupere l'instance d'un plugin dans un thread separé
            	loadPlugin(descripteurLoaded);
            }
        }

    }
    
    private void checkRequirement() {
    	Collection <Descripteur> DescripteurLoadedList = getLoadedPlugin().values();
    	
    	int nbError = 0;
        //on parcour la liste des plugin chargés
        for (Descripteur descripteurLoaded : DescripteurLoadedList) {
        	
        	if (descripteurLoaded.getRequirements() != null) {
        		
        		// on recupère la liste de descripteurs des plugins requis
        		Collection <Descripteur> DescripteurRequisList = getDescripteursPluginRequis(descripteurLoaded).values();
        		descripteurLoaded.setError(false);
        		//on parcoure la liste
                for (Descripteur descripteurRequis : DescripteurRequisList) {
                	
                	boolean found =false;
                	if (descripteurRequis != null) {
                		for (Descripteur d :DescripteurLoadedList) {
                    		if (d.getClassName().equals(descripteurRequis.getClassName())) {
                    			found = true;
                    		}
                    	}
                	}
                	
                	
                	if (!found) {
                		// si des plugins requis n'est pas dans la liste des plugins chargés, on enregistre une erreur et on annule le chargement du plugin qui en depend
                		descripteurLoaded.setLoaded(false);
                		descripteurLoaded.setError(true);
                		//descripteurLoaded.setMessage(descripteurLoaded.getMessage() + "Echec du chargement ! Le plugin "+descripteurRequis.getName()+" est requis \n");
                		if (descripteurRequis != null) {
                			notifySubscribers(descripteurLoaded.getName(),"Erreur","Echec du chargement ! Le plugin "+descripteurRequis.getName()+" est requis");
                		} else {
                			notifySubscribers(descripteurLoaded.getName(),"Erreur","Echec du chargement ! L'un des plugins requis est inconnus");
                		}
                		
                	}
                }
             // s"il y a un erreur on incremente le compteur des erreurs
                if (descripteurLoaded.isError()) {
                	nbError++;
                
                	INSTANCE.descripteurs.put(descripteurLoaded.getName(), descripteurLoaded);
                } 
        	}
        }
        
        // certains plugins sont actuellement ok, peuvent dependre de plugin en erreur, 
        // du coups on relance la verification jusqu'à ce qu'il n'y est plus de nouvelles erreurs
        if(nbError>0) {
        	checkRequirement();
        }
    }

    // recupere l'instance d'un plugin dans un thread separé
    public void loadPlugin(Descripteur descripteur) {
        try {
            Thread t = new Thread((Runnable) this.recupererIntancePlugin(descripteur));
            t.start();
        } catch (SecurityException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Récupère le descripteur de tous les plugins se situant dans le fichier de conf
     *
     */
    public void chargerDescripteurs() {
        // Initialisation des descripteurs
        this.descripteurs = new HashMap<String, Descripteur>();

        try {
            // RÃ©cupÃ©ration du fichier de conf
            Yaml yaml = new Yaml();

            HashMap configMap;

            InputStream inputStream = new FileInputStream(CONF_PATH);

            configMap = (HashMap) yaml.load(inputStream);

            // Parcours du fichier de conf
            Set<Integer> keys = configMap.keySet();
            for (Integer key : keys) {
                HashMap pluginMap = (HashMap) configMap.get(key);
                // Valorisation du descripteur
                Descripteur descripteur = new Descripteur();
                descripteur.setName((String) pluginMap.get("nom"));
                descripteur.setClassName((String) pluginMap.get("nomClasse"));
                descripteur.setInterfaceImpl((String) pluginMap.get("interface"));
                descripteur.setAutoRun(pluginMap.get("autorun") != null? (Boolean) pluginMap.get("autorun"): false);
                descripteur.setDefaultPlugin(pluginMap.get("defaultPlugin") != null? (Boolean) pluginMap.get("defaultPlugin"): false);
                descripteur.setUnique(pluginMap.get("unique") != null? (Boolean) pluginMap.get("unique"): false);
                
                descripteur.setPosition((String) pluginMap.get("position"));
                descripteur.setPluginIntegrable((List<String>) pluginMap.get("pluginIntegrable"));
                List<String> reqs = (List<String>) pluginMap.get("requirements");
                if (reqs == null || !reqs.isEmpty()) {
                    descripteur.setRequirements(reqs);
                }
               
                // Gestion des dÃ©pendences : valorisation du parent
                if (configMap.get("dependances") != null || pluginMap.get("dependances") != "") {
                    descripteur.setDependency((String) pluginMap.get("dependances"));
                }
                // Ajout Ã  la liste des descripteurs
                this.descripteurs.put(descripteur.getName(), descripteur);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Récupèration des plugins integrables au plugin passée en paramètre
     *
     * @param pluginName the dependency
     * @return the descripteurs for
     */
    public static HashMap<String, Descripteur> getDescripteursPluginIntegrable(String pluginName, boolean isLoaded) {
    	// initialise le tableau à retourner
        HashMap<String, Descripteur> descripteurs = new HashMap<String, Descripteur>();
        Collection <Descripteur> descripteursList = new ArrayList<Descripteur>();
        if (isLoaded) {
        	descripteursList = INSTANCE.getLoadedPlugin().values();
        } else {
        	descripteursList = INSTANCE.getDescripteurs().values();
        }
        
        // parcours la liste des descripteurs
        for (Descripteur d : descripteursList) {
        	
            // On recupère tous les plugins integrables au plugin entrée en paramètre
        	boolean found = false;
            if (d.getPluginIntegrable() != null ) { //&& Arrays.asList(d.getPluginIntegrable()).contains(pluginName)
            	
            	for (String name : d.getPluginIntegrable()) {
            		
            		if (name.equals(pluginName)) {
            			found =true;
            			break;
            		}
            	}
                
            }
            if (found) {
            	descripteurs.put(d.getName(), d);
            }
        }
        return descripteurs;
    }

    // Recupere la liste des plugins qui sont requis par le plugin passé en paramètre
    public HashMap<String, Descripteur> getDescripteursPluginRequis(Descripteur plugin) {
    	// recuperes le nom des plugins requis
        List<String> requirementList = plugin.getRequirements();
        // initialise le tableau à retourner
        HashMap<String, Descripteur> requirements = new HashMap<String, Descripteur>();
        // recupère la liste des descriptions
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();

        int i=0;
        // parcours la liste des noms de plugins requis
        while(i<requirementList.size()) {
        	// recupère la description du plugin par son nom
            Descripteur descripteur = descripteurList.get(requirementList.get(i));

            try {
            	//ajoute la description du plugin dans le tableau 
            	if (descripteur != null) {
            		requirements.put(descripteur.getName(), descripteur);
            	} else {
            		requirements.put(requirementList.get(i),null);
            	}
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            i++;
        }
        // retourne le tableau
        return requirements;
    }

    /**
     * Méthode de récupération d'instance du plugin passé en paramètre
     *
     * @param descripteur the descripteur du plugin
     * @return the object
     */
    public static Object recupererIntancePlugin(Descripteur descripteur) {
    	
    	
        try {
            // Instanciation de la classe avec le contructeur, avec ou sans paramÃ¨tres
        	Class classe = Class.forName(descripteur.getClassName());
            Constructor constructor = classe.getConstructor();
            //INSTANCE.notifySubscribers(descripteur.getName(),"Instancer","L'insance du plugin a été recupéré avec succès");
            return constructor.newInstance();

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException
                | SecurityException | IllegalArgumentException | InvocationTargetException e) {
        	//INSTANCE.notifySubscribers(descripteur.getName(),"Erreur","Une erreur s'est produite lors de la récuperation de l'instance");
            e.printStackTrace();
        }
        return null;
    }

    // recupère les plugins chargés en fonction de l'interface passé en paramètre
    
    public static Object getLoadPluginByInterface(Class<?> interfaceSearch) {
    	//recupération de la liste des plugin charger
        HashMap<String, Descripteur> pluginLoad = INSTANCE.getLoadedPlugin();
        //recupération de la description des plugins appartenants à cette interface
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
                //INSTANCE.notifySubscribers(loadPluginDescriptor.getName(),"Instancier","Le plugin a été instancier avec succès");

            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //INSTANCE.notifySubscribers(loadPluginDescriptor.getName(),"Erreur","Erreur lors de l'instanciation");
            }

            return result;


        }else {
            return null;
        }
    }
    

    public static void loadPluginInList(String pluginName) {
    	// recupère le plugin dans la liste des descripteurs
    	Descripteur pluginNeedToBeLoad = INSTANCE.descripteurs.get(pluginName);
        
        if (pluginNeedToBeLoad != null) {
        	// recupère le nom de l'interface
            String interfaceName = pluginNeedToBeLoad.getInterfaceImpl();
            
            // on desactive les plugins dejà chargé appartenant à cette interface si celui-ci doit etre unique
            System.out.println("isUnique");
            System.out.println(pluginNeedToBeLoad.isUnique());
            if (pluginNeedToBeLoad.isUnique()) {
            	unloadPluginByInterface(interfaceName);
            }
           
            // on charge le plugin passé en parametre
            pluginNeedToBeLoad.setLoaded(true);
            //on met à jour la liste de nos descripteurs
            INSTANCE.descripteurs.put(pluginName, pluginNeedToBeLoad);
            INSTANCE.notifySubscribers(pluginNeedToBeLoad.getName(),"Charger","Le plugin est chargé");
        }
        
    }

 // on desactive les plugins dejà chargé appartenant à l'interface passée en paramètre
    public static void unloadPluginByInterface(String interfaceName) {
    	// recupère la liste des descripteurs
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        // parcours la liste
        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
        	
            if (stringDescripteurEntry.getValue().getInterfaceImpl()!= null && stringDescripteurEntry.getValue().getInterfaceImpl().equals(interfaceName)
                && stringDescripteurEntry.getValue().isLoaded()) {
            	// on desactive les plugins dejà chargé appartenant à l'interface passé en parametre
                Descripteur dsc = stringDescripteurEntry.getValue();
                dsc.setLoaded(false);
                // on met à jour la liste de nos descripteurs
                descripteurList.put(dsc.getName(), dsc);
                INSTANCE.notifySubscribers(dsc.getName(),"Disponible","Le plugin a été stoppé suite au chargement d'un plugin unique");
            }
        }
    }
    
    // on desactive les plugins dejà chargé appartenant au plugin passée en paramètre
    public void unloadPluginByName(String pluginName) {
    	// recupère la liste des descripteurs
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        // parcours la liste
        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
        	
            if (stringDescripteurEntry.getValue().getName().equals(pluginName)
                && stringDescripteurEntry.getValue().isLoaded()) {
            	// on desactive le plugin dejà chargé si ce n'est pas un plugin par defaut
            	if (stringDescripteurEntry.getValue().isDefaultPlugin()) {
            		// error 
            		this.notifySubscribers(stringDescripteurEntry.getValue().getName(),"Charger","Echec de l'arret ! Les plugins par defaut ne peuvent pas être stopper");
            	} else {
            		Descripteur dsc = stringDescripteurEntry.getValue();
                    dsc.setLoaded(false);
                    // on met à jour la liste de nos descripteurs
                    descripteurList.put(dsc.getName(), dsc);
                    this.notifySubscribers(stringDescripteurEntry.getValue().getName(),"Disponible","Le plugin a été stoppé");
            	}
            	break;
                
            }
        }
    }

    public static void setDefaultPluginLoaded() {
    	//recupération de la description de tous les plugins
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        if(descripteurList != null) {
        	 //parcours la liste des descripteurs
            for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
                if (stringDescripteurEntry.getValue().isDefaultPlugin()) {
                	// on charge le plugin en desactivant ceux provenant de la même interface dans le cas ou le plugin est unique
                	
                    loadPluginInList(stringDescripteurEntry.getKey());
                }
            }
           
        }

    }

  //recupération de la description des plugins appartenants à l'interface passé en paramètre
    public static HashMap<String, Descripteur> getDescripteurListByInterface(Class<?> interfaceSearch) {
    	//recupération de la description de tous les plugins
        HashMap<String, Descripteur> descripteurList = INSTANCE.getDescripteurs();
        //initialisation du tableau à retourner
        HashMap<String, Descripteur> descripteurListByInterface = new HashMap<String, Descripteur>();

        //recuperation du nom de l'interface
        String interfaceName = interfaceSearch.getName();
        
        //parcours la liste des descripteurs
        for (Map.Entry<String, Descripteur> stringDescripteurEntry : descripteurList.entrySet()) {
            Descripteur descripteur = stringDescripteurEntry.getValue();
            if (descripteur.getInterfaceImpl() != null && descripteur.getInterfaceImpl().equals(interfaceName)) {
            	// ajoute la description du plugin lorsque celui(ci implemente l'interface rechergché
                descripteurListByInterface.put(descripteur.getName(), descripteur);
            }
        }
        return descripteurListByInterface;
    }

	@Override
	public void addSubscriber(Observer observer) {
		if(this.suscribers==null) {
			this.suscribers = new ArrayList<Observer>();
		}
		this.suscribers.add(observer);
	}

	@Override
	public void removeSubscriber(Observer observer) {
		if (this.suscribers.contains(observer)) {
			this.suscribers.remove(observer);
		}
	}

	@Override
	public void notifySubscribers(String name, String status, String message) {
		for(Observer suscriber : this.suscribers) {
           suscriber.update(name,status,message);
        }
	}
	
	/**
	 * Notifie les plugins disponibles dans la config non chargÃ©s Ã  l'initialisation de la plateforme (les chargÃ©s ont dÃ©jÃ  Ã©tÃ© notifiÃ©s dans autorun)
	 */
	private void notifyInit() {
		for (Descripteur d : INSTANCE.getDescripteurs().values()){
			if(!d.isLoaded()) {
				notifySubscribers(d.getName(),"Disponible","Le plugin est disponible dans la conf");
			}
			else {
				if(!d.getName().equals("Moniteur")) {
					notifySubscribers(d.getName(),"Charger","Le plugin est chargé");
				}
					

			}
		}
		//Moniteur.getInstance().addSubscriber(INSTANCE);
		
	}

	@Override
	public void update(String name, String status, String message) {
		// TODO Auto-generated method stub
		System.out.println(name);
		System.out.println(status);
		if (status.equals("charger")) {
			PluginLoader.loadPluginInList(name);
			
		} else if (status.equals("stopper")){
			unloadPluginByName(name);
			
		}
		this.checkRequirement();
		FrameWindow.refreshHeaderButton();
	}

}
