/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.base.timing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.RealtimeInfo;
import racecontrol.client.data.enums.CarLocation;
import racecontrol.client.events.RealtimeCarUpdate;
import racecontrol.client.events.RealtimeUpdate;
import racecontrol.client.events.SessionChanged;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.eventbus.Event;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class TimingExtension
        extends AccClientExtension {

    private static final Logger LOG = Logger.getLogger(TimingExtension.class.getName());

    private TimingPanel panel;

    private final Map<Integer, List<Float>> rawRaceDistanceHistory = new HashMap<>();
    private final Map<Integer, List<Float>> raceDistanceHistory = new HashMap<>();

    private final Map<Integer, RealtimeInfo> prevRealtime = new HashMap<>();

    private final Map<Integer, Float> raceDistance = new HashMap<>();
    private Map<Integer, Integer> syncCorrection = new HashMap<>();

    private int syncType = 0;

    public TimingExtension(AccBroadcastingClient client) {
        super(client);
        panel = new TimingPanel(this);
    }

    @Override
    public LPContainer getPanel() {
        return panel;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof RealtimeCarUpdate) {
            RealtimeInfo info = ((RealtimeCarUpdate) e).getInfo();
            calculateRaceDistance(info);

        } else if (e instanceof RealtimeUpdate) {
            int focusedCarId = ((RealtimeUpdate) e).getSessionInfo().getFocusedCarIndex();

            if (rawRaceDistanceHistory.containsKey(focusedCarId)) {
                panel.rawRaceDistance = rawRaceDistanceHistory.get(focusedCarId);
                panel.invalidate();
            }

            if (raceDistanceHistory.containsKey(focusedCarId)) {
                panel.raceDistance = raceDistanceHistory.get(focusedCarId);
                panel.invalidate();
            }
        } else if (e instanceof SessionChanged) {
            rawRaceDistanceHistory.clear();
            raceDistanceHistory.clear();
            syncCorrection.clear();
            prevRealtime.clear();
        }
    }

    private void calculateRaceDistance(RealtimeInfo info) {
        if (!rawRaceDistanceHistory.containsKey(info.getCarId())) {
            rawRaceDistanceHistory.put(info.getCarId(), new LinkedList<>());
        }
        float rawRaceDistance = info.getSplinePosition() + info.getLaps();
        this.rawRaceDistanceHistory.get(info.getCarId()).add(rawRaceDistance);

        calculateSyncIssueCorrection(info);
        prevRealtime.put(info.getCarId(), info);

        float newRaceDistance = info.getSplinePosition()
                + info.getLaps()
                + syncCorrection.getOrDefault(info.getCarId(), 0);
        raceDistance.put(info.getCarId(), newRaceDistance);
        if (!raceDistanceHistory.containsKey(info.getCarId())) {
            raceDistanceHistory.put(info.getCarId(), new LinkedList<>());
        }
        raceDistanceHistory.get(info.getCarId()).add(newRaceDistance);
    }

    private void calculateSyncIssueCorrection(RealtimeInfo info) {
        RealtimeInfo prevInfo = prevRealtime.getOrDefault(info.getCarId(), null);
        if (prevInfo == null) {
            LOG.info("null prev info");
            return;
        }

        float newRD = info.getSplinePosition() + info.getLaps();
        float prevRD = prevInfo.getSplinePosition() + prevInfo.getLaps();
        float diff = newRD - prevRD;
        //if the race distance is significantly different from the last update
        //we have a sync issue and we must correct it.
        if (Math.abs(diff) > 0.5) {

            //if the sync type is unknown we try and find it else we return.
            if (syncType == 0) {
                findSyncType(prevInfo, info);
                if (syncType == 0) {
                    return;
                }
            }

            boolean isLapUpdate = (info.getLaps() == prevInfo.getLaps());
            if (isLapUpdate) {
                if (syncType == 1) {
                    syncCorrection.put(info.getCarId(), 1);
                } else if (syncType == -1) {
                    syncCorrection.put(info.getCarId(), 0);
                }
            } else {
                if (syncType == 1) {
                    syncCorrection.put(info.getCarId(), 0);
                } else if (syncType == -1) {
                    syncCorrection.put(info.getCarId(), -1);
                }
            }

            LOG.info("Sync event for car #" + getClient().getModel().getCar(info.getCarId()).getCarNumber()
                    + "\tc: " + syncCorrection.get(info.getCarId())
                    + "\ttype: " + syncType
                    + "\tin pits: " + info.getLocation().name()
            );
        }
    }

    private void findSyncType(RealtimeInfo prev, RealtimeInfo now) {

        boolean isLapChange = (prev.getLaps() != now.getLaps());
        LOG.info("finding sync type is lap change: " + isLapChange);
        if (!isLapChange) {
            return;
        }
        if (now.getSplinePosition() < 0.5) {
            syncType = 1;
        } else {
            syncType = -1;
        }
        LOG.info("Sync type set to: " + syncType);
    }

}
