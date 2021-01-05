package tray;

import dto.KibanaDTO;
import dto.MicroserviceDTO;
import tab.TabbedPane;
import tools.CounterTimerTask;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class TrayHolder {

    List<Tray> trayList = new ArrayList<>();
    TabbedPane tabbedPane;

    public List<Tray> initializeMicroservices(TrayHolder trayHolder, KibanaDTO kibanaInformations, TabbedPane tabbedPane) {
        int id = 0;
        for (MicroserviceDTO microserviceDTO : kibanaInformations.getMicroserviceDTOArrayList()) {

            trayList.add(TrayInit(microserviceDTO,trayHolder,id++));
        }
        this.tabbedPane = tabbedPane;
        return trayList;
    }

    public Tray TrayInit(MicroserviceDTO microserviceDTO, TrayHolder trayHolder, int id) {
        Tray tray = new Tray(microserviceDTO, trayHolder, id);
        tray.initializeTrayIcon();
        return tray;
    }

    public void begin(TrayHolder trayHolder) {
        trayList.forEach(Tray::start);
        System.out.println("System has been started!");
        timerLoopBegin(trayList);
    }

    private void timerLoopBegin(List<Tray> trayList) {
        TimerTask task = new CounterTimerTask(trayList);
        Timer timer = new Timer(true); // deamon timer

        timer.schedule(task, 0, 60000); // cancelled automatically when the main program finishes
        try {
            Thread.sleep(60000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void counterReset() {
        trayList.forEach(tray ->{
            tray.changeTrayIconText("0");
            tray.setCount(0);
        });
        System.out.println("counterReset");
    }

    public void errorsOn() {
        trayList.forEach(tray ->{
            tray.setErrorKey(true);
            tray.resetMenu();
        });
        System.out.println("errorsOn");
    }

    public void errorsOff() {
        trayList.forEach(tray ->{
            tray.setErrorKey(false);
            tray.resetMenu();
        });
        System.out.println("errorsOff");
    }

    public List<Tray> getTrayList() {
        return trayList;
    }

    public void aboutEvent() {
        JFrame frame = new JFrame("Kibana Watcher 2.1");

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JTextArea jTextArea = new JTextArea("Uygulama mikroservis verilerini microservices.json içerisinde saklıyor.\n" +
                "\n" +
                "Hata sayısı üzerine çift tıklayınca kibana linki açılır. isteğe göre bu link değiştirilebilir.\n" +
                "Hata sayısı üzerine sağ tıklanıldığında sayaç sıfırlama, ayarlar, çıkış ve iklinci link seçeneği var. \n" +
                "İsteğe göre çift tıklama ile son 15dk hataları, sağ tık ile gelen extra linke ise günlük hataların linki koyulabilir.\n" +
                "\n" +
                "Ayarlar menüsünde tab bölgesine sağ tıklayarak yeni mikroservis ekleyebilir ya da çıkartabilriz. \n" +
                "Kaydet yapmadıkça kaydetmez ve uygulamayı kapatıp açınca geri gelir eski hali.\n" +
                "Mikroservis adı kişisel olarak değiştirilebilir ancak diğer alanlar belirli standartta girilmelidir.\n" +
                "İsteğe göre query alanında değişiklik yapılabilir, bildiğimiz kibana search mantığındadır ancak url yazar gibi yani boşluk yerine %20 kullanmak gerekir.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Windows açılırken otomatik başlaması için .bat file hazırladım, eğer o çalışmazsa aşağıdaki yöntemi uygulayablirsiniz.\n" +
                "\n" +
                "-Windows logo butonu ve R butonuna aynı anda basın, shell:startup yazın ve onaylayın. \n" +
                "-Açılan klasöre KibanaWatcher.jar ın kısayolunu kopyalayın.\n" +
                "\n" +
                "\n" +
                "Görüş ve önerilere açığım :)");


        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setColumns(39);
        JLabel label = new JLabel("Ahmet Ceyhan");

        panel.add(jTextArea);
        panel.add(label);
        frame.add(panel);
        frame.setSize(450, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void menu() {
        tabbedPane.setVisible(true);
    }

    public void refresh(ArrayList<MicroserviceDTO> microserviceDTOArrayList) {
        trayList.forEach(tray -> tray.refresh(microserviceDTOArrayList));
    }
}
