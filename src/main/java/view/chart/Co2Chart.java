///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package view.chart;
//
//import java.time.LocalDate;
//import java.util.List;
//import javafx.scene.chart.NumberAxis;
//import model.dto.SensorData;
//
///**
// *
// * @author nemanja.marjanovic
// */
//public class Co2Chart extends SensorChart
//{
//
//    public Co2Chart(LocalDate selectedDate, List<SensorData> result)
//    {
//        super(selectedDate, result);
//    }
//
//    @Override
//    protected NumberAxis createYAxis()
//    {
//        return new NumberAxis("CO2", 2000, 6000, 500);
//    }
//
//    @Override
//    protected Number getYValue(SensorData sensorData)
//    {
//        return sensorData.getCo2();
//    }
//
//    @Override
//    protected Double getYLowerBound()
//    {
//        return 2000d;
//    }
//
//    @Override
//    protected Double getYUpperBound()
//    {
//         return 6000d;
//    }
//
//}
