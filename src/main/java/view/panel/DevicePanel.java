package view.panel;

import view.main.MainView;
import java.text.DecimalFormat;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.dto.ArduinoDevice;
import view.api.ArduinoDevicePanel;

/**
 *
 * @author nemanja.marjanovic
 */
public class DevicePanel implements ArduinoDevicePanel {

    private final VBox vbox = new VBox();
    private final Text title, active, mod, current;

    public DevicePanel(final MainView mainView, final Integer deviceNumber, ArduinoDevice arduinoDevice) {
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getStyleClass().add("leftPanelItem");

        title = new Text();
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        //   title.setFill(Color.SILVER);
        title.setTextAlignment(TextAlignment.LEFT);

        active = new Text();
        active.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        active.setTextAlignment(TextAlignment.RIGHT);

        mod = new Text();
        mod.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        mod.setTextAlignment(TextAlignment.RIGHT);

        current = new Text();
        current.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        current.setTextAlignment(TextAlignment.RIGHT);

        vbox.getChildren().addAll(title, active, mod, current);
        setArduinoDevice(arduinoDevice);
        //System.out.println(arduinoDevice);

        vbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                mainView.changeDevice(deviceNumber);
            }
        });
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        // this.arduinoDevice = arduinoDevice;

        if (arduinoDevice.isDeviceActive()) {
            active.setFill(Color.GREEN);
            //  vbox.getStyleClass().add("leftPanelItem");

        } else {
            active.setFill(Color.RED);
            //vbox.getStyleClass().add("leftPanelItemDisconnected");
        }
        active.setText(arduinoDevice.getArduinoDeviceActiveLabel());
        title.setText(arduinoDevice.getLocationName());
        mod.setText("MOD: " + arduinoDevice.getCurrentData().getMod().toString());

        DecimalFormat df = new DecimalFormat("##.00");
        String currentText = "T: " + df.format(arduinoDevice.getCurrentData().getTemperature()) + " C";
        currentText += "  V: " + df.format(arduinoDevice.getCurrentData().getHumidity()) + " %";
        currentText += "  C: " + arduinoDevice.getCurrentData().getCo2();

        current.setText(currentText);
    }

    @Override
    public Pane getPanelContent() {
        return vbox;
    }
}
