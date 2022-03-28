package agenda.application;


import agenda.application.interfaces.OnClickInterface;
import agenda.models.Evenement;
import agenda.plateforme.PluginLoader;
import agenda.plateforme.models.Descripteur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FrameWindow implements  Runnable {
    private static JFrame mainFrame;
    private static JPanel mainPanel;
    private static Object pluginActif;
    private static JButton headerButtonActif;
    private static List<Evenement> allEventList;
    private static Box headerBox;
    private static Box topSeparatorBox;
    private static Box contentBox;
    private static List<JButton> allPluginButton;

    public FrameWindow() {
        this.initFrame();
    }


    private void initFrame() {
        FrameWindow.mainFrame = new JFrame("Agenda");
        FrameWindow.mainFrame.setSize(900,600);
        
        FrameWindow.mainPanel =  new JPanel();
        FrameWindow.mainPanel.setLayout(new BoxLayout(FrameWindow.mainPanel, BoxLayout.Y_AXIS));
        
        topSeparatorBox = new Box(BoxLayout.X_AXIS);
        contentBox = new Box(BoxLayout.X_AXIS);
        
        //
        headerBox = new Box(BoxLayout.X_AXIS);
        refreshHeaderButton();
        
        topSeparatorBox.add(new JSeparator());
        
        FrameWindow.mainPanel.add(headerBox);
        FrameWindow.mainPanel.add(topSeparatorBox);
        FrameWindow.mainPanel.add(contentBox);
        
        


        
        FrameWindow.mainFrame.setContentPane(FrameWindow.mainPanel);

     

        FrameWindow.mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
            	System.out.println("Save data to csv");
                System.exit(0);
            }
        });
        

        FrameWindow.mainFrame.setVisible(true);
    }

    public static List<Evenement> getAllEventList () {
    	return FrameWindow.allEventList;
    }
    public static void setAllEventList (List<Evenement> events) {
    	FrameWindow.allEventList = events;
    }
    public static void refreshComponent(Object component) {
    	if (component != null) {
        	contentBox.removeAll();
        	contentBox.add((Component) component);
            mainFrame.revalidate();
            mainFrame.repaint();
        }
    }
    
    public static void refreshPage () {
    	headerButtonActif.doClick();
    }
    public static void refreshHeaderButton() {
    	
    	HashMap<String, Descripteur> descriptorList = PluginLoader.getInstance().getLoadedPlugin();
        Iterator it = descriptorList.entrySet().iterator();
        allPluginButton = new ArrayList<JButton>();
        
        headerBox.removeAll();
        while (it.hasNext()) {
            HashMap.Entry<String, Descripteur> entry = (HashMap.Entry)it.next();
            Descripteur descripteur = entry.getValue();
            if (descripteur.getPosition()!= null && descripteur.getPosition().equals("header")) {
                JButton btn = new JButton(descripteur.getName());
                btn.setForeground(Color.WHITE);
                btn.setBackground(new Color(70, 137, 112));
        		
                btn.setBorderPainted(false);
                btn.setFont(new Font("Arial", Font.PLAIN, 15)); 
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	pluginActif = PluginLoader.recupererIntancePlugin(descripteur);
                        
                        ((OnClickInterface) pluginActif).execute();
                        headerButtonActif = btn;
                        
                        
                    }
                });
                allPluginButton.add(btn);
                headerBox.add(btn);
            }
        }
        if(allPluginButton.size()>0) {
    		allPluginButton.get(0).doClick();
    	}
    }
    
    @Override
    public void run() {
    	
    }

}
