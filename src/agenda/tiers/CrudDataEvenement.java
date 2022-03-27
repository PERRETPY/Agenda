package agenda.tiers;

import agenda.application.interfaces.CrudDataInterface;
import agenda.models.Evenement;

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
    
    private void addData(String filename, Evenement obj) {
        try {
            FileWriter bw1 = new FileWriter(filename, true);
            bw1.write("\n");
            bw1.write(obj.toCSV());
            bw1.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
