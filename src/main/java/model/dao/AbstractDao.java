package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author nemanja.marjanovic
 */
public abstract class AbstractDao {

    protected final static String DRIVER_NAME = "org.h2.Driver";
    protected final static String DATABASE_URL = "jdbc:h2:~/arduino/arduino_sensor_data";

    public boolean executeSQL(String SQL) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER_NAME);
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.execute();
        }
        return true;
    }
}
