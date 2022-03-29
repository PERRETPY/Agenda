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

public class TrieData implements IntegrableInterface {


	@Override
	public Object getComponet() {
		
		//List<Evenement> eventList = FrameWindow.getAllEventList();
		JButton trier = new JButton("Trier"); 
 		trier.setFont(new Font("Arial", Font.PLAIN, 15));
		
 		trier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	DisplayTabEvent.table.setAutoCreateRowSorter(true);
         		//DefaultRowSorter has the sort() method
         		DefaultRowSorter sorter = ((DefaultRowSorter)DisplayTabEvent.table.getRowSorter()); 
         		ArrayList list = new ArrayList();
         		list.add( new RowSorter.SortKey(2, SortOrder.ASCENDING) );
         		sorter.setSortKeys(list);
         		sorter.sort();

         		JOptionPane.showMessageDialog(DisplayTabEvent.contentBox,
                	    "Le plugin de trie est actif ! Vous pouvez maintenant trier votre tableau en cliquant sur le titre des colonnes");
            }
            
 		});
		// TODO Auto-generated method stub
		
 		return trier;
	}
}
