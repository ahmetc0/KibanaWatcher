package tab;

import dto.KibanaDTO;
import tray.TrayHolder;

public class TabbedMain {
    public TabbedPane initializeMenu (KibanaDTO kibanaDTO, TrayHolder trayHolder) {
        TabbedPane tb = new TabbedPane(kibanaDTO, trayHolder);
        tb.setSize(tb.getSize());
        tb.setVisible(false);
        tb.setLocationRelativeTo(null);
        //tb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return tb;
    }
}