/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.chart;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import model.dto.SensorData;

/**
 *
 * @author nemanja.marjanovic
 */
public class TemperatureChart extends SensorChart
{

    public TemperatureChart(LocalDate selectedDate, ObservableList<XYChart.Data<Date, Number>> result)
    {
        super(selectedDate, result);
    }

    @Override
    protected NumberAxis createYAxis()
    {
        return new NumberAxis("Temperatura (C)", 0, 30, 1);
    }

    @Override
    protected Number getYValue(SensorData sensorData)
    {
        return sensorData.getTemperature();
    }

    @Override
    protected Double getYLowerBound()
    {
        return 0d;
    }

    @Override
    protected Double getYUpperBound()
    {
        return 30d;
    }

}
