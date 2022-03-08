package agenda.application;

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

public class FrameWindow implements DisplayInterface, Runnable {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;
    private DisplayInterface displayInterface;
    private CrudDataInterface crudDataInterface;

    public FrameWindow() {
        this.displayInterface = getDisplayInterface();
        this.crudDataInterface = getCrudDataInterface();
        this.initFrame();
    }

    @Override
    public String test() {
        return null;
    }

    private void initFrame() {
        this.mainFrame = new JFrame("Agenda");
        this.mainFrame.setSize(400,400);
        this.mainFrame.setLayout(new GridLayout(3, 1));

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

        for(int i=0 ; i<allEventList.size() ; i++) {
            JLabel statusLabel = new JLabel(allEventList.get(i).getTitleEvent(),JLabel.CENTER);
            panel.add(statusLabel);
            allEventIdList.add(String.valueOf(allEventList.get(i).getIdEvent()));
        }

        this.controlPanel.add(panel);
    }

    private List<Event> getAllEventList() {
        return this.crudDataInterface.getAllEventList();
    }

    private void showGridLayoutDemo() {
        List<Event> allEventList = this.getAllEventList();
        //DisplayInterface displayInterface = (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
        this.headerLabel.setText("Hello : " + this.displayInterface.test());
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
                        statusLabel.setText(displayInterface.test());
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

    private DisplayInterface getDisplayInterface() {
        return (DisplayInterface) PluginLoader.getLoadPluginByInterface(DisplayInterface.class);
    }

    private CrudDataInterface getCrudDataInterface() {
        return (CrudDataInterface) PluginLoader.getLoadPluginByInterface(CrudDataInterface.class);
    }

    private List<String> fromEventToEventId(List<Event> eventList) {
        List<String> eventIdList = new ArrayList<>();
        for(Event event: eventList) {
            System.out.println(String.valueOf(event.getIdEvent()));
            eventIdList.add(String.valueOf(event.getIdEvent()));
        }
        return eventIdList;
    }

    @Override
    public void run() {
        this.initFrame();
        FrameWindow frameWindow = new FrameWindow();
        frameWindow.showGridLayoutDemo();
        frameWindow.showGridEvent();
    }

}
