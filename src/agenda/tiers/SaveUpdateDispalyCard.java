package agenda.tiers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import agenda.application.FrameWindow;
import agenda.application.interfaces.IntegrableInterface;
import agenda.models.Evenement;

public class SaveUpdateDispalyCard implements IntegrableInterface {

	List<Evenement> eventList;
	@Override
	public Object getComponet() {
		// TODO Auto-generated method stub
		eventList = FrameWindow.getAllEventList();
		JButton modifier = new JButton("Enregistrer modification"); 
 		modifier.setFont(new Font("Arial", Font.PLAIN, 15));
 		
 		modifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] selection = DisplayTabEvent.table.getSelectedRows();
                
                if (selection.length == 0) {
             	   JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
        					"Vous devez d'abord selectionner l'événement qui a été modifier",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                if (selection.length > 1) {
             	   JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
        					"Vous ne pouvez enregistrer qu'un événement à la fois",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                if (selection.length == 1) {
                	updateDataEvenement(selection[0]);
             	   
             	   
                }
            }
 		});
		return modifier;
	}
	
	public void updateDataEvenement(int index) {
		Evenement updated_event;
		try {
			updated_event = eventList.get(index).clone();
			
			updated_event.setTitre((String) DisplayTabEvent.table.getValueAt(index, 1));
			updated_event.setJour((String) DisplayTabEvent.table.getValueAt(index, 2));
			updated_event.setHeureDebut((String) DisplayTabEvent.table.getValueAt(index, 3));
			updated_event.setHeureFin((String) DisplayTabEvent.table.getValueAt(index, 4));
			updated_event.setDescription((String) DisplayTabEvent.table.getValueAt(index, 5));
			updated_event.setType((String) DisplayTabEvent.table.getValueAt(index, 6));
			updated_event.setOrganisateur((String) DisplayTabEvent.table.getValueAt(index, 7));
			
			
			String Errors = updated_event.getErrors();
			if(!Errors.isEmpty()) {
				JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
						Errors.toString(),
					    "Données incorrects",
					    JOptionPane.WARNING_MESSAGE);
			} else {
				eventList.set(index, updated_event);
	            
	            JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
	                	    "Votre modification a été enregistré avec succès !");
	          
			}
			
		} catch (CloneNotSupportedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}




}
