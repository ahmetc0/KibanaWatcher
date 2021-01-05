package tab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TabCloseActionHandler {
    JTabbedPane tabPane;

    TabCloseActionHandler(JTabbedPane tabPane){
        this.tabPane = tabPane;
    }

    public void actionPerformed(ActionEvent evt) {

        Component selected = tabPane.getSelectedComponent();
        if (selected != null) {

            tabPane.remove(selected);
            // It would probably be worthwhile getting the source
            // casting it back to a JButton and removing
            // the action handler reference ;)

        }

    }
}
