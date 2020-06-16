/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.panel;

import view.main.MainView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.dto.ArduinoDevice;
import view.api.ArduinoDevicePanel;

/**
 *
 * @author nemanja.marjanovic
 */
public class TabPanel implements ArduinoDevicePanel
{

    VBox vbox1;
    VBox vbox2;
    VBox vbox3;
    HBox hbox;
    MainView mainView;

    public TabPanel(MainView mainView)
    {
        this.mainView = mainView;

        hbox = new HBox();
        hbox.getStyleClass().add("tabPanel");

        vbox1 = tab("Trenutno stanje", 1);
        vbox2 = tab("Istorija", 2);
        vbox3 = tab("Posljednji dogadjaji", 3);
        vbox1.getStyleClass().clear();
        vbox1.getStyleClass().add("tab-selected");

        hbox.getChildren().addAll(vbox1, vbox2, vbox3);
    }

    private VBox tab(String title, final int tab)
    {
        VBox vbox = new VBox();
        vbox.getStyleClass().clear();
        vbox.getStyleClass().add("tab");
        vbox.setPrefWidth(150);
        Text text = new Text(title);
        vbox.getChildren().add(text);

        vbox.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                VBox vbox = (VBox) t.getSource();
                vbox1.getStyleClass().clear();
                vbox1.getStyleClass().add("tab");
                vbox2.getStyleClass().clear();
                vbox2.getStyleClass().add("tab");
                vbox3.getStyleClass().clear();
                vbox3.getStyleClass().add("tab");
                vbox.getStyleClass().clear();
                vbox.getStyleClass().add("tab-selected");
                mainView.changeTabPanel(tab);
            }
        });

        return vbox;
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice)
    {

    }

    @Override
    public Pane getPanelContent()
    {
        return hbox;
    }

}
