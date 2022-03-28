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

public class DeleteItemDisplayCard  implements IntegrableInterface{

	@Override
	public Object getComponet() {
		// TODO Auto-generated method stub
		List<Evenement> eventList = FrameWindow.getAllEventList();
		JButton supprimer = new JButton("Supprimer"); 
 		supprimer.setFont(new Font("Arial", Font.PLAIN, 15));
		
 		supprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] selection = DisplayTabEvent.table.getSelectedRows();
            	int nbSuppression = 0;
            	if (selection.length == 0) {
             	   JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
        					"Vous devez d'abord selectionner les événements à supprimer",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                
         	   for (int i = 0; i < selection.length; i++) {
         		   System.out.println(selection[i]);
         		  eventList.remove(selection[i]-nbSuppression);
         		 nbSuppression++;
         	   }
         	   FrameWindow.refreshPage();
            }
 		});
		return supprimer;
	}

}
