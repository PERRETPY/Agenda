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
    private Object pluginActif;
    private static List<Evenement> allEventList;
    private static Box headerBox;
    private static Box topSeparatorBox;
    private static Box contentBox;
    private List<JButton> allPluginButton;

    public FrameWindow() {
        this.initFrame();
    }


    private void initFrame() {
        FrameWindow.mainFrame = new JFrame("Agenda");
        FrameWindow.mainFrame.setSize(900,600);
        
        FrameWindow.mainPanel =  new JPanel();
        FrameWindow.mainPanel.setLayout(new BoxLayout(FrameWindow.mainPanel, BoxLayout.Y_AXIS));
        headerBox = new Box(BoxLayout.X_AXIS);
        topSeparatorBox = new Box(BoxLayout.X_AXIS);
        contentBox = new Box(BoxLayout.X_AXIS);
        
        HashMap<String, Descripteur> descriptorList = PluginLoader.getInstance().getDescripteurs();
        Iterator it = descriptorList.entrySet().iterator();
        allPluginButton = new ArrayList<JButton>();
        
        
        while (it.hasNext()) {
            HashMap.Entry<String, Descripteur> entry = (HashMap.Entry)it.next();
            Descripteur descripteur = entry.getValue();
            if (descripteur.isHeaderButton()) {
                JButton btn = new JButton(descripteur.getName());
                btn.setForeground(Color.WHITE);
                btn.setBackground(new Color(70, 137, 112));
        		
                btn.setBorderPainted(false);
                btn.setFont(new Font("Arial", Font.PLAIN, 15)); 
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	pluginActif = PluginLoader.loadPluginsFor(descripteur,null);
                        
                        ((OnClickInterface) pluginActif).execute();
                        
                        
                        
                    }
                });
                allPluginButton.add(btn);
                headerBox.add(btn);
            }
        }
        
        
        
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

    @Override
    public void run() {
    	if(allPluginButton.size()>0) {
    		allPluginButton.get(0).doClick();
    	}
    }

}
