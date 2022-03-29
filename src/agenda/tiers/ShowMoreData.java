package agenda.tiers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import agenda.application.FrameWindow;
import agenda.application.interfaces.IntegrableInterface;
import agenda.models.Evenement;

public class ShowMoreData implements IntegrableInterface {

	@Override
	public Object getComponet() {
		
		List<Evenement> eventList = FrameWindow.getAllEventList();
		JButton showMore = new JButton("Voir plus d'info"); 
		showMore.setFont(new Font("Arial", Font.PLAIN, 15));
		
		showMore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] selection = DisplayTabEvent.table.getSelectedRows();
                
                if (selection.length == 0) {
             	   JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
        					"Vous devez d'abord selectionner l'événement dont vous voulez afficher les informations supplémentaires",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                if (selection.length > 1) {
             	   JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
        					"Vous ne pouvez afficher les informations detaillés d'un seul événement à la fois",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                if (selection.length == 1) {
                	
                	int id = (int) DisplayTabEvent.table.getValueAt(selection[0], 0);
                	
                	Evenement event =eventList.get(id-1);
                	JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
                    	    "Jour : "+event.getJour()+"\n"
                    	    + "heureDebut : "+event.getHeureDebut()+"\n"
                    	    + "heureFin : "+event.getHeureFin()+"\n"
                    	    + "Titre : "+event.getTitre()+"\n"
                    	    + "Type : "+event.getType()+"\n"
                    	    + "Description : "+event.getDescription()+"\n"
                    	    + "Organisateur : "+event.getOrganisateur()+"\n"
                    	    + "Participant : "+event.getParticipant()+"\n"
                    	    + "Lieu : "+event.getLieu()+"\n"
                    	    + "Statut : "+event.getStatut()+"\n"
                    	    + "Priorite : "+event.getPriorite()+"\n"
                    	    + "Commentaire : "+event.getCommentaire()+"\n"
                    	    + "Date creation : "+event.getDateCreation()+"\n"
                    	    + "Date mis à jour : "+event.getDateUpdate()+"\n",
                    	    "Informations detaillés",
        				    JOptionPane.INFORMATION_MESSAGE
                			);
             	   
             	   
                }

         		
            }
            
 		});
		// TODO Auto-generated method stub
		
		return showMore;
	}

}
