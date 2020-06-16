package model.dto;

public class ArduinoDevice {

    private final String seriaNumber;
    private final String locationName;
    private final String IPAddress;
    private final String port;
    private Boolean deviceActive;
    private SensorData currentData;
    private SensorData lastData;

    public ArduinoDevice(
            String seriaNumber,
            String locationName,
            String IPAddress,
            String port) {
        this.seriaNumber = seriaNumber;
        this.locationName = locationName;
        this.IPAddress = IPAddress;
        this.port = port;
        this.deviceActive = false;
        this.currentData = new SensorData();
        this.lastData = new SensorData();
    }

    public String getArduinoDeviceActiveLabel() {
        return (isDeviceActive()) ? "AKTIVAN" : "NEAKTIVAN";
    }

    public String getSeriaNumber() {
        return seriaNumber;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public String getPort() {
        return port;
    }

    public Boolean isDeviceActive() {
        return deviceActive;
    }

    public void setDeviceActive(Boolean deviceActive) {
        this.deviceActive = deviceActive;
    }

    public SensorData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(SensorData currentData) {
        this.lastData = this.currentData;
        this.currentData = currentData;
    }

    public SensorData getLastData() {
        return lastData;
    }

}
