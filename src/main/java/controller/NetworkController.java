/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import model.dto.ArduinoDevice;

/**
 *
 * @author nemanja.marjanovic
 */
public class NetworkController {

    private static final Integer NETWORK_TIMEOUT = 12000;

    public String getCurrentDataFromDevice(ArduinoDevice arduinoDevice) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            socket.setSoTimeout(NETWORK_TIMEOUT);
            InetSocketAddress address = new InetSocketAddress(arduinoDevice.getIPAddress(), Integer.parseInt(arduinoDevice.getPort()));
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket("V".getBytes(), "V".getBytes().length, address);
            socket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            socket.close();
            String response = new String(receivePacket.getData());
            System.out.println(response);

            return response;
        } catch (IOException e) {
            if (socket != null) {
                socket.close();
            }
            System.err.println("Uredjaj ne odgorvara na poruke!");
            return null;
        }
    }

    public String getRandomValue(ArduinoDevice arduinoDevice) {
        Random random = new Random(System.currentTimeMillis());
        if (random.nextInt(3) == 2) {
            return null;
        }
        double temp = random.nextDouble() * 10 + 12;
        double vl = random.nextDouble() * 30 + 70;
        int co2 = random.nextInt(1200) + 4800;
        Boolean k = random.nextBoolean();
        Boolean v = random.nextBoolean();
        int mod = random.nextInt(1) + 3;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss");
        String result = arduinoDevice.getSeriaNumber()
                + ";" + temp + ";" + vl + ";" + co2 + ";"
                + ((k) ? "DA" : "NE") + ";" + ((v) ? "DA" : "NE") + ";" + mod + ";" + sdf.format(new Timestamp(System.currentTimeMillis()));
        System.out.println(result);
        try {
            Thread.sleep(random.nextInt(100));
        } catch (InterruptedException ex) {
        }
        return result;
    }
}
