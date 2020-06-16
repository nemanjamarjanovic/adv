package controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.SensorDao;
import model.dto.SensorData;

public class DataController {

    private final static Logger logger = Logger.getLogger(DataController.class.getName());

    SensorData generateSensorData(String data) {

        try {
            if (data != null) {

                data = data.replace(",", ".");
                data = data.trim();
                String[] values = data.split(";");
                for (String value : values) {
                    value = value.trim();
                }

                Timestamp ct = new Timestamp(0);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
                ct.setTime(sdf.parse(values[7]).getTime());

                SensorData result = new SensorData(
                        null, values[0], ct,
                        Double.parseDouble(values[1]),
                        Double.parseDouble(values[2]),
                        Integer.parseInt(values[3]),
                        values[4].equals("DA"),
                        values[5].equals("DA"),
                        Integer.parseInt(values[6]));

                boolean res = new SensorDao().insert(result);

                return result;
            }
            logger.log(Level.WARNING, "Nema rezlutata!");
            return null;

        } catch (Exception e) {
            logger.log(Level.WARNING, "Greska pri parsiranju rezlutata!", e);
            return null;

        }

    }

    public SensorData getLast(String serialNumber) {
        return new SensorDao().getLast(serialNumber);
    }

}
