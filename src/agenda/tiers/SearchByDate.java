package agenda.tiers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

import agenda.application.FrameWindow;
import agenda.application.interfaces.IntegrableInterface;
import agenda.application.interfaces.ValidateurInterface;
import agenda.models.Evenement;

public class SearchByDate implements IntegrableInterface {

	

	@Override
	public Object getComponet() {
		List<Evenement> eventList = FrameWindow.getAllEventList();
		JLabel label = new JLabel("Rechercher par date :");
        final JTextField jourTextField = new JTextField(20);
        JButton search = new JButton("search");
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String jour = jourTextField.getText();
            	System.out.println(jour);
            	if (!ValidateurInterface.isValidDate(jour)) {
            		JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
            				"Le jour doit être formaté selon le modèle suivant : dd/MM/yyyy" ,
        				    "Données incorrects",
        				    JOptionPane.WARNING_MESSAGE);
        		}else {
        			
        			List<Evenement> eventListFiltred = new ArrayList<Evenement>();
        			for(Evenement event : eventList) {
        				System.out.println(event.getJour()+"-----"+jour+"-----"+event.getJour().equals(jour));
        				System.out.println(jour);
        				if(event.getJour().equals(jour)) {
        					eventListFiltred.add(event);
        					
        				}
        			}
        			DisplayCardEvent.showCard(eventListFiltred);
        		}
            }
        });
        
        JButton annuler = new JButton("annuler");
        annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DisplayCardEvent.showCard(eventList);
            }
        });
        JPanel bloc = new JPanel();
        bloc.add(label);
        bloc.add(jourTextField);
        bloc.add(search);
		
 		return bloc;
	}
}
