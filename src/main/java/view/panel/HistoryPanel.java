package view.panel;

import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.dao.SensorDao;
import model.dto.ArduinoDevice;
import model.dto.SensorData;
import view.api.ArduinoDevicePanel;
import view.chart.HumidityChart;
import view.chart.TemperatureChart;

/**
 *
 * @author nemanja.marjanovic
 */
public class HistoryPanel implements ArduinoDevicePanel, EventHandler<ActionEvent> {

    private final VBox vbox = new VBox();
    private final VBox vboxt = new VBox();
    private final ComboBox<String> combo = new ComboBox<>();
    private final DatePicker datePicker = new DatePicker(LocalDate.now());
    private LocalDate selectedDate = LocalDate.now();
    //  private final Text currentValue = new Text();
    //  private final Text currentDateValue = new Text();
    private ArduinoDevice arduinoDevice;
    private final Timer timer = new Timer();
    private TimerTask timerTask;
    private ObservableList<XYChart.Data<Date, Number>> dataList;

    public HistoryPanel(ArduinoDevice arduinoDevice) {
        this.arduinoDevice = arduinoDevice;

        vbox.getStyleClass().add("panel");
        Text comboTitle = new Text("Senzor: ");
        combo.getItems().addAll("Temperatura", "Vlaznost", "CO2");
        Button button = new Button("Osvjezi");

        HBox hBox1 = new HBox();
        hBox1.setSpacing(10);
        hBox1.setPadding(new Insets(20));
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(comboTitle, combo, datePicker, button);

        vbox.getChildren().addAll(hBox1, vboxt);
        combo.setOnAction(this);
        button.setOnAction(this);
        datePicker.setOnAction(this);

    }

    @Override
    public  void handle(ActionEvent t) {
        selectedDate = datePicker.getValue();
        vboxt.getChildren().clear();

        // timer.cancel();
//        if (timerTask != null) {
//            timerTask.cancel();
//        }

        if (combo.getSelectionModel().isSelected(0)) {
            dataList = new ObservableSequentialListWrapper<XYChart.Data<Date, Number>>(new ArrayList<XYChart.Data<Date, Number>>());
            dataList = getTemperatureData(dataList);
            vboxt.getChildren().add(new TemperatureChart(selectedDate, getTemperatureData(dataList)).getRoot());
//            timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    getTemperatureData(dataList);
//                }
//            };
        } else if (combo.getSelectionModel().isSelected(1)) {
            vboxt.getChildren().add(new HumidityChart(selectedDate, getHumidityData(dataList)).getRoot());
        } else if (combo.getSelectionModel().isSelected(2)) {
            //  vboxt.getChildren().add(new Co2Chart(selectedDate, getData(dataList)).getRoot());
        }

        //timer.schedule(timerTask, 3000, 3000);
    }

    
    public  ObservableList<XYChart.Data<Date, Number>> getTemperatureData(ObservableList<XYChart.Data<Date, Number>> dataList) {
        Timestamp t1 = Timestamp.valueOf(selectedDate.atStartOfDay());
        Timestamp t2 = Timestamp.valueOf(selectedDate.plusDays(1).atStartOfDay());

        for (SensorData sensorData : new SensorDao().findBeetwenTimes(arduinoDevice.getSeriaNumber(), t1, t2)) {
            if (sensorData.getTemperature() == 0d) {
                continue;
            }
            XYChart.Data<Date, Number> data = new XYChart.Data<Date, Number>(sensorData.getTime(), sensorData.getTemperature());
            if (!dataList.contains(data)) {;
                dataList.add(data);
            }
            //series.getData().add(new XYChart.Data(sensorData.getTime(), ));
        }

        // dataList = new ObservableSequentialListWrapper<>();
        return dataList;
    }

    public ObservableList<XYChart.Data<Date, Number>> getHumidityData(ObservableList<XYChart.Data<Date, Number>> dataList) {
        Timestamp t1 = Timestamp.valueOf(selectedDate.atStartOfDay());
        Timestamp t2 = Timestamp.valueOf(selectedDate.plusDays(1).atStartOfDay());

        for (SensorData sensorData : new SensorDao().findBeetwenTimes(arduinoDevice.getSeriaNumber(), t1, t2)) {
            if (sensorData.getTemperature() == 0d) {
                continue;
            }
            dataList.add(new XYChart.Data<Date, Number>(sensorData.getTime(), sensorData.getHumidity()));
            //series.getData().add(new XYChart.Data(sensorData.getTime(), ));
        }

        // dataList = new ObservableSequentialListWrapper<>();
        return dataList;
    }

    public ObservableList<SensorData> getData(ObservableList<SensorData> dataList) {
        Timestamp t1 = Timestamp.valueOf(selectedDate.atStartOfDay());
        Timestamp t2 = Timestamp.valueOf(selectedDate.plusDays(1).atStartOfDay());

        dataList = new ObservableSequentialListWrapper<>(new SensorDao().findBeetwenTimes(
                arduinoDevice.getSeriaNumber(), t1, t2));

        return dataList;
    }

    @Override
    public void setArduinoDevice(ArduinoDevice arduinoDevice) {
        this.arduinoDevice = arduinoDevice;
    }

    @Override
    public Pane getPanelContent() {
        return vbox;
    }

}
