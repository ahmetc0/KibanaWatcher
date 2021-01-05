package tab;

import dto.KibanaDTO;
import dto.MicroserviceDTO;
import tools.SaveToJson;
import tools.SpringUtilities;
import tray.TrayHolder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class TabbedPane extends JFrame {
    private JTabbedPane pane;
    private ArrayList<JPanel> panelList = new ArrayList<>();
    private int tabWidth = 0;
    private TrayHolder trayHolder;

    public TabbedPane(KibanaDTO kibanaDTO, TrayHolder trayHolder) {
        super("Kibana Watcher");
        String tabName = "microservice";
        pane = new JTabbedPane();
        add(pane);
        for (MicroserviceDTO microserviceDTO : kibanaDTO.getMicroserviceDTOArrayList()) {
            initializeTab(microserviceDTO);
        }
        this.trayHolder = trayHolder;
    }

    private void initializeTab(MicroserviceDTO microserviceDTO) {

        String[] labels = {"Microservice Name",
                "tools.Elasticsearch Ip",
                "tools.Elasticsearch Appname",
                "Double Click Link",
                "Extra Link",
                "tools.Elasticsearch Query"};
        int numPairs = labels.length;

        JPanel panel1 = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            panel1.add(l);
            JTextField textField = new JTextField(10);
            String text = getText(microserviceDTO, i);
            textField.setText(text.compareTo("+") == 0 ? "" : text);
            l.setLabelFor(textField);
            panel1.add(textField);
        }
        JLabel l = new JLabel("Error alert", JLabel.TRAILING);
        panel1.add(l);
        JCheckBox checkbox1 = new JCheckBox("");
        checkbox1.setBounds(150, 100, 60, 60);
        checkbox1.setSelected(microserviceDTO.isErrorKey());
        checkbox1.setToolTipText("System will give warning when error increase is too fast");
        panel1.add(checkbox1);

        JButton button = new JButton("Save");
        button.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        ArrayList<MicroserviceDTO> microserviceDTOArrayList = new ArrayList<>();
                        JOptionPane.showMessageDialog(null, "Saved");
                        panelList.forEach(panel -> microserviceDTOArrayList.add(getMicroserviceFromPanel(panel)));
                        KibanaDTO kibanaDTO = new KibanaDTO(microserviceDTOArrayList);
                        SaveToJson saveToJson = new SaveToJson();
                        saveToJson.save(kibanaDTO);
                        reset();
                    }
                }
        );
        JLabel l2 = new JLabel("", JLabel.TRAILING);
        panel1.add(l2);
        panel1.add(button);


        JPopupMenu pop_up = new JPopupMenu();
        refreshCloseTabs(pop_up);
        JMenuItem closetab = getCloseTab(panel1,pop_up);
        pop_up.add(closetab);

        pane.setComponentPopupMenu(pop_up);

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel1,
                numPairs + 2, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        panelList.add(panel1);

        pane.addTab(microserviceDTO.getName(), panel1);
        tabWidth = tabWidth + microserviceDTO.getElasticsearchAppname().length();
    }


    public static void reset() {
        try {
            Runtime.getRuntime().exec("java -jar KibanaWatcher_v2.1.jar");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void refreshCloseTabs(JPopupMenu pop_up) {
        pop_up.removeAll();
        JMenuItem newtab = new JMenuItem("Add Microservice");
        newtab.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        String st = JOptionPane.showInputDialog(null, "Enter Microservice Name.");
                        if (!st.equals("")) {
                            JPanel panel2 = new JPanel();
                            JLabel label = new JLabel("Your program is working successfully.");
                            panel2.add(label);
                            initializeTab(new MicroserviceDTO(st, "", "", "", "", "", false));
                        }
                    }
                }
        );

        pop_up.add(newtab);
        for(JPanel panel2 : panelList) {
            JMenuItem closetab = getCloseTab(panel2,pop_up);
            pop_up.add(closetab);
        }
    }

    private JMenuItem getCloseTab(JPanel panel1, JPopupMenu pop_up) {
        JMenuItem closetab = new JMenuItem("Remove " + getMicroserviceFromPanel(panel1).getName());
        closetab.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (panelList.size() > 1) {
                            int st = JOptionPane.showConfirmDialog(null, "Microservice " + getMicroserviceFromPanel(panel1).getName());
                            if (st == 0) {
                                pane.remove(panel1);
                                panelList.remove(panel1);
                                refreshCloseTabs(pop_up);
                            }
                        } else
                            JOptionPane.showMessageDialog(null, "The last window cannot be removed.");
                    }
                }
        );
        return closetab;
    }

    private String getText(MicroserviceDTO microserviceDTO, int i) {
        try {
            switch (i) {
                case 0:
                    return microserviceDTO.getName();
                case 1:
                    return microserviceDTO.getElasticsearchUrlIp();
                case 2:
                    return microserviceDTO.getElasticsearchAppname();
                case 3:
                    return microserviceDTO.getKibanaLast15Link();
                case 4:
                    return microserviceDTO.getKibanaTodayLink();
                case 5:
                    return microserviceDTO.getElasticsearchQuery();
                default:
                    System.out.println("Error: the file cannot be read!");
                    break;
            }
        } catch (Exception E) {
        }
        return "Hata: Dosyadan okuma yapılamadı";
    }

    public Dimension getSize() {
        return new Dimension(800, 400);// new Dimension(tabWidth * 18, 312);
    }

    private MicroserviceDTO getMicroserviceFromPanel(JPanel panel1) {
        return new MicroserviceDTO(((JTextField) panel1.getComponent(1)).getText(),
                ((JTextField) panel1.getComponent(3)).getText(),
                ((JTextField) panel1.getComponent(5)).getText(),
                ((JTextField) panel1.getComponent(7)).getText(),
                ((JTextField) panel1.getComponent(9)).getText(),
                ((JTextField) panel1.getComponent(11)).getText(),
                ((JCheckBox) panel1.getComponent(13)).isSelected());
    }
}