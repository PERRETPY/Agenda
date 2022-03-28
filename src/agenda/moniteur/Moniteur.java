package agenda.moniteur;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import agenda.plateforme.PluginLoader;
import agenda.plateforme.listener.Observer;
import agenda.plateforme.listener.Subject;
import agenda.tiers.DisplayTabEvent;

/**
 * Plugin pour le laoder de la plateforme
 * Permet un suivi en temps réel de l'état des plugins disponibles
 *
 */
public class Moniteur extends JFrame implements Runnable,Subject, Observer {

	private Map<String,String> listePlugins;
	
	
	private JLabel labelList;	
	private JList <String> historic;
	private DefaultListModel <String> dlm;
	private DefaultTableModel dtm;
	private JTable table;
    List<Observer> suscribers;
	
    /** The Constant INSTANCE. */
    private static final Moniteur INSTANCE = new Moniteur();
    
	public Moniteur() {
		this.suscribers = new ArrayList<Observer>();
		PluginLoader.getInstance().addSubscriber(this);
		this.addSubscriber(PluginLoader.getInstance());
		listePlugins = new HashMap<String, String>();
		
		setTitle("Monitor");
		setBounds(300, 90, 400, 500);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));	
		
		labelList = new JLabel("Liste des plugins charges : ",SwingConstants.LEFT);
		
		dlm = new DefaultListModel<String>();
		historic = new JList<>(dlm);
		String[] entetes = {"Plugin","Status"};
		dtm = new DefaultTableModel(entetes,0);
		table = new JTable(dtm);
		
		JScrollPane scrollPane = new  JScrollPane(table);
 		scrollPane.setBounds(20, 150, 600, 400);
 		
        
        
        Box footerBox = new Box(BoxLayout.X_AXIS);
        JButton load = new JButton("Charger"); 
        load.setFont(new Font("Arial", Font.PLAIN, 15));
 		
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sendActionMoniteur("charger");
            }
 		});
        JButton stop = new JButton("Stopper"); 
        stop.setFont(new Font("Arial", Font.PLAIN, 15));
 		
        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sendActionMoniteur("stopper");
            }
 		});
        footerBox.add(load);
        footerBox.add(stop);
		add(labelList);
		add(scrollPane);
		add(footerBox);
		add(historic);
	}
	
    /**
     * Gets the single instance of Moniteur.
     *
     * @return single instance of Moniteur
     */
    public static final Moniteur getInstance() {
        return INSTANCE;
    }
	
	public void run() {
		System.out.println("Lancement du moniteur");		
		setVisible(true);
	}

	/**
	 * Ajoute les informations reçues dans la map des plugins et met à jour le tableau de suivi et l'historique
	 * @param name : nom du plugin notifié
	 * @param status : état du plugin notifié
	 */
	@Override
	public void update(String name, String status, String message) {
		listePlugins.put(name,status);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM : hh:mm:ss");
		LocalDateTime time = LocalDateTime.now();
		addHistoricLine(time.format(formatter),name,status,message);
		
		refreshTable();
		revalidate();
		repaint();
		
	}
	
	/**
	 * Ajoute une ligne d'historique à l'interface graphique
	 * @param time : heure actuelle
	 * @param plugin : nom du plugin notifié
	 * @param status : état du plugin notifié
	 */
	private void addHistoricLine(String time, String plugin, String status, String message) {
		
		dlm.addElement(time+" : "+plugin+" ---> "+ message);
		if(dlm.size() > 5) {
			//dlm.remove(0);
		}
	}
	
	/**
	 * Remplace les données du tableau avec les modifications apportées via le loader
	 */
	private void refreshTable() {
		Object[][] arr = new Object[listePlugins.size()][2];
		Set entries = listePlugins.entrySet();
		Iterator entriesIterator = entries.iterator();

		int i = 0;
		while(entriesIterator.hasNext()){

		    Map.Entry mapping = (Map.Entry) entriesIterator.next();

		    arr[i][0] = mapping.getKey();
		    arr[i][1] = mapping.getValue();
		    i++;
		}
		String[] entetes = {"Plugin","Status"};
		dtm.setDataVector(arr, entetes);
	}

	@Override
	public void addSubscriber(Observer observer) {
		if(this.suscribers==null) {
			this.suscribers = new ArrayList<Observer>();
		}
		this.suscribers.add(observer);
	}

	@Override
	public void removeSubscriber(Observer observer) {
		if (this.suscribers.contains(observer)) {
			this.suscribers.remove(observer);
		}
	}

	@Override
	public void notifySubscribers(String name, String status, String message) {
		for(Observer suscriber : this.suscribers) {
           suscriber.update(name,status,message);
           System.out.println(suscriber);
        }
	}
	
	public void sendActionMoniteur(String action) {
		int[] selection = table.getSelectedRows();
    	if (selection.length == 0) {
      	   JOptionPane.showMessageDialog(table,
 					"Vous devez d'abord selectionner le plugin à "+action,
 				    "Action incorrect",
 				    JOptionPane.WARNING_MESSAGE);
         }
         if (selection.length > 1) {
      	   JOptionPane.showMessageDialog(table,
 					"Vous ne pouvez "+action+" qu'un plugin à la fois",
 				    "Action incorrect",
 				    JOptionPane.WARNING_MESSAGE);
         }
         if (selection.length == 1) {
        	 String pluginName = (String) table.getValueAt(selection[0], 0);
        	 notifySubscribers(pluginName, action,"");
      	   
         }
	}
}
