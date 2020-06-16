///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package model.dao;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import model.dto.ArduinoDevice;
//
///**
// *
// * @author nemanja.marjanovic
// */
//public class ArduinoDeviceDao extends AbstractDao
//{
//
//    public final static String SQL_CREATE_TABLE = " CREATE TABLE arduino_device ( "
//            + "serial_number VARCHAR PRIMARY KEY, "
//            + "location_name VARCHAR, "
//            + "ip_address VARCHAR, "
//            + "port VARCHAR, "
//            + "device_active BOOLEAN, "
//            + "temperature_sensor_active BOOLEAN, "
//            + "humidity_sensor_active BOOLEAN, "
//            + "co2_sensor_active BOOLEAN, "
//            + "last_synhronization_time TIMESTAMP "
//            + " ) ";
//    private final static String SQL_INSERT = " INSERT INTO arduino_device VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
//    private final static String SQL_DELETE_TABLE = "DROP TABLE arduino_device";
//    private final static String SQL_QUERY_BY_SERIAL_NUMBER = " SELECT * FROM arduino_device WHERE serial_number=? ";
//    private final static String SQL_QUERY_ALL = " SELECT * FROM arduino_device ";
//
//    public boolean insert(ArduinoDevice arduinoDevice) throws ClassNotFoundException, SQLException
//    {
//         Class.forName(DRIVER_NAME);
//        try (Connection connection = DriverManager.getConnection(DATABASE_URL))
//        {
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
//            preparedStatement.setString(1, arduinoDevice.getSeriaNumber());
//            preparedStatement.setString(2, arduinoDevice.getLocationName());
//            preparedStatement.setString(3, arduinoDevice.getIPAddress());
//            preparedStatement.setString(4, arduinoDevice.getPort());
//            preparedStatement.setBoolean(5, arduinoDevice.isDeviceActive());
//            preparedStatement.setBoolean(6, arduinoDevice.isTemperatureSensorActive());
//            preparedStatement.setBoolean(7, arduinoDevice.isHumiditySensorActive());
//            preparedStatement.setBoolean(8, arduinoDevice.isCo2SensorActive());
//            return preparedStatement.execute();
//        }
//    }
////
////    public List<ArduinoDevice> findAll() throws ClassNotFoundException, SQLException
////    {
////        List<ArduinoDevice> result = new ArrayList<>();
////        initDriver();
////        try (Connection connection = DriverManager.getConnection(DATABASE_URL))
////        {
////            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_ALL);
////
////            ResultSet resultSet = preparedStatement.executeQuery();
////            while (resultSet.next())
////            {
////                result.add(new ArduinoDevice(
////                        resultSet.getString(1),
////                        resultSet.getString(2),
////                        resultSet.getString(3),
////                        resultSet.getString(4),
////                        resultSet.getBoolean(5),
////                        resultSet.getBoolean(6),
////                        resultSet.getBoolean(7),
////                        resultSet.getBoolean(8),
////                        resultSet.getTimestamp(9)));
////            }
////        }
////        return result;
////    }
////
////    public ArduinoDevice findBySerialNumber(String serialNumber) throws ClassNotFoundException, SQLException
////    {
////        initDriver();
////        try (Connection connection = DriverManager.getConnection(DATABASE_URL))
////        {
////            PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_BY_SERIAL_NUMBER);
////            preparedStatement.setString(1, serialNumber);
////            ResultSet resultSet = preparedStatement.executeQuery();
////            if (resultSet.next())
////            {
////                return new ArduinoDevice(
////                        resultSet.getString(1),
////                        resultSet.getString(2),
////                        resultSet.getString(3),
////                        resultSet.getString(4),
////                        resultSet.getBoolean(5),
////                        resultSet.getBoolean(6),
////                        resultSet.getBoolean(7),
////                        resultSet.getBoolean(8),
////                        resultSet.getTimestamp(9));
////            }
////        }
////        return null;
////    }
//
//}
