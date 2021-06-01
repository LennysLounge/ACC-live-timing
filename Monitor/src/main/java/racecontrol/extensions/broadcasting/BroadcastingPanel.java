/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.broadcasting;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import racecontrol.extensions.livetiming.tablemodels.LiveTimingTableModel;
import static racecontrol.visualisation.LookAndFeel.LINE_HEIGHT;
import racecontrol.visualisation.gui.LPButton;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.visualisation.gui.LPLabel;
import racecontrol.visualisation.gui.LPTable;

/**
 *
 * @author Leonard
 */
public class BroadcastingPanel
        extends LPContainer {

    /**
     * Extension.
     */
    private final BroadcastingExtension extension;
    /**
     * The table that display the live timing.
     */
    private final LPTable table = new LPTable();

    private final LPLabel hudLabel = new LPLabel("HUD");
    private final LPLabel carCameraLable = new LPLabel("Car Cameras");
    private final LPLabel tvCameraLable = new LPLabel("TV Cameras");
    private final LPLabel replayLabel = new LPLabel("Replay");

    private final Map<String, LPButton> hudButtons = new LinkedHashMap<>();
    private final Map<String, Map<String, LPButton>> cameraButtonsRef = new HashMap<>();
    private final List<LPButton> carCameraButtons = new LinkedList<>();
    private final List<LPButton> tvCameraButtons = new LinkedList<>();

    public BroadcastingPanel(BroadcastingExtension extension) {
        setName("BROADCASTING");

        this.extension = extension;

        table.setOverdrawForLastLine(false);
        //table.setCellClickAction((c, r) -> onCellClickAction(c, r));
        addComponent(table);

        hudLabel.setSize(200, LINE_HEIGHT);
        addComponent(hudLabel);
        carCameraLable.setSize(200, LINE_HEIGHT);
        addComponent(carCameraLable);
        tvCameraLable.setSize(200, LINE_HEIGHT);
        addComponent(tvCameraLable);
        //addComponent(replayLabel);

        addHUDButton("Blank", "Blank");
        addHUDButton("Basic", "Basic HUD");
        //addHUDButton("Help", "Help");
        addHUDButton("Times", "TimeTable");
        addHUDButton("BC", "Broadcasting");
        addHUDButton("Map", "TrackMap");

        addCarCameraButton("Chase", "Drivable", "Chase");
        addCarCameraButton("Far Chase", "Drivable", "FarChase");
        addCarCameraButton("Bonnet", "Drivable", "Bonnet");
        addCarCameraButton("Dash", "Drivable", "DashPro");
        addCarCameraButton("Cockpit", "Drivable", "Cockpit");
        addCarCameraButton("Helmet", "Drivable", "Helmet");
        addCarCameraButton("Onboard 0", "Onboard", "Onboard0");
        addCarCameraButton("Onboard 1", "Onboard", "Onboard1");
        addCarCameraButton("Onboard 2", "Onboard", "Onboard2");
        addCarCameraButton("Onboard 3", "Onboard", "Onboard3");
    }

    @Override
    public void onResize(int w, int h) {
        int tableHeight = (int) Math.max(2, Math.floor(h / LINE_HEIGHT) - 10);
        table.setPosition(0, 0);
        table.setSize(w, tableHeight * LINE_HEIGHT);

        hudLabel.setPosition(20, LINE_HEIGHT * tableHeight);
        carCameraLable.setPosition(210, LINE_HEIGHT * tableHeight);
        tvCameraLable.setPosition(210, LINE_HEIGHT * (tableHeight + 4));

        int x = 20;
        int y = 1;
        for (LPButton button : hudButtons.values()) {
            button.setPosition(x, (tableHeight + y) * LINE_HEIGHT);
            y++;
        }

        x = 210;
        y = 1;
        for (LPButton button : carCameraButtons) {
            button.setPosition(x, (tableHeight + y) * LINE_HEIGHT);
            y++;
            if (y > 3) {
                y = 1;
                x += button.getWidth() + 4;
            }
        }

        x = 210;
        y = 5;
        for (LPButton button : tvCameraButtons) {
            button.setPosition(x, (tableHeight + y) * LINE_HEIGHT);
            x += button.getWidth() + 4;
        }
    }

    public void setLiveTimingTableModel(LiveTimingTableModel model) {
        table.setTableModel(model);
        invalidate();
    }

    public void setCameraSets(Map<String, List<String>> sets) {
        for (String camSet : sets.keySet()) {
            if (!cameraButtonsRef.containsKey(camSet)) {
                cameraButtonsRef.put(camSet, new LinkedHashMap<>());
            }
            if (camSet.equals("set2")
                    || camSet.equals("set1")
                    || camSet.equals("Helicam")
                    || camSet.equals("pitlane")) {
                String name = "";
                if (camSet.equals("set1")) {
                    name = "TV 1";
                } else if (camSet.equals("set2")) {
                    name = "TV 2";
                } else if (camSet.equals("Helicam")) {
                    name = "Helicam";
                } else if (camSet.equals("pitlane")) {
                    name = "PIT";
                }
                LPButton b = new LPButtonCustom(name);
                b.setAction(() -> extension.setCameraSet(camSet, sets.get(camSet).get(0)));
                b.setSize(150, LINE_HEIGHT);
                addComponent(b);
                for (String camera : sets.get(camSet)) {
                    cameraButtonsRef.get(camSet).put(camera, b);
                }
                tvCameraButtons.add(b);
            }
        }
    }

    private void addHUDButton(String name, String page) {
        LPButton button = new LPButtonCustom(name);
        button.setAction(() -> extension.setHudPage(page));
        button.setSize(150, LINE_HEIGHT);
        hudButtons.put(page, button);
        addComponent(button);
    }

    private void addCarCameraButton(String name, String camSet, String camera) {
        LPButton b = new LPButtonCustom(name);
        b.setAction(() -> extension.setCameraSet(camSet, camera));
        b.setSize(150, LINE_HEIGHT);
        addComponent(b);
        if (!cameraButtonsRef.containsKey(camSet)) {
            cameraButtonsRef.put(camSet, new HashMap<>());
        }
        cameraButtonsRef.get(camSet).put(camera, b);
        carCameraButtons.add(b);
    }

    public void setActiveCameraSet(String activeCameraSet, String activeCamera) {
        if (cameraButtonsRef.containsKey(activeCameraSet)) {
            cameraButtonsRef.values().forEach(
                    list -> list.values().forEach(button -> button.setEnabled(true))
            );
            cameraButtonsRef.get(activeCameraSet).get(activeCamera).setEnabled(false);
        }
    }

    public void setActiveHudPage(String page) {
        if (hudButtons.containsKey(page)) {
            hudButtons.values().forEach(button -> button.setEnabled(true));
            hudButtons.get(page).setEnabled(false);
        }
    }

}
