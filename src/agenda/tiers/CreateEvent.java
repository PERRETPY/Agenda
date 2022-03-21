package agenda.tiers;

import java.awt.Font;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import agenda.application.interfaces.CreateInterface;
import agenda.models.Event;

public class CreateEvent implements CreateInterface{

    private String strPathToDataEvent = "inputData/Event.csv";
    
	@Override
	public Event createNewEvent() {
		// TODO Auto-generated method stub
		
		
		return null;
	}
	public Box createForm() {
		Box contentBox = new Box(BoxLayout.Y_AXIS);
		JLabel title = new JLabel("Ajout d'un nouveau evenement", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(10, 250, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		//title.setSize(300, 30); 
		//title.setLocation(300, 30); 
		contentBox.add(title); 
		
		JLabel titre = new JLabel("Titre"); 
		titre.setFont(new Font("Arial", Font.PLAIN, 18)); 
		//titre.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentBox.add(titre); 

		JTextField titreTextField = new JTextField(); 
		contentBox.add(titreTextField); 
		
		JLabel date = new JLabel("Date"); 
		date.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(date); 

		JTextField dateTextField = new JTextField(); 
		dateTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(dateTextField); 
		
		JLabel duree = new JLabel("Durée"); 
		duree.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(duree); 

		JTextField dureeTextField = new JTextField(); 
		dureeTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(dureeTextField); 
		
		JLabel description = new JLabel("Description"); 
		description.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(description); 

		JTextField descriptionTextField = new JTextField(); 
		descriptionTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(descriptionTextField); 
		
		JLabel type = new JLabel("Type"); 
		type.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(type); 

		JTextField typeTextField = new JTextField(); 
		typeTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(typeTextField); 

		JLabel proprietaire = new JLabel("Id Propriétaire"); 
		proprietaire.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(proprietaire); 

		JTextField proprietaireTextField = new JTextField(); 
		proprietaireTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(proprietaireTextField); 
		
		JLabel invite = new JLabel("Id Invité"); 
		invite.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentBox.add(invite); 

		JTextField inviteTextField = new JTextField(); 
		inviteTextField.setFont(new Font("Arial", Font.PLAIN, 15)); 
		contentBox.add(inviteTextField); 
		
		JButton Enregistrer = new JButton("Enregistrer"); 
		Enregistrer.setFont(new Font("Arial", Font.PLAIN, 15));
		
		Enregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		String Errors = "";
            		String titreInput = titreTextField.getText();
            		String dateInput = dateTextField.getText();
            		if (!isValidDate(dateInput)) {
            			Errors +="La date doit être formaté selon le modèle suivant : dd/MM/yyyy HH:mm \n";
            		}
            		String dureeInput = dureeTextField.getText();
            		if (!isNumeric(dureeInput)) {
            			Errors+="La durée doit etre un nombre entier \n";
            		}
            		String descriptionInput = descriptionTextField.getText();
            		String typeInput = typeTextField.getText();
            		String proprietaireInput = proprietaireTextField.getText();
            		if (!isNumeric(proprietaireInput)) {
            			Errors+="L'id du proprietaire doit etre un nombre entier \n";
            		}
            		String inviteInput = inviteTextField.getText();
            		if (!isNumeric(inviteInput)) {
            			Errors+="L'id de l'invité doit etre un nombre entier \n";
            		}
            		
            		if(!Errors.isEmpty()) {
            			JOptionPane.showMessageDialog(contentBox,
            					Errors.toString(),
            				    "Données incorrects",
            				    JOptionPane.WARNING_MESSAGE);
            		} else {
            			try {
                    		String csvEvent ="1;"+titreInput+";"+dateInput+";"+dureeInput+";"
                    				+descriptionInput+";"+typeInput+";"+proprietaireTextField.getText()+";"+inviteTextField.getText();
                            FileWriter fileEvent = new FileWriter(strPathToDataEvent, true);
                            fileEvent.write("\n");
                            fileEvent.write(csvEvent);
                            fileEvent.flush();
                            
                            titreTextField.setText("");
                            dateTextField.setText("");
                            dureeTextField.setText("");
                            descriptionTextField.setText("");
                            typeTextField.setText("");
                            proprietaireTextField.setText("");
                            inviteTextField.setText("");
                            JOptionPane.showMessageDialog(contentBox,
                            	    "Votre événement a été crée avec succès !");
                            
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
            		}
            	
            }
		});
		
		contentBox.add(Enregistrer); 
		
		
		return contentBox;
		
	}
	
	public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

}
