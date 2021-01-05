package tools;

import tray.Tray;

import java.util.List;
import java.util.TimerTask;

public class CounterTimerTask extends TimerTask {
    private int data;
    private List<Tray> trayList;

    public CounterTimerTask(List<Tray> trayList) {
        this.trayList = trayList;
        data = 0;
    }
    @Override
    public void run() {
        trayList.forEach(tray -> tray.askToElasticsearch());
        System.out.println(" ");
    }
}
