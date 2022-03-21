package agenda.tiers;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import agenda.application.interfaces.DisplayInterface;
import agenda.models.Event;
import agenda.tiers.component.TabHeader;


public class DisplayTabEvent implements DisplayInterface{

	private JList<Event> eventJList;
	@Override
	public String test() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Box displayEventList(List<Event> eventList) {
		// TODO Auto-generated method stub
		Box contentBox = new Box(BoxLayout.X_AXIS);
		Event [] rdvArray = new Event[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) 
		 rdvArray[i] = eventList.get(i);

		 		 
 		
 		String[] columnNames = new String[] {"N","TITRE", "DATE", "DUREE", "DESCRIPTION", "TYPE","PROPRIETAIRE"};
 		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
 		for (int i = 0; i < eventList.size(); i++) {
 			 model.addRow( new Object[]{ 
 					 1+i, 
 					eventList.get(i).getTitleEvent(),
 					eventList.get(i).getDateEvent(),
 					eventList.get(i).getDurationEvent(),
 					eventList.get(i).getDescriptionEvent(),
 					eventList.get(i).getTypeEvent(),
 					eventList.get(i).getOwner()} );
 		}
 		JTable table = new JTable(model);
        
 		table.getColumnModel().getColumn(0).setMaxWidth(30);
 		table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(50);
        
        
        table.getTableHeader().setDefaultRenderer(new TabHeader());
        table.getColumnModel().getColumn(1).setHeaderRenderer(new TabHeader());
        table.getColumnModel().getColumn(2).setHeaderRenderer(new TabHeader());
        table.getColumnModel().getColumn(4).setHeaderRenderer(new TabHeader());
        table.getColumnModel().getColumn(5).setHeaderRenderer(new TabHeader());
 		
 		//table.setForeground(Color.CYAN);
 		//table.setFont(new Font("Arial", Font.PLAIN, 15));
 		table.setSize(600, 400);
 		table.setLocation(20, 150);
 		JScrollPane scrollPane = new  JScrollPane(table);
 		scrollPane.setBounds(20, 150, 600, 400);
 		contentBox.add(scrollPane);
 		return contentBox;
	}

}
