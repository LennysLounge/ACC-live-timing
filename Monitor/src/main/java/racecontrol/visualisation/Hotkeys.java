/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.visualisation;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import processing.event.KeyEvent;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.CarInfo;

/**
 *
 * @author Leonard
 */
public class Hotkeys {

    public static final Logger LOG = Logger.getLogger(Hotkeys.class.getName());

    public static final int KEY_1 = 49;
    public static final int KEY_2 = 50;
    public static final int KEY_3 = 51;
    public static final int KEY_F1 = 112;
    public static final int KEY_F2 = 113;
    public static final int KEY_F3 = 114;
    public static final int KEY_F6 = 117;
    public static final int KEY_LEFT = 37;
    public static final int KEY_RIGHT = 39;
    public static final int KEY_UP = 38;
    public static final int KEY_DOWN = 40;

    private AccBroadcastingClient client;

    public Hotkeys() {
    }

    public void setClient(AccBroadcastingClient client) {
        this.client = client;
    }

    public void handleHotkeys(KeyEvent event) {
        if (client == null) {
            return;
        }

        switch (event.getKeyCode()) {
            case KEY_1:
                client.sendSetCameraRequest("Drivable", "Cockpit");
                break;
            case KEY_2:
                client.sendSetCameraRequest("Drivable", "Bonnet");
                break;
            case KEY_3:
                client.sendSetCameraRequest("Drivable", "Chase");
                break;
            case KEY_F1:
                List<String> cameras = Arrays.asList("Cockpit", "Dash", "Helmet", "Bonnet", "DashPro", "Chase", "FarChase");
                String activeCamera = client.getModel().getSessionInfo().getActiveCamera();
                int index = cameras.indexOf(activeCamera);
                client.sendSetCameraRequest("Drivable", cameras.get((index + 1) % cameras.size()));
                break;
            case KEY_F2:
                List<String> hudPages = Arrays.asList("Blank", "Basic HUD", "Help", "TimeTable", "Broadcasting", "TrackMap");
                String activeHud = client.getModel().getSessionInfo().getCurrentHudPage();
                index = hudPages.indexOf(activeHud);
                client.sendSetHudPageRequest(hudPages.get((index + 1) % hudPages.size()));
                break;
            case KEY_F3:
                //camera set "setVR" is not used and will default switch between
                //set1 set2 helicam and pitlane. We can use this to emulate
                //the F3 functionality.
                client.sendSetCameraRequest("setVR", "-");
                break;
            case KEY_F6:
                cameras = Arrays.asList("Onboard0", "Onboard1", "Onboard2", "Onboard3");
                activeCamera = client.getModel().getSessionInfo().getActiveCamera();
                index = cameras.indexOf(activeCamera);
                client.sendSetCameraRequest("Onboard", cameras.get((index + 1) % cameras.size()));
                break;
            case KEY_LEFT:
                if (event.isShiftDown()) {
                    moveFocusRelative(1);
                }
                break;
            case KEY_RIGHT:
                if (event.isShiftDown()) {
                    moveFocusRelative(-1);
                }
                break;
            case KEY_UP:
                if (event.isShiftDown()) {
                    moveFocusAbsolute(1);
                }
                break;
            case KEY_DOWN:
                if (event.isShiftDown()) {
                    moveFocusAbsolute(-1);
                }
                break;
        }
    }

    private void moveFocusRelative(int direction) {
        int focusedCarIndex = client.getModel().getSessionInfo().getFocusedCarIndex();
        CarInfo focusedCar = client.getModel().getCar(focusedCarIndex);

        List<CarInfo> cars = client.getModel().getCarsInfo().values().stream()
                .sorted((c1, c2) -> compareSplinePos(c1, c2))
                .collect(Collectors.toList());
        int index = cars.indexOf(focusedCar);
        int target = index + direction;
        if (target < 0) {
            target = cars.size();
        }
        if(target >= cars.size()){
            target = 0;
        }
        client.sendChangeFocusRequest(cars.get(target).getCarId());
    }

    private void moveFocusAbsolute(int direction) {
        int focusedCarIndex = client.getModel().getSessionInfo().getFocusedCarIndex();
        CarInfo focusedCar = client.getModel().getCar(focusedCarIndex);

        List<CarInfo> cars = client.getModel().getCarsInfo().values().stream()
                .sorted((c1, c2) -> c2.getRealtime().getPosition() - c1.getRealtime().getPosition())
                .collect(Collectors.toList());
        int index = cars.indexOf(focusedCar);
        int target = index + direction;
        if (target < 0) {
            target = cars.size();
        }
        if(target >= cars.size()){
            target = 0;
        }
        client.sendChangeFocusRequest(cars.get(target).getCarId());
    }
    
    private int compareSplinePos(CarInfo c1, CarInfo c2){
        float s1 = c1.getRealtime().getSplinePosition();
        float s2 = c2.getRealtime().getSplinePosition();
        if(s1 > s2){
            return 1;
        }
        if(s2 > s1){
            return -1;
        }
        return 0;
    }
}
