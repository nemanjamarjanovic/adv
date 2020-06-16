
package view.panel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.dto.ArduinoDevice;
import view.api.ArduinoDevicePanel;

/**
 *
 * @author nemanja.marjanovic
 */
public class CurrentStatePanel implements ArduinoDevicePanel {

    private final VBox vbox = new VBox();
    private final Text temperatureText, humidityText, co2Text, acText, fanText,
            deviceText, locationText, modText, ipText, tchText, hchText, cchText;
    private final TextArea logTextArea = new TextArea();
    private final List<String> alarmlist = new ArrayList<>();

    public CurrentStatePanel() {
        vbox.getStyleClass().add("panel");

        HBox hboxtit = new HBox();
        hboxtit.getStyleClass().add("panelItem");

        Text locationTextTitle = new Text("Lokacija: ");
        locationTextTitle.getStyleClass().add("middlePanelText");
        locationText = new Text();
        locationText.getStyleClass().add("middlePanelTextBold");

        Text deviceTextTitle = new Text("Status: ");
        deviceTextTitle.getStyleClass().add("middlePanelText");
        deviceText = new Text();
        deviceText.getStyleClass().add("middlePanelTextBold");

        Text modTextTitle = new Text("Mod rada: ");
        modTextTitle.getStyleClass().add("middlePanelText");
        modText = new Text();
        modText.getStyleClass().add("middlePanelTextBold");

        Text ipTextTitle = new Text("IP adresa: ");
        ipTextTitle.getStyleClass().add("middlePanelText");
        ipText = new Text();
        ipText.getStyleClass().add("middlePanelTextBold");

        VBox vboxtit1 = new VBox();
        // vboxtit1.setPadding(new Insets(0, 20, 0, 0));
        vboxtit1.getChildren().addAll(locationTextTitle, deviceTextTitle, modTextTitle, ipTextTitle);
        vboxtit1.setAlignment(Pos.CENTER_RIGHT);

        VBox vboxtit2 = new VBox();
        //vboxtit2.setPadding(new Insets(0, 20, 0, 0));
        vboxtit2.getChildren().addAll(locationText, deviceText, modText, ipText);
        vboxtit2.setAlignment(Pos.CENTER_LEFT);

        hboxtit.getChildren().addAll(vboxtit1, vboxtit2);

        acText = new Text();
        HBox hboxk = acPanelItem("images/ac.png", acText, "Klima uredjaj");

        fanText = new Text();
        HBox hboxf = acPanelItem("images/fan.png", fanText, "Ventilator");

        temperatureText = new Text();
        tchText = new Text();
        HBox hboxt = sensorPanelItem("images/temperature.png", temperatureText, tchText, "Temperatura");

        humidityText = new Text();
        hchText = new Text();
        HBox hboxh = sensorPanelItem("images/humidity.png", humidityText, hchText, "Vlažnost");

        co2Text = new Text();
        cchText = new Text();
        HBox hboxc = sensorPanelItem("images/co2.png", co2Text, cchText, "CO2");

        HBox sensorHbox = new HBox();
        sensorHbox.setSpacing(10);
        sensorHbox.getChildren().addAll(hboxt, hboxh, hboxc);

        HBox sensorHbox2 = new HBox();
        sensorHbox2.setSpacing(10);
        sensorHbox2.getChildren().addAll(hboxtit, hboxk, hboxf);

        //logTextArea.setDisable(true);
        logTextArea.setEditable(false);
        logTextArea.getStyleClass().add("logTextArea");
       // logTextArea.setBackground(Background.EMPTY);
        // logTextArea.set
        //ScrollPane scrollPane = new ScrollPane();
        // scrollPane.setContent(logTextArea);
        // scrollPane.setFitToWidth(true);
        // scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        vbox.getChildren().addAll(sensorHbox2, sensorHbox, logTextArea);
    }

    private HBox sensorPanelItem(String image, Text value, Text change, String title) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("panelItem");

        VBox vboxit = new VBox();
        vboxit.setPadding(new Insets(0, 20, 0, 0));

        ImageView imageView = new ImageView(new Image(image));
        Text textValue = value;
        textValue.getStyleClass().add("largePanelText");
        Text textName = new Text(title);
        textName.getStyleClass().add("middlePanelText");
        Text textChange = change;
        textChange.getStyleClass().add("middlePanelText");

