package agenda.application.interfaces;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;
import agenda.tiers.DisplayTabEvent;

public interface SupportIntegrableInterface {

	public static final String name = "";
	
	public static HashMap<String, List<Component>> getExternalPluginComponent(String name) {
		// TODO Auto-generated method stub
		//footerBox.add((Component) component);
		
		HashMap<String, Descripteur> descripteurs = PluginLoader.getDescripteursPluginIntegrable(name,true);
		HashMap<String, List<Component>> externalComponent = new HashMap<String, List<Component>>();
		
		List<Component> header = new ArrayList<Component>();
		List<Component> footer = new ArrayList<Component>();
		List<Component> center = new ArrayList<Component>();
		 for (Descripteur d : descripteurs.values()) {
	            // On recupère tous les plugins integrables au plugin entrée en paramètre
			 	
			 	IntegrableInterface pluginExterne = (IntegrableInterface) PluginLoader.recupererIntancePlugin(d);
			 	Component componentPluginEterne = (Component) pluginExterne.getComponet();
			 	switch(d.getPosition()){

			        case "header": 
			        	header.add(componentPluginEterne);
			        case "footer":
			        	footer.add(componentPluginEterne);
			            break;
			        case "center":
			        	center.add(componentPluginEterne);
			            break;
			        default:
			        	d.isLoaded();
			            System.out.println("Position inconnu");
			            break;
			    }
	        }
		 externalComponent.put("header", header);
		 externalComponent.put("footer", footer);
		 externalComponent.put("center", center);
		 return externalComponent;
		
	}
}
