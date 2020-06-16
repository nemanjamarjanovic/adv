
package view.panel;

import view.main.MainView;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.dto.ArduinoDevice;
import view.api.ArduinoDevicePanel;

/**
 *
 * @author nemanja.marjanovic
 */
public class LeftPanel implements ArduinoDevicePanel
{

    private final VBox vbox = new VBox();
    private final List<DevicePanel> devicePanels = new ArrayList<>();

    public LeftPanel(MainView parent, List<ArduinoDevice> arduinoDevices)
    {
        vbox.setPrefWidth(200);
        vbox.setSpacing(30);
        vbox.getStyleClass().add("leftPanel");
        vbox.setPadding(new Insets(0, 10, 10, 0));

        for (int i = 0; i < arduinoDevices.size(); i++)
        {
            DevicePanel devicePanel = new DevicePanel(parent, i, arduinoDevices.get(i));
            devicePanels.add(devicePanel);
            vbox.getChildren().add(devicePanel.getPanelContent());
        }
    }

    public void setArduinoDevice(List<ArduinoDevice> arduinoDevices)
    {
        for (int i = 0; i < devicePanels.size(); i++)
        {
            devicePanels.get(i).setArduinoDevice(arduinoDevices.get(i));
        }
    }

    public List<DevicePanel> getDevicePanels()
    {
        return devicePanels;
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice)
    {

    }

    @Override
    public Pane getPanelContent()
    {
        return vbox;
    }

}