        HBox hbox3 = new HBox();
        hbox3.setSpacing(30);
        hbox3.getChildren().addAll(textValue, textChange);
        vboxit.getChildren().addAll(textName, textValue, textChange);
        vboxit.setAlignment(Pos.CENTER_LEFT);

        hbox.getChildren().addAll(imageView, vboxit);
        return hbox;
    }

    private HBox acPanelItem(String image, Text value, String title) {
        HBox hbox = new HBox();
        hbox.getStyleClass().add("panelItem");

        VBox vboxit = new VBox();
        vboxit.setPadding(new Insets(0, 20, 0, 0));

        ImageView imageView = new ImageView(new Image(image));
        Text textValue = value;
        textValue.getStyleClass().add("largePanelText");
        Text textName = new Text(title);
        textName.getStyleClass().add("middlePanelText");

        vboxit.getChildren().addAll(textName, textValue);
        vboxit.setAlignment(Pos.CENTER_LEFT);
        hbox.getChildren().addAll(imageView, vboxit);
        return hbox;
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        Double tempchDouble = arduinoDevice.getCurrentData().getTemperature() - arduinoDevice.getLastData().getTemperature();
        Double humchDouble = arduinoDevice.getCurrentData().getHumidity() - arduinoDevice.getLastData().getHumidity();
        Integer co2ch = arduinoDevice.getCurrentData().getCo2() - arduinoDevice.getLastData().getCo2();

        temperatureText.setText(getDoubleString(arduinoDevice.getCurrentData().getTemperature()) + " C");
        tchText.setText(getChangeString(tempchDouble));
        tchText.setFill(getChangeColor(tempchDouble));

        humidityText.setText(getDoubleString(arduinoDevice.getCurrentData().getHumidity()) + " %");
        hchText.setText(getChangeString(humchDouble));
        hchText.setFill(getChangeColor(humchDouble));

        co2Text.setText(arduinoDevice.getCurrentData().getCo2().toString());
        cchText.setText(((co2ch > 0) ? "+" : "") + co2ch);
        cchText.setFill(getChangeColor(co2ch.doubleValue()));

        acText.setText((arduinoDevice.getCurrentData().getAirConditionerAcitve()) ? "UKLJUCEN" : "ISKLJUCEN");
        acText.setFill((arduinoDevice.getCurrentData().getAirConditionerAcitve()) ? Color.RED : Color.BLUE);
        fanText.setText((arduinoDevice.getCurrentData().getVentilatorActive()) ? "UKLJUCEN" : "ISKLJUCEN");
        fanText.setFill((arduinoDevice.getCurrentData().getVentilatorActive()) ? Color.RED : Color.BLUE);

        locationText.setText(arduinoDevice.getLocationName());
        modText.setText(arduinoDevice.getCurrentData().getMod().toString());
        deviceText.setText(arduinoDevice.getArduinoDeviceActiveLabel());
        ipText.setText(arduinoDevice.getIPAddress());
    }

    public void setArduinoDevices(List<ArduinoDevice> arduinoDevices) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.YYYY");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

        for (ArduinoDevice arduinoDevice : arduinoDevices) {
            if (!arduinoDevice.isDeviceActive()) {
                String date = sdf1.format(arduinoDevice.getCurrentData().getTime());
                String time = sdf2.format(arduinoDevice.getCurrentData().getTime());

                while (alarmlist.size() > 10) {
                    alarmlist.remove(alarmlist.size() - 1);
                }
                alarmlist.add(0,date + " " + time + ": Uredjaj na lokaciji " + arduinoDevice.getLocationName() + " nije dostupan!\n");
            }
        }
        logTextArea.clear();
        for (String line : alarmlist) {
            logTextArea.appendText(line);
        }
    }

    private String getDoubleString(Double value) {
        return new DecimalFormat("00.00").format(value);
    }

    private String getChangeString(Double value) {
        return ((value > 0) ? "+" : "") + new DecimalFormat("00.00").format(value);
    }

    private Color getChangeColor(Double value) {
        if (value < 0) {
            return Color.RED;
        } else if (value > 0) {
            return Color.GREEN;
        } else {
            return Color.BLUE;
        }
    }

    @Override
    public Pane getPanelContent() {
        return vbox;
    }

}
