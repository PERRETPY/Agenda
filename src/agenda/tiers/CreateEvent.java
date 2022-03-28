package agenda.tiers;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import agenda.models.DefaultEnumeration;
import agenda.models.Evenement;


public class CreateEvent implements CreateInterface{

    private String strPathToDataEvent = "inputData/Evenement.csv";
    private List<Evenement> eventList;
    Box contentBox;
    JTextField jourTextField,heureDebutTextField,heureFinTextField,titreTextField,descriptionTextField,
    organisateurTextField,participantTextField,lieuTextField,commentaireTextField;
    JComboBox typeComboBox,statutComboBox,prioriteComboBox;
    String jourInput,heureDebutInput,heureFinInput,titreInput,typeInput,descriptionInput,organisateurInput,participantInput,
    lieuInput,statutInput,prioriteInput,commentaireInput,date_creation;

	public Box createForm() {
		this.eventList = FrameWindow.getAllEventList();
		this.contentBox = new Box(BoxLayout.Y_AXIS);
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(13, 2));
		contentPanel.setBorder(new EmptyBorder(0,10,10,10));
		
		JLabel title = new JLabel("Ajout d'un nouveau evenement", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(0, 250, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		this.contentBox.add(title); 
		
		JLabel jour = new JLabel("Jour *"); 
		jour.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(jour); 
		
		this.jourTextField = new JTextField(); 
		contentPanel.add(this.jourTextField); 
		
		JLabel heureDebut = new JLabel("Heure debut *"); 
		heureDebut.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(heureDebut); 
		
		this.heureDebutTextField = new JTextField();  
		contentPanel.add(this.heureDebutTextField); 
		
		JLabel heureFin = new JLabel("Heure fin *"); 
		heureFin.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(heureFin); 
		
		this.heureFinTextField = new JTextField(); 
		contentPanel.add(this.heureFinTextField); 
		
		
		JLabel titre = new JLabel("Titre *"); 
		titre.setFont(new Font("Arial", Font.PLAIN, 18)); 
		//titre.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPanel.add(titre); 

		this.titreTextField = new JTextField(); 
		contentPanel.add(this.titreTextField); 
		
		JLabel type = new JLabel("Type"); 
		type.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(type); 

		this.typeComboBox = new JComboBox(DefaultEnumeration.getType());
		contentPanel.add(this.typeComboBox); 
		
		JLabel description = new JLabel("Description"); 
		description.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(description); 

		this.descriptionTextField = new JTextField(); 
		contentPanel.add(this.descriptionTextField); 
		
		
		JLabel organisateur = new JLabel("Organisateur(s)"); 
		organisateur.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(organisateur); 

		this.organisateurTextField = new JTextField(); 
		contentPanel.add(this.organisateurTextField); 
		
		JLabel participant = new JLabel("Participant(s)"); 
		participant.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(participant); 

		this.participantTextField = new JTextField(); 
		contentPanel.add(this.participantTextField); 
		
		JLabel lieu = new JLabel("Lieu"); 
		lieu.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(lieu); 

		this.lieuTextField = new JTextField(); 
		contentPanel.add(this.lieuTextField); 
		
		JLabel statut = new JLabel("Statut"); 
		statut.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(statut); 

		this.statutComboBox = new JComboBox(DefaultEnumeration.getStatut()); 
		contentPanel.add(this.statutComboBox); 
		
		JLabel priorite = new JLabel("Priorité"); 
		priorite.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(priorite); 

		this.prioriteComboBox = new JComboBox(DefaultEnumeration.getPriorite()); 
		contentPanel.add(this.prioriteComboBox); 
		
		
		JLabel commentaire = new JLabel("Commentaire"); 
		commentaire.setFont(new Font("Arial", Font.PLAIN, 20)); 
		contentPanel.add(commentaire); 

		this.commentaireTextField = new JTextField(); 
		contentPanel.add(this.commentaireTextField); 
		
		
		JButton Enregistrer = new JButton("Enregistrer"); 
		Enregistrer.setFont(new Font("Arial", Font.PLAIN, 15));
		
		
		Enregistrer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		
            	getValueInput();
            	saveValueInput();	
            		
            	
            }
		});
		
		
		JScrollPane scrollPane = new  JScrollPane(contentPanel);
 		scrollPane.setBounds(20, 150, 600, 400);
 		this.contentBox.add(scrollPane);
 		this.contentBox.add(Enregistrer); 
		
		
		
		return this.contentBox;
		
	}
	
	private void getValueInput() {
		this.jourInput = jourTextField.getText();
		this.heureDebutInput = heureDebutTextField.getText();
		this.heureFinInput = heureFinTextField.getText();
		this.titreInput = titreTextField.getText();
		this.typeInput = (String) typeComboBox.getSelectedItem();
		this.descriptionInput = descriptionTextField.getText();
		this.organisateurInput = organisateurTextField.getText();
		this.participantInput = participantTextField.getText();
		this.lieuInput = lieuTextField.getText();
		this.statutInput = (String) statutComboBox.getSelectedItem();
		this.prioriteInput = (String) prioriteComboBox.getSelectedItem();
		this.commentaireInput = commentaireTextField.getText();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String date_creation = dtf.format(now);
	}
	private void saveValueInput() {
		Evenement new_event = new Evenement(jourInput,heureDebutInput,heureFinInput,titreInput,typeInput,descriptionInput,organisateurInput,
				participantInput,lieuInput,statutInput,prioriteInput,commentaireInput,date_creation,date_creation); 
		
		
		
		String Errors = new_event.getErrors();
		if(!Errors.isEmpty()) {
			JOptionPane.showMessageDialog(this.contentBox,
					Errors.toString(),
				    "Données incorrects",
				    JOptionPane.WARNING_MESSAGE);
		} else {
			this.eventList.add(new_event);
            reinitialiseInput();
            JOptionPane.showMessageDialog(this.contentBox,
                	    "Votre événement a été crée avec succès !");
          
		}
	}
	private void reinitialiseInput() {
		this.jourTextField.setText("");
		this.heureDebutTextField.setText("");
		this.heureFinTextField.setText("");
		this.titreTextField.setText("");
		this.typeComboBox.setSelectedIndex(0);
		this.descriptionTextField.setText("");
		this.organisateurTextField.setText("");
		this.participantTextField.setText("");
		this.lieuTextField.setText("");
		this.statutComboBox.setSelectedIndex(0);
		this.prioriteComboBox.setSelectedIndex(0);
		this.commentaireTextField.setText("");
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		FrameWindow.refreshComponent(createForm());
	}

}
