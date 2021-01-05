package tray;

import dto.MicroserviceDTO;
import tab.TabbedPane;
import tools.Elasticsearch;
import tray.TrayHolder;
import tray.TrayIconMenuItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Tray {
    private int id;
    private String name;
    private TrayIcon trayIcon;
    private String kibanaLast15Link = "";
    private String kibanaTodayLink = "";
    private String date = "";
    private String elasticsearchUrlIp = "";
    private String elasticSearchLogstash = "/logstash-";
    private String elasticsearchUrlMiddle = "/_count?q=";
    private String elasticsearchUrlMiddleSearch = "/_search?q=";
    private String elasticsearchUrlMiddle2 = "appname:";
    private String elasticsearchAppname = "";
    private String elasticsearchQueryAnd = "%20AND%20";
    private String elasticsearchQuery = "";
    private int count = 0;
    private int countBefore = 0;
    private int countErrorBefore = 0;
    private int iconDigitNumber = 2;
    private boolean errorKey = false;
    private TrayHolder trayHolder;
    private PopupMenu popup = new PopupMenu();
    private ArrayList<TrayIconMenuItem> trayIconMenuItems;


    public Tray(MicroserviceDTO microserviceDTO, TrayHolder trayHolder, int id) {
        this.id = id;
        this.name = microserviceDTO.getName();
        this.errorKey = microserviceDTO.isErrorKey();
        this.elasticsearchUrlIp = microserviceDTO.getElasticsearchUrlIp();
        this.elasticsearchAppname = microserviceDTO.getElasticsearchAppname();
        this.kibanaLast15Link = microserviceDTO.getKibanaLast15Link();
        this.kibanaTodayLink = microserviceDTO.getKibanaTodayLink();
        this.elasticsearchQuery = microserviceDTO.getElasticsearchQuery();
        this.trayHolder = trayHolder;
        this.iconDigitNumber = microserviceDTO.getIconDigitNumber();
        this.trayIconMenuItems = new ArrayList<>();
    }

    public void start() {
        Elasticsearch elasticsearch = new Elasticsearch();
        int count = getErrorNumber(elasticsearch);
        if(count == -1) {
            changeTrayIconText("-");
            return;
        }
        this.count = count;
        countBefore = count;
        changeTrayIconText(String.valueOf(count));
        trayIcon.setToolTip(name + " (" + count + ")");
    }

    public void mouseClickedEvent() {
        changeTrayIconText("0");
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(kibanaLast15Link));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        count = 0;
    }

    public void getTodayEvent() {
        changeTrayIconText("0");
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(kibanaTodayLink));
        }
        catch (java.io.IOException e) {
            System.out.println(e.getMessage());
        }
        count = 0;
    }

    public void changeTrayIconText(String text) {
        trayIcon.setImage(getBufferedImage(text));
    }

    private BufferedImage getBufferedImage(String text) {
        BufferedImage image2 = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image2.createGraphics();
        g2d.drawString(text, 0, 15);
        g2d.dispose();
        return image2;
    }

    private void changeTrayIconNumber(int num,int count) {
        trayIcon.setToolTip(name + " ( Today Errors: " + count + ")");
        if(num > 99)
            changeTrayIconText("X");
        else {
            BufferedImage image2 = getBufferedImage(String.valueOf(num));
            trayIcon.setImage(image2);
        }
    }

    private int getErrorNumber(Elasticsearch elasticsearch){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate localDate = LocalDate.now();
        String date = String.valueOf(dtf.format(localDate));
        if(this.date.compareTo(date) == 1){
            count = 0;
            countBefore = 0;
            countErrorBefore = 0;
        }
        System.out.println(date);
        return elasticsearch.getErrorNumner(elasticsearchUrlIp + elasticSearchLogstash + date + elasticsearchUrlMiddle + elasticsearchUrlMiddle2 + elasticsearchAppname + elasticsearchQueryAnd + elasticsearchQuery);
    }

    public void askToElasticsearch() {
        Elasticsearch elasticsearch = new Elasticsearch();
        int currentCount = getErrorNumber(elasticsearch);
        if(currentCount == -1) {
            changeTrayIconText("--");
            return;
        }
        if(countBefore != currentCount) {
            count += currentCount - countBefore;
            countBefore = currentCount;
            changeTrayIconNumber(count,currentCount);
        }

        errorAlert(count);
        countErrorBefore = currentCount;
    }

    private void errorAlert(int count) {
        if(errorKey && count > 100) {
            trayIcon.displayMessage(name,
                    "Hata sayısı 100 ün üstüne çıktı!",
                    TrayIcon.MessageType.ERROR);
        }
    }

    public void initializeTrayIcon(){
        if (SystemTray.isSupported()) {

            SystemTray tray = SystemTray.getSystemTray();
            Image image = getBufferedImage("88");//default icon

            ActionListener exitListener = e -> {
                System.out.println("Exiting...");
                System.exit(0);
            };
            ActionListener kibanaTodayListener = e -> getTodayEvent();
            ActionListener resetListener = e -> trayHolder.counterReset();
            ActionListener settingsItemListener = e -> trayHolder.menu();
            ActionListener aboutItemListener = e -> trayHolder.aboutEvent();
            ActionListener empty = e -> {};
            ActionListener resetWatcherItemListener = e -> TabbedPane.reset();

            MouseListener mouseListener = new MouseListener() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        System.out.println("tray.Tray Icon - Double Mouse clicked!");
                        mouseClickedEvent();
                    }
                }
                public void mouseEntered(MouseEvent e) {
                    System.out.println("tray.Tray Icon - Mouse entered!");
                }
                public void mouseExited(MouseEvent e) {
                    System.out.println("tray.Tray Icon - Mouse exited!");
                }
                public void mousePressed(MouseEvent e) {
                    System.out.println("tray.Tray Icon - Mouse pressed!");
                }
                public void mouseReleased(MouseEvent e) {
                    System.out.println("tray.Tray Icon - Mouse released!");
                }
            };


            newMenuItem(empty, elasticsearchAppname, false);
            newMenuItem(kibanaTodayListener, "Extra Link", false);
            newMenuItem(resetListener, "Clear Errors", false);
            newMenuItem(settingsItemListener, "Settings", false);
            newMenuItem(aboutItemListener, "About", false);
            newMenuItem(exitListener, "Exit", false);
            newMenuItem(resetWatcherItemListener, "Reset Watcher", false);
            resetMenu();

            trayIcon = new TrayIcon(image, name, popup);
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(mouseListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        } else {
            System.err.println("System tray.Tray is not supported.");
        }
    }

    private void newMenuItem(ActionListener resetListener, String name, boolean hide) {
        MenuItem resetItem = new MenuItem(name);
        resetItem.addActionListener(resetListener);
        trayIconMenuItems.add(new TrayIconMenuItem(resetItem,hide));
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isErrorKey() {
        return errorKey;
    }

    public void setErrorKey(boolean errorKey) {
        this.errorKey = errorKey;
    }

    public void resetMenu() {
        popup.removeAll();
        trayIconMenuItems.forEach(trayIconMenuItem -> {
            if(!trayIconMenuItem.isHide())
                popup.add(trayIconMenuItem.getMenuItem());
        });
    }

    public void refresh(ArrayList<MicroserviceDTO> microserviceDTOArrayList) {
        MicroserviceDTO microserviceDTO = microserviceDTOArrayList.stream().filter(microserviceDTO2 -> microserviceDTO2.getName().equals(name)).collect(Collectors.toList()).iterator().next();
        this.name = microserviceDTO.getName();
        this.elasticsearchUrlIp = microserviceDTO.getElasticsearchUrlIp();
        this.elasticsearchAppname = microserviceDTO.getElasticsearchAppname();
        this.kibanaLast15Link = microserviceDTO.getKibanaLast15Link();
        this.kibanaTodayLink = microserviceDTO.getKibanaTodayLink();
        this.elasticsearchQuery = microserviceDTO.getElasticsearchQuery();
        this.iconDigitNumber = microserviceDTO.getIconDigitNumber();
    }
}
