import dto.KibanaDTO;
import org.json.JSONException;
import org.json.JSONObject;
import tab.TabbedMain;
import tab.TabbedPane;
import tray.TrayHolder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KibanaWatcher {
        KibanaDTO kibanaDTO;
        TabbedMain tabbedMain;
        TrayHolder trayHolder;
        TabbedPane tabbedPane;

    KibanaWatcher(){
        JSONObject kibanaInformations = getKibanaInformationsFromFile();
        kibanaDTO = new KibanaDTO(kibanaInformations);
        tabbedMain = new TabbedMain();
        trayHolder = new TrayHolder();
        tabbedPane = tabbedMain.initializeMenu(kibanaDTO,trayHolder);
        trayHolder.initializeMicroservices(trayHolder, kibanaDTO, tabbedPane);
        System.out.println("System initialized!");
    }

    public void run(){
        trayHolder.begin(trayHolder);
    }

    private JSONObject getKibanaInformationsFromFile() {
        try {
            String data = "";
            data = new String(Files.readAllBytes(Paths.get("microservices.json")));
            return new JSONObject(data);

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
