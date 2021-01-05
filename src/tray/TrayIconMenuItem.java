package tray;

import java.awt.*;

public class TrayIconMenuItem {
    private MenuItem menuItem;
    private boolean hide;

    public TrayIconMenuItem(MenuItem menuItem, boolean hide) {
        this.menuItem = menuItem;
        this.hide = hide;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }
}
