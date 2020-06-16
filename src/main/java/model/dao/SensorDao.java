package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.SensorData;

public class SensorDao extends AbstractDao {

    public final static String SQL_CREATE_TABLE = " CREATE TABLE sensor_data "
            + " ( id IDENTITY PRIMARY KEY, serial_number VARCHAR, time TIMESTAMP, "
            + "temperature DOUBLE, humidity DOUBLE, co2 INTEGER, aircond BOOLEAN, ventilator BOOLEAN, mod INTEGER) ";
    public final static String SQL_DELETE_TABLE = "DROP TABLE sensor_data";
    private final static String SQL_INSERT = " INSERT INTO sensor_data (serial_number, time,"
            + " temperature, humidity, co2, aircond, ventilator, mod) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? ) ";
    private final static String SQL_QUERY_BETWEEN_TIMES = " SELECT * FROM sensor_data WHERE serial_number=? AND time BETWEEN ? AND ? ";
    private final static String SQL_QUERY_LAST = " SELECT * FROM sensor_data WHERE serial_number=? ORDER BY time DESC LIMIT 1 ";

    private final static Logger logger = Logger.getLogger(SensorDao.class.getName());

    public boolean insert(SensorData sensorData) {
        try {
            Class.forName(DRIVER_NAME);

            try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
                // preparedStatement.setLong(1, 0);
                preparedStatement.setString(1, sensorData.getSerialNumber());
                preparedStatement.setTimestamp(2, sensorData.getTime());
                preparedStatement.setDouble(3, sensorData.getTemperature());
                preparedStatement.setDouble(4, sensorData.getHumidity());
                preparedStatement.setInt(5, sensorData.getCo2());
                preparedStatement.setBoolean(6, sensorData.getAirConditionerAcitve());
                preparedStatement.setBoolean(7, sensorData.getVentilatorActive());
                preparedStatement.setInt(8, sensorData.getMod());
                return preparedStatement.execute();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.WARNING, "Greska pri upisu u bazu!", ex);

            return false;
        }
    }

    public List<SensorData> findBeetwenTimes(String serialNumber, Timestamp before,
            Timestamp after) {
        List<SensorData> result = new ArrayList<>();
        try {
            Class.forName(DRIVER_NAME);
            try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_BETWEEN_TIMES);
                preparedStatement.setString(1, serialNumber);
                preparedStatement.setTimestamp(2, before);
                preparedStatement.setTimestamp(3, after);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    result.add(new SensorData(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getTimestamp(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getInt(6),
                            resultSet.getBoolean(7),
                            resultSet.getBoolean(8),
                            resultSet.getInt(9)
                    ));
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.WARNING, "reska pri citanju iz baze", ex);
        }

        return result;
    }

    public SensorData getLast(String serialNumber) {
        try {
            Class.forName(DRIVER_NAME);
            try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY_LAST);
                preparedStatement.setString(1, serialNumber);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new SensorData(
                            resultSet.getLong(1),
                            resultSet.getString(2),
                            resultSet.getTimestamp(3),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            resultSet.getInt(6),
                            resultSet.getBoolean(7),
                            resultSet.getBoolean(8),
                            resultSet.getInt(9)
                    );
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            logger.log(Level.WARNING, "Greska pri citanju iz baze!", ex);
            try {
                new SensorDao().executeSQL(SensorDao.SQL_CREATE_TABLE);
            } catch (ClassNotFoundException | SQLException ex1) {
                logger.log(Level.WARNING, "Greska pri citanju iz baze!", ex);
            }
        }

        return null;
    }
}
