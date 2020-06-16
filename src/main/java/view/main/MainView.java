package view.main;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import controller.DeviceController;
import view.panel.CurrentStatePanel;
import view.panel.DevicePanel;
import view.EventPanel;
import view.panel.HistoryPanel;
import view.panel.LeftPanel;
import view.panel.StatusPanel;
import view.panel.TabPanel;

public class MainView extends Application implements Observer
{

    DeviceController dataProvider;
    CurrentStatePanel currentStatePanel;
    HistoryPanel historyPanel;
    EventPanel eventPanel;
    BorderPane border;
    TabPanel tabPanel;
    LeftPanel leftPanel;
    StatusPanel statusPanel;
    Integer selectedDevice;

    @Override
    public void update(Observable o, Object arg)
    {
        leftPanel.setArduinoDevice(dataProvider.getArduinoDevices());
        currentStatePanel.setArduinoDevice(dataProvider.getArduinoDevices().get(selectedDevice));
        currentStatePanel.setArduinoDevices(dataProvider.getArduinoDevices());
        statusPanel.setArduinoDevice(dataProvider.getArduinoDevices().get(selectedDevice));
        historyPanel.setArduinoDevice(dataProvider.getArduinoDevices().get(selectedDevice));

    }

    @Override
    public void start(Stage stage)
    {
        dataProvider = new DeviceController();
        dataProvider.addObserver(this);
        selectedDevice = 0;

        currentStatePanel = new CurrentStatePanel();
        currentStatePanel.setArduinoDevice(dataProvider.getArduinoDevices().get(selectedDevice));
        historyPanel = new HistoryPanel(dataProvider.getArduinoDevices().get(selectedDevice));
        eventPanel = new EventPanel();
        border = new BorderPane();
        tabPanel = new TabPanel(this);
        leftPanel = new LeftPanel(this, dataProvider.getArduinoDevices());
        statusPanel = new StatusPanel();

        border.setTop(tabPanel.getPanelContent());
        border.setLeft(leftPanel.getPanelContent());
        border.setCenter(currentStatePanel.getPanelContent());
        border.setBottom(statusPanel.getPanelContent());
        border.getStyleClass().add("main");
        border.setPrefWidth(800);

        Scene scene = new Scene(border);
        scene.getStylesheets().addAll("css/main.css", "css/chart.css");

        stage.setTitle("Pregled podataka sa senzora");
        stage.getIcons().add(new Image("images/red-star-icon.png"));
        stage.setScene(scene);

        stage.show();

        changeTabPanel(1);
        changeDevice(0);
        
        dataProvider.start();
    }

    public void changeTabPanel(int tab)
    {
        switch (tab)
        {
            case 1:
                border.setCenter(currentStatePanel.getPanelContent());
                break;
            case 2:
                border.setCenter(historyPanel.getPanelContent());
                break;
            case 3:
                border.setCenter(eventPanel.getVbox());
                break;
        }
    }

    public void changeDevice(Integer device)
    {
        this.selectedDevice = device;
        currentStatePanel.setArduinoDevice(dataProvider.getArduinoDevices().get(selectedDevice));

        for (DevicePanel devicePanel : leftPanel.getDevicePanels())
        {
            devicePanel.getPanelContent().getStyleClass().clear();
            devicePanel.getPanelContent().getStyleClass().add("leftPanelItem");
        }

        leftPanel.getDevicePanels().get(device).getPanelContent().getStyleClass().add("selectedLeftPanelItem");
    }

    @Override
    public void stop() throws Exception
    {
        dataProvider.stop();
        super.stop();
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
