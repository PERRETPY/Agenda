package agenda.tiers;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
import agenda.models.Evenement;


public class DisplayListEvent implements DisplayInterface{

	private JList<Evenement> eventJList;
	List<Evenement> eventList;

	@Override
	public Box displayEventList() {
		// TODO Auto-generated method stub
		eventList = FrameWindow.getAllEventList();
		Box contentBox = new Box(BoxLayout.X_AXIS);
		
		JLabel title = new JLabel("Liste des evenements", SwingConstants.CENTER); 
		title.setBorder(new EmptyBorder(0, 20, 10, 10));
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		contentBox.add(title); 
		
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(3, 3, 20, 25));
		contentPanel.setBorder(new EmptyBorder(10,10,10,10));
        
		if (eventList == null) {
			return contentBox;
		}
		Evenement [] eventArray = new Evenement[eventList.size()];
		for (int i = 0; i < eventList.size(); i++) {
			eventArray[i] = eventList.get(i);
			JPanel eventBox = new JPanel();
			
			eventBox.setLayout(new GridLayout(3, 1));    
			
			eventBox.setBackground(new Color(68,96,69));
			
			JLabel date = new JLabel(eventList.get(i).getJour()); 
			date.setFont(new Font("Arial", Font.PLAIN, 20)); 
			date.setForeground(Color.WHITE);
			date.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(date); 
			JLabel titre = new JLabel(eventList.get(i).getTitre()); 
			titre.setFont(new Font("Arial", Font.PLAIN, 15)); 
			titre.setForeground(Color.WHITE);
			titre.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(titre); 
			JLabel description = new JLabel(eventList.get(i).getDescription()); 
			description.setFont(new Font("Arial", Font.PLAIN, 12)); 
			description.setForeground(Color.WHITE);
			description.setBorder(new EmptyBorder(10, 10, 10, 10));
			eventBox.add(description); 
			
			contentPanel.add(eventBox);
		}
		JScrollPane scrollPane = new  JScrollPane(contentPanel);
 		scrollPane.setBounds(20, 150, 600, 400);
 		contentBox.add(scrollPane);
		//eventJList= new JList<>(eventArray);
		//eventJList.setForeground(Color.BLUE);
		//eventJList.setFont(new Font("Arial", Font.PLAIN, 15));
		//eventJList.setSize(500, 400);
		//eventJList.setLocation(20, 150);
		//contentBox.add(eventJList);
		return contentBox;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		FrameWindow.refreshComponent(displayEventList());
	}

}
