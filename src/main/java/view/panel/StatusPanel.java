
package view.panel;

import java.text.SimpleDateFormat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.dto.ArduinoDevice;
import view.api.ArduinoDevicePanel;

/**
 *
 * @author nemanja
 */
public class StatusPanel implements ArduinoDevicePanel
{

    private final HBox hbox;
    private final Text status;

    public StatusPanel()
    {
        hbox = new HBox();
        hbox.setPrefHeight(20);
       // hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getStyleClass().add("statusPanel");
        status = new Text("Inicijalizacija...");
       // status.setFill(Color.SILVER);
        hbox.getChildren().addAll(status);
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice)
    {
        String date = new SimpleDateFormat("dd.MM.YYYY").format(arduinoDevice.getCurrentData().getTime());
        String time = new SimpleDateFormat("HH:mm:ss").format(arduinoDevice.getCurrentData().getTime());
        status.setText("Posljednje mjerenje " + date + " u " + time);
    }

    @Override
    public Pane getPanelContent()
    {
        return hbox;
    }
}
