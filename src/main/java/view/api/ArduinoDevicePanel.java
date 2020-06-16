/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.api;

import javafx.scene.layout.Pane;
import model.dto.ArduinoDevice;

/**
 *
 * @author nemanja.marjanovic
 */
public interface ArduinoDevicePanel
{
    void setArduinoDevice(ArduinoDevice arduinoDevice);
    Pane getPanelContent();
}
