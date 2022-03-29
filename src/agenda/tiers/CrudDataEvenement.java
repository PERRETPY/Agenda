package agenda.tiers;

import agenda.application.FrameWindow;
import agenda.application.interfaces.CrudDataInterface;
import agenda.models.Evenement;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CrudDataEvenement implements CrudDataInterface {

    private List<Evenement> eventList;
    private String strPathToDataEvent = "inputData/Evenement.csv";
    private String SEPARATOR = ";";

    public CrudDataEvenement() {
        this.eventList = new ArrayList<>();
    }



    @Override
    public List<Evenement> getAllEventList() {
        List<Object> objList = loadData(strPathToDataEvent, Evenement.class);
        this.eventList = new ArrayList<>();
        for(int i=0 ; i<objList.size() ; i++) {
            Object obj = objList.get(i);
            this.eventList.add((Evenement) obj);
        }
        return this.eventList;
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
                obj = createEvenement(attributes);

                fileDataList.add(obj);

                line = br.readLine();
            }
        } catch (IOException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return fileDataList;
    }

    private Object createEvenement(String[] attributes) {
       
    		Evenement event = new Evenement();
            
            event.setId(attributes[0]);
            event.setJour(attributes[1]);
            event.setHeureDebut(attributes[2]);
            event.setHeureFin(attributes[3]);
            event.setTitre(attributes[4]);
            event.setType(attributes[5]);
            event.setDescription(attributes[6]);
            event.setOrganisateur(attributes[7]);
            event.setParticipant(attributes[8]);
            event.setLieu(attributes[9]);
            event.setStatut(attributes[10]);
            event.setPriorite(attributes[11]);
            event.setCommentaire(attributes[12]);
            event.setDateCreation(attributes[13]);
            event.setDateUpdate(attributes[14]);


            return event;
        

    }
    
    private void addData(String eventCsv) {
        try {
            FileWriter bw1 = new FileWriter(strPathToDataEvent, true);
            bw1.write("\n");
            bw1.write(eventCsv);
            bw1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public String toCSV(Evenement event) {
		// replace ; par , pour chaque attribut modifiable via un textField pour eviter que le csv plante
    	if (event.getErrors().isEmpty()) {
    		return event.getJour()+";"+event.getHeureDebut()+";"+event.getHeureFin()+";"+event.getTitre()+";"
    				+event.getType()+";"+event.getDescription()+";"+event.getOrganisateur()+";"+event.getParticipant()+";"
    				+event.getLieu()+";"+event.getStatut()+";"+event.getPriorite()+";"+event.getCommentaire()+";"
    				+event.getDateCreation()+";"+event.getDateUpdate()+";end";
    	}
        return null;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.eventList = getAllEventList();
        FrameWindow.setAllEventList(this.eventList);
        FrameWindow.crudInstance = this;
	}


	@Override
	public void saveAllEventList() {
		// TODO Auto-generated method stub
		PrintWriter writer;
		try {
			//effacement des données precedentes
			writer = new PrintWriter(strPathToDataEvent);
			writer.print("id;jour;heureDebut;heureFin;titre;type;description;organisateur;participant;lieu;statut;priorite;commentaire;dateCreation;dateUpdate;endRow");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int i = 1;
		for(Evenement event : this.eventList) {
			String eventCsv = toCSV(event);
			if (eventCsv!=null) {
				// enregistrement de l'evenement
				addData(i+";"+eventCsv);
				i++;
			}
		}
	}




}
