package agenda.tiers;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import agenda.application.FrameWindow;
import agenda.application.interfaces.CrudDataInterface;
import agenda.application.interfaces.DisplayInterface;
import agenda.application.interfaces.IntegrableInterface;
import agenda.application.interfaces.OnClickInterface;
import agenda.application.interfaces.SupportIntegrableInterface;
import agenda.models.Evenement;
import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;
import agenda.tiers.component.TabHeader;


public class DisplayTabEvent implements DisplayInterface, SupportIntegrableInterface{

	private JList<Evenement> eventJList;
	private List<Evenement> eventList;
	public static Box contentBox;
	public static JTable table;
	public static JPanel footerBox;
	public static final String name = "Tableau des events";

	@Override
	public Box displayEventList() {
		eventList = FrameWindow.getAllEventList();
		// TODO Auto-generated method stub
		this.contentBox = new Box(BoxLayout.Y_AXIS);
		
		JLabel title = new JLabel("Liste des evenements", SwingConstants.CENTER); 
		//title.setBorder(new EmptyBorder(0, 180, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		
		JPanel header = new JPanel();
		header.setLayout(new FlowLayout(FlowLayout.CENTER));
		header.add(title);
		
		this.contentBox.add(header); 
		
		if (eventList == null) {
			return this.contentBox;
		}
		Evenement [] rdvArray = new Evenement[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) 
		 rdvArray[i] = eventList.get(i);

		 		 
 		
 		String[] columnNames = new String[] {"N","TITRE", "JOUR", "DEBUT", "FIN", "DESCRIPTION", "TYPE","ORGANISATEUR"};
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
 		DisplayTabEvent.table = new JTable(model);
        
 		DisplayTabEvent.table.getColumnModel().getColumn(0).setMaxWidth(30);
 		DisplayTabEvent.table.getColumnModel().getColumn(2).setMaxWidth(125);
        DisplayTabEvent.table.getColumnModel().getColumn(3).setMaxWidth(75);
        DisplayTabEvent.table.getColumnModel().getColumn(4).setMaxWidth(75);
        
        DisplayTabEvent.table.getTableHeader().setDefaultRenderer(new TabHeader());
        DisplayTabEvent.table.getColumnModel().getColumn(1).setHeaderRenderer(new TabHeader());
        DisplayTabEvent.table.getColumnModel().getColumn(2).setHeaderRenderer(new TabHeader());
        DisplayTabEvent.table.getColumnModel().getColumn(4).setHeaderRenderer(new TabHeader());
        DisplayTabEvent.table.getColumnModel().getColumn(5).setHeaderRenderer(new TabHeader());
 		
 		//table.setForeground(Color.CYAN);
 		//table.setFont(new Font("Arial", Font.PLAIN, 15));
 		DisplayTabEvent.table.setSize(600, 400);
 		DisplayTabEvent.table.setLocation(20, 150);
 		JScrollPane scrollPane = new  JScrollPane(DisplayTabEvent.table);
 		scrollPane.setBounds(20, 150, 600, 400);
 		this.contentBox.add(scrollPane);
 		
 		/*
 		
 		*/
 		
 		
 		//Box footerBox = new Box(BoxLayout.X_AXIS);
 		
 		
        
 		//footerBox = new JPanel();
 		//footerBox.add(modifier);
 		//footerBox.add(supprimer);
 		
 		this.contentBox.add(footerBox);
		
 		return this.contentBox;
	}
	public void actionBtnModifier() {
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		HashMap<String, List<Component>> externalComponent = SupportIntegrableInterface.getExternalPluginComponent(name);
		footerBox = new JPanel();
		footerBox.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		List<Component> externalFooterComponent = externalComponent.get("footer");
		for (Component c : externalFooterComponent) {
			System.out.println(c);
			footerBox.add(c);
		}
		
		FrameWindow.refreshComponent(displayEventList());
	}
	


}
