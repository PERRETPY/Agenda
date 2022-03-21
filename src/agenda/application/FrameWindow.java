package agenda.application;

import agenda.application.interfaces.CreateInterface;
import agenda.application.interfaces.CrudDataInterface;
import agenda.application.interfaces.DisplayInterface;
import agenda.models.Event;
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
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;
    private Object displayInterface;
    private Object pluginActif;
    private CrudDataInterface crudDataInterface;
    private JComponent displayComponent;
    private Object component;
    private List<Event> allEventList;
    private Box headerBox;
    private Box topSeparatorBox;
    private Box contentBox;
    private List<JButton> allPluginButton;

    public FrameWindow() {
        this.displayInterface = getDisplayInterface();
        this.crudDataInterface = getCrudDataInterface();
        this.initFrame();
    }


    private void initFrame() {
        this.mainFrame = new JFrame("Agenda");
        this.mainFrame.setSize(900,600);
        
        this.mainPanel =  new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        headerBox = new Box(BoxLayout.X_AXIS);
        topSeparatorBox = new Box(BoxLayout.X_AXIS);
        contentBox = new Box(BoxLayout.X_AXIS);
        //Box bottomSeparatorBox = new Box(BoxLayout.X_AXIS);
        //Box footerBox = new Box(BoxLayout.X_AXIS);
        
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
        		//create.setOpaque(true);
                btn.setBorderPainted(false);
                btn.setFont(new Font("Arial", Font.PLAIN, 15)); 
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //PluginLoader.loadPluginInList(descripteur.getName());
                    	pluginActif = PluginLoader.loadPluginsFor(descripteur,null);
                        //displayInterface = getDisplayInterface();
                        //statusLabel.setText(displayInterface.test());
                        //mainFrame.repaint();
                        //mainFrame.remove(displayComponent);
                        //displayInterface = getDisplayInterface();
                        if (pluginActif instanceof DisplayInterface ) {
                        	allEventList = getAllEventList();
                        	component = ((DisplayInterface) pluginActif).displayEventList(allEventList);
                        }
                        if (pluginActif instanceof CreateInterface ) {
                        	component = ((CreateInterface) pluginActif).createForm();
                        }
                        if (component != null) {
                        	contentBox.removeAll();
                        	contentBox.add((Component) component);
                            mainFrame.revalidate();
                            mainFrame.repaint();
                        }
                        
                        
                    }
                });
                allPluginButton.add(btn);
                headerBox.add(btn);
            }
        }
        
        
        
        topSeparatorBox.add(new JSeparator());
        //contentBox.add(new JTextField(""));
        this.mainPanel.add(headerBox);
        this.mainPanel.add(topSeparatorBox);
        this.mainPanel.add(contentBox);
        //this.mainPanel.add(bottomSeparatorBox);
        //this.mainPanel.add(footerBox);
        
        


        
        this.mainFrame.setContentPane(this.mainPanel);
        //this.mainFrame.setLayout(new GridLayout(3, 1));

        this.headerLabel = new JLabel("",JLabel.CENTER );
        this.statusLabel = new JLabel("",JLabel.CENTER);
        this.statusLabel.setSize(600,600);

        this.mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(new FlowLayout());

        this.mainFrame.add(this.headerLabel);
        this.mainFrame.add(this.controlPanel);
        this.mainFrame.add(this.statusLabel);
        this.mainFrame.setVisible(true);
    }
    
/*

    private void showGridEvent() {
        List<Event> allEventList = getAllEventList();
        List<String> allEventIdList = new ArrayList<>();

        int nbRow = allEventList.size() / 3;
        int reste = allEventList.size() % 3;
        nbRow += (reste != 0) ? 1 : 0;
        JPanel panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setSize(300,300);
        GridLayout layout = new GridLayout(nbRow,3);
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);

        for(int i=0 ; i<allEventList.size()  ; i++){
            JLabel statusLabel = new JLabel(allEventList.get(i).getTitleEvent(),JLabel.CENTER);
            panel.add(statusLabel);
            allEventIdList.add(String.valueOf(allEventList.get(i).getIdEvent()));
        }

        this.controlPanel.add(panel);
    }


    private void showGridLayoutDemo() {
        List<Event> allEventList = this.getAllEventList();
        //DisplayInterface displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
        //this.headerLabel.setText("Hello : " + this.displayInterface.test());
        //statusLabel.setText(this.displayInterface.test());

        JPanel panel = new JPanel();
        panel.setBackground(Color.darkGray);
        panel.setSize(300,300);
        GridLayout layout = new GridLayout(0,3);
        layout.setHgap(10);
        layout.setVgap(10);

        panel.setLayout(layout);

        HashMap<String, Descripteur> descriptorList = PluginLoader.getInstance().getDescripteurs();

        Iterator it = descriptorList.entrySet().iterator();

        while (it.hasNext()) {
            HashMap.Entry<String, Descripteur> entry = (HashMap.Entry)it.next();
            Descripteur descripteur = entry.getValue();
            if (!descripteur.isAutoRun()) {
                JButton btn = new JButton(descripteur.getName());
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        PluginLoader.loadPluginInList(descripteur.getName());
                        displayInterface = getDisplayInterface();
                       // statusLabel.setText(displayInterface.test());
                        mainFrame.repaint();
                    }
                });
                panel.add(btn);
            }
        }

        String[] choices = new String[fromEventToEventId(allEventList).size()];
        allEventList.toArray(new Event[0]); // fill the array

        final JComboBox<String> cb = new JComboBox<String>(choices);

        cb.setVisible(true);
        panel.add(cb);

        this.controlPanel.add(panel);
        this.mainFrame.setVisible(true);
    }



    private List<String> fromEventToEventId(List<Event> eventList) {
        List<String> eventIdList = new ArrayList<>();
        for(Event event: eventList) {
            System.out.println(String.valueOf(event.getIdEvent()));
            eventIdList.add(String.valueOf(event.getIdEvent()));
        }
        return eventIdList;
    }
*/
    private DisplayInterface getDisplayInterface() {
        return (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
    }

    private CrudDataInterface getCrudDataInterface() {
        return (CrudDataInterface) PluginLoader.getLoadPluginByInterface(CrudDataInterface.class);
    }
    private List<Event> getAllEventList() {
        return this.crudDataInterface.getAllEventList();
    }
    
    @Override
    public void run() {
        //this.initFrame();
        //FrameWindow frameWindow = new FrameWindow();
        //frameWindow.showGridLayoutDemo();
        //frameWindow.showGridEvent();
    	if(allPluginButton.size()>0) {
    		allPluginButton.get(0).doClick();
    	}
    }

}
