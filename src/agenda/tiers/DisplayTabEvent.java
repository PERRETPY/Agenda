package agenda.tiers;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import agenda.application.FrameWindow;
import agenda.application.interfaces.CrudDataInterface;
import agenda.application.interfaces.DisplayInterface;
import agenda.application.interfaces.OnClickInterface;
import agenda.models.Evenement;
import agenda.tiers.component.TabHeader;


public class DisplayTabEvent implements DisplayInterface{

	private JList<Evenement> eventJList;
	List<Evenement> eventList;


	@Override
	public Box displayEventList() {
		eventList = FrameWindow.getAllEventList();
		// TODO Auto-generated method stub
		Box contentBox = new Box(BoxLayout.Y_AXIS);
		
		JLabel title = new JLabel("Liste des evenements", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(0, 180, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		contentBox.add(title); 
		
		Evenement [] rdvArray = new Evenement[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) 
		 rdvArray[i] = eventList.get(i);

		 		 
 		
 		String[] columnNames = new String[] {"N","TITRE", "DATE", "DEBUT", "FIN", "DESCRIPTION", "TYPE","ORGANISATEUR"};
 		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
 		for (int i = 0; i < eventList.size(); i++) {
 			 model.addRow( new Object[]{ 
 					 1+i, 
 					eventList.get(i).getTitre(),
 					eventList.get(i).getJour(),
 					eventList.get(i).getHeureDebut(),
 					eventList.get(i).getHeureFin(),
 					eventList.get(i).getDescription(),
 					eventList.get(i).getType(),
 					eventList.get(i).getOrganisateur()} );
 		}
 		JTable table = new JTable(model);
        
 		table.getColumnModel().getColumn(0).setMaxWidth(30);
 		table.getColumnModel().getColumn(2).setMaxWidth(125);
        table.getColumnModel().getColumn(3).setMaxWidth(75);
        table.getColumnModel().getColumn(4).setMaxWidth(75);
        
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
 		
 		JButton modifier = new JButton("Enregistrer modification"); 
 		modifier.setFont(new Font("Arial", Font.PLAIN, 15));
 		
 		modifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] selection = table.getSelectedRows();
               
               if (selection.length == 0) {
            	   JOptionPane.showMessageDialog(contentBox,
       					"Vous devez d'abord selectionner l'événement qui a été modifier",
       				    "Action incorrect",
       				    JOptionPane.WARNING_MESSAGE);
               }
               if (selection.length > 1) {
            	   JOptionPane.showMessageDialog(contentBox,
       					"Vous ne pouvez enregistrer qu'un événement à la fois",
       				    "Action incorrect",
       				    JOptionPane.WARNING_MESSAGE);
               }
               if (selection.length == 1) {
            	 System.out.println(selection[0]);
            	 System.out.println(table.getValueAt(selection[0], 1));
            	 System.out.println(table.getValueAt(selection[0], 2));
            	 System.out.println(table.getValueAt(selection[0], 3));
            	 System.out.println(table.getValueAt(selection[0], 4));
               }
        	   
        	   
            }
 		});
 		
 		JButton supprimer = new JButton("Supprimer"); 
 		supprimer.setFont(new Font("Arial", Font.PLAIN, 15));
		
 		supprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int[] selection = table.getSelectedRows();
            	int nbSuppression = 0;
            	if (selection.length == 0) {
             	   JOptionPane.showMessageDialog(contentBox,
        					"Vous devez d'abord selectionner les événements à supprimer",
        				    "Action incorrect",
        				    JOptionPane.WARNING_MESSAGE);
                }
                
         	   for (int i = 0; i < selection.length; i++) {
         		   System.out.println(selection[i]);
         		  eventList.remove(selection[i]-nbSuppression);
         		 nbSuppression++;
         	   }
         	  execute();
            }
 		});
 		
 		//Box footerBox = new Box(BoxLayout.X_AXIS);
 		
 		JPanel footerBox = new JPanel();
 		footerBox.setLayout(new FlowLayout(FlowLayout.CENTER));
        
 		
 		footerBox.add(modifier);
 		footerBox.add(supprimer);
 		
 		contentBox.add(footerBox);
		
 		return contentBox;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		FrameWindow.refreshComponent(displayEventList());
	}

}
