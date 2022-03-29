package agenda.tiers;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import agenda.application.FrameWindow;
import agenda.application.interfaces.DisplayInterface;
import agenda.application.interfaces.SupportIntegrableInterface;
import agenda.models.Evenement;


public class DisplayCardEvent implements DisplayInterface, SupportIntegrableInterface{

	private JList<Evenement> eventJList;
	List<Evenement> eventList;
	public static JPanel contentPanel= new JPanel();
	JPanel footer= new JPanel();
	public static final String name = "Liste des events";
	@Override
	public Box displayEventList() {
		// TODO Auto-generated method stub
		eventList = FrameWindow.getAllEventList();
		Box page = new Box(BoxLayout.Y_AXIS);
		
		Box contentBox = new Box(BoxLayout.X_AXIS);
		
		JLabel title = new JLabel("Liste des evenements", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(0, 20, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		contentBox.add(title);
		 
		
		
		contentPanel.setLayout(new GridLayout(3, 3, 20, 25));
		contentPanel.setBorder(new EmptyBorder(10,10,10,10));
        
		if (eventList == null) {
			return contentBox;
		}
		showCard(eventList);
		
		JScrollPane scrollPane = new  JScrollPane(contentPanel);
 		scrollPane.setBounds(20, 150, 600, 400);
 		contentBox.add(scrollPane);
 		footer.setLayout(new FlowLayout(FlowLayout.CENTER));
		
 		page.add(contentBox);
 		page.add(footer); 
		return page;
	}
	
	public static void showCard(List<Evenement> listEvenement) {
		
		contentPanel.removeAll();
		for (int i = 0; i < listEvenement.size(); i++) {
			JPanel eventBox = new JPanel();
			
			eventBox.setLayout(new GridLayout(3, 1));    
			
			eventBox.setBackground(new Color(68,96,69));
			
			JLabel date = new JLabel(listEvenement.get(i).getJour()); 
			date.setFont(new Font("Arial", Font.PLAIN, 20)); 
			date.setForeground(Color.WHITE);
			date.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(date); 
			JLabel titre = new JLabel(listEvenement.get(i).getTitre()); 
			titre.setFont(new Font("Arial", Font.PLAIN, 15)); 
			titre.setForeground(Color.WHITE);
			titre.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(titre); 
			JLabel description = new JLabel(listEvenement.get(i).getDescription()); 
			description.setFont(new Font("Arial", Font.PLAIN, 12)); 
			description.setForeground(Color.WHITE);
			description.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(description); 
			
			contentPanel.add(eventBox);
		}
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		HashMap<String, List<Component>> externalComponent = SupportIntegrableInterface.getExternalPluginComponent(name);
		footer.removeAll();
		
		List<Component> externalFooterComponent = externalComponent.get("footer");
		for (Component c : externalFooterComponent) {
			System.out.println(c);
			footer.add(c);
		}
		FrameWindow.refreshComponent(displayEventList());
	}

}
