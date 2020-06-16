package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import model.dto.ArduinoDevice;
import model.dto.SensorData;

public class DeviceController extends Observable {

    //private static final Integer NETWORK_TIMEOUT = 12000;
    private static final Integer INITIAL_DELAY = 10000;
    private static final Integer UPDATE_PERIOD = 15000;

    private final List<ArduinoDevice> arduinoDevices;
    Timer timer = new Timer();

    public DeviceController() {
        super();
        this.arduinoDevices = new ArrayList<>();
        ArduinoDevice arduinoDevice = new ArduinoDevice("1000456", "Prostorija 1", "192.168.1.106", "8888");
        SensorData sensorData = new DataController().getLast(arduinoDevice.getSeriaNumber());
        if (sensorData != null) {
            arduinoDevice.setCurrentData(sensorData);
        }
        arduinoDevices.add(arduinoDevice);
    }

    private void updateSensorData(ArduinoDevice arduinoDevice) {
       String data = new NetworkController().getCurrentDataFromDevice(arduinoDevice);
        //String data = new NetworkController().getRandomValue(arduinoDevice);
        SensorData sensorData = new DataController().generateSensorData(data);

        if (sensorData != null) {
            arduinoDevice.setCurrentData(sensorData);
            arduinoDevice.setDeviceActive(Boolean.TRUE);
        } else {
            arduinoDevice.setDeviceActive(Boolean.FALSE);
        }
    }

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (ArduinoDevice arduinoDevice : arduinoDevices) {
                    updateSensorData(arduinoDevice);
                }
                setChanged();
                notifyObservers(arduinoDevices);
            }
        }, INITIAL_DELAY, UPDATE_PERIOD);
    }

    public void stop() {
        timer.cancel();
    }

    public List<ArduinoDevice> getArduinoDevices() {
        return arduinoDevices;
    }

}
