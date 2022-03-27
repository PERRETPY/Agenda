package agenda.tiers;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import agenda.application.FrameWindow;
import agenda.application.interfaces.CreateInterface;
import agenda.application.interfaces.OnClickInterface;
import agenda.models.Event;
import agenda.models.DefaultEnumeration;


public class CreateEvent implements CreateInterface{

    private String strPathToDataEvent = "inputData/Evenement.csv";
    

	public Box createForm() {
		Box contentBox = new Box(BoxLayout.Y_AXIS);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(13, 2));
		contentPanel.setBorder(new EmptyBorder(0,10,10,10));
		
		JLabel title = new JLabel("Ajout d'un nouveau evenement", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(0, 250, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		contentBox.add(title); 
		
		JLabel jour = new JLabel("Jour *"); 
		jour.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(jour); 
		
		JTextField jourTextField = new JTextField(); 
		jourTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(jourTextField); 
		
		JLabel heureDebut = new JLabel("Heure debut *"); 
		heureDebut.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(heureDebut); 
		
		JTextField heureDebutTextField = new JTextField(); 
		heureDebutTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(heureDebutTextField); 
		
		JLabel heureFin = new JLabel("Heure fin *"); 
		heureFin.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(heureFin); 
		
		JTextField heureFinTextField = new JTextField(); 
		heureFinTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(heureFinTextField); 
		
		
		JLabel titre = new JLabel("Titre *"); 
		titre.setFont(new Font("Arial", Font.PLAIN, 18)); 
		//titre.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.add(titre); 

		JTextField titreTextField = new JTextField(); 
		contentPanel.add(titreTextField); 
		
		JLabel type = new JLabel("Type"); 
		type.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(type); 

		JComboBox typeComboBox = new JComboBox(DefaultEnumeration.getType()); 
		typeComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(typeComboBox); 
		
		JLabel description = new JLabel("Description"); 
		description.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(description); 

		JTextField descriptionTextField = new JTextField(); 
		descriptionTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(descriptionTextField); 
		
		
		JLabel organisateur = new JLabel("Organisateur(s)"); 
		organisateur.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(organisateur); 

		JTextField organisateurTextField = new JTextField(); 
		organisateurTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(organisateurTextField); 
		
		JLabel participant = new JLabel("Participant(s)"); 
		participant.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(participant); 

		JTextField participantTextField = new JTextField(); 
		participantTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(participantTextField); 
		
		JLabel lieu = new JLabel("Lieu"); 
		lieu.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(lieu); 

		JTextField lieuTextField = new JTextField(); 
		lieuTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(lieuTextField); 
		
		JLabel statut = new JLabel("Statut"); 
		statut.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(statut); 

		JComboBox statutComboBox = new JComboBox(DefaultEnumeration.getStatut()); 
		statutComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(statutComboBox); 
		
		JLabel priorite = new JLabel("Priorité"); 
		priorite.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(priorite); 

		JComboBox prioriteComboBox = new JComboBox(DefaultEnumeration.getPriorite()); 
		prioriteComboBox.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(prioriteComboBox); 
		
		
		JLabel commentaire = new JLabel("Commentaire"); 
		commentaire.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(commentaire); 

		JTextField commentaireTextField = new JTextField(); 
		commentaireTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentPanel.add(commentaireTextField); 
		
		
		JButton Enregistrer = new JButton("Enregistrer"); 
		Enregistrer.setFont(new Font("Arial", Font.PLAIN, 15));
		
		
		Enregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		String Errors = "";
            		String jourInput = jourTextField.getText();
            		if (!isValidDate(jourInput)) {
            			Errors +="Le jour doit être formaté selon le modèle suivant : dd/MM/yyyy \n";
            		}
            		
            		String heureDebutInput = heureDebutTextField.getText();
            		if(!isValidHeure(heureDebutInput)) {
            			Errors +="L'heure de debut doit être formaté selon le modèle suivant : HH:mm \n";
            		}
            		String heureFinInput = heureFinTextField.getText();
            		if(!isValidHeure(heureFinInput)) {
            			Errors +="L'heure de fin doit être formaté selon le modèle suivant : HH:mm \n";
            		}
            		
            		String titreInput = titreTextField.getText();
            		if(titreInput.isEmpty()) {
            			Errors += "Le titre est obligatoire";
            		}
            		String typeInput = (String) typeComboBox.getSelectedItem();
            		
            		String descriptionInput = descriptionTextField.getText();
            		
            		String organisateurInput = organisateurTextField.getText();
            		
            		String participantInput = participantTextField.getText();
            		String lieuInput = lieuTextField.getText();
            		String statutInput = (String) statutComboBox.getSelectedItem();
            		String prioriteInput = (String) prioriteComboBox.getSelectedItem();
            		String commentaireInput = commentaireTextField.getText();
            		
            		if(!Errors.isEmpty()) {
            			JOptionPane.showMessageDialog(contentBox,
            					Errors.toString(),
            				    "Données incorrects",
            				    JOptionPane.WARNING_MESSAGE);
            		} else {
            			SimpleDateFormat date_creation = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            			// date_update = new Date() 
            			try {
                    		String csvEvent ="1;"+jourInput+";"+heureDebutInput+";"+heureFinInput+";"
                    				+titreInput+";"+typeInput+";"+descriptionInput+";"+organisateurInput+";"
                            		+participantInput+";"+lieuInput+";"+statutInput+";"+prioriteInput+";"
                                    +commentaireInput+";"+date_creation+";"+date_creation+";endRow";
                            FileWriter fileEvent = new FileWriter(strPathToDataEvent, true);
                            fileEvent.write("\n");
                            fileEvent.write(csvEvent);
                            fileEvent.flush();
                            
                            
                            jourTextField.setText("");
                            heureDebutTextField.setText("");
                            heureFinTextField.setText("");
                            titreTextField.setText("");
                          //typeComboBox.setText("");
                            descriptionTextField.setText("");
                            organisateurTextField.setText("");
                            participantTextField.setText("");
                            lieuTextField.setText("");
                            //statutComboBox
                            //prioriteComboBox
                            commentaireTextField.setText("");
                            
                            JOptionPane.showMessageDialog(contentBox,
                            	    "Votre événement a été crée avec succès !");
                            
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
            		}
            	
            }
		});
		
		
		JScrollPane scrollPane = new  JScrollPane(contentPanel);
 		scrollPane.setBounds(20, 150, 600, 400);
 		contentBox.add(scrollPane);
 		contentBox.add(Enregistrer); 
		
		
		
		return contentBox;
		
	}
	
	public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
	public static boolean isValidHeure(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		FrameWindow.refreshComponent(createForm());
	}

}
