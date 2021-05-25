/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.incidents;

import racecontrol.Main;
import racecontrol.client.data.SessionId;
import racecontrol.client.events.AfterPacketReceived;
import racecontrol.client.events.BroadcastingEventEvent;
import racecontrol.eventbus.Event;
import racecontrol.eventbus.EventBus;
import racecontrol.eventbus.EventListener;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.extensions.logging.LoggingExtension;
import racecontrol.client.data.AccBroadcastingData;
import racecontrol.client.data.BroadcastingEvent;
import racecontrol.client.data.enums.BroadcastingEventType;
import racecontrol.utility.TimeUtils;
import racecontrol.extensions.incidents.events.Accident;
import racecontrol.extensions.replayoffset.ReplayOffsetExtension;
import racecontrol.extensions.replayoffset.ReplayStart;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.visualisation.gui.LPContainer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Leonard
 */
public class IncidentExtension
        implements EventListener, AccClientExtension {

    /**
     * This classes logger.
     */
    private static final Logger LOG = Logger.getLogger(IncidentExtension.class.getName());
    /**
     * Reference to the client.
     */
    private final AccBroadcastingClient client;
    /**
     * The visualisation for this extension.
     */
    private final IncidentPanel panel;
    /**
     * Last accident that is waiting to be commited.
     */
    private IncidentInfo stagedAccident = null;
    /**
     * List of accidents that have happened.
     */
    private List<IncidentInfo> accidents = new LinkedList<>();
    /**
     * Table model for the incident panel table.
     */
    private final IncidentTableModel model = new IncidentTableModel();
    /**
     * Flag indicates that the replay offset is known.
     */
    private boolean replayTimeKnown = false;

    public IncidentExtension() {
        //LogginExtension loggingExtension = dependsOn( LogginExtension.class );
        this.client = Main.getClient();
        this.panel = new IncidentPanel(this);
        EventBus.register(this);
    }

    @Override
    public LPContainer getPanel() {
        if (GeneralExtentionConfigPanel.getInstance().isIncidentLogEnabled()) {
            return panel;
        }
        return null;
    }

    public AccBroadcastingData getModel() {
        return client.getModel();
    }

    public IncidentTableModel getTableModel() {
        return model;
    }

    public List<IncidentInfo> getAccidents() {
        List<IncidentInfo> a = new LinkedList<>(accidents);
        Collections.reverse(a);
        return Collections.unmodifiableList(a);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AfterPacketReceived) {
            afterPacketReceived(((AfterPacketReceived) e).getType());
            if (!replayTimeKnown && ReplayOffsetExtension.requireSearch()) {
                panel.enableSearchButton();
            }
        } else if (e instanceof BroadcastingEventEvent) {
            BroadcastingEvent event = ((BroadcastingEventEvent) e).getEvent();
            if (event.getType() == BroadcastingEventType.ACCIDENT) {
                onAccident(event);
            }
        } else if (e instanceof ReplayStart) {
            replayTimeKnown = true;
            panel.setReplayOffsetKnown();
            updateAccidentsWithReplayTime();
        }
    }

    public void afterPacketReceived(byte type) {
        if (stagedAccident != null) {
            long now = System.currentTimeMillis();
            if (now - stagedAccident.getSystemTimestamp() > 1000) {
                commitAccident(stagedAccident);
                stagedAccident = null;
            }
        }
    }

    public void onAccident(BroadcastingEvent event) {
        float sessionTime = client.getModel().getSessionInfo().getSessionTime();
        String logMessage = "Accident: #" + client.getModel().getCar(event.getCarId()).getCarNumber()
                + "\t" + TimeUtils.asDuration(sessionTime)
                + "\t" + TimeUtils.asDuration(ReplayOffsetExtension.getReplayTimeFromConnectionTime(event.getTimeMs()));
        LoggingExtension.log(logMessage);
        LOG.info(logMessage);

        SessionId sessionId = client.getSessionId();
        if (stagedAccident == null) {
            stagedAccident = new IncidentInfo(sessionTime,
                    client.getModel().getCar(event.getCarId()),
                    sessionId);
        } else {
            float timeDif = stagedAccident.getSessionLatestTime() - sessionTime;
            if (timeDif > 1000) {
                commitAccident(stagedAccident);
                stagedAccident = new IncidentInfo(sessionTime,
                        client.getModel().getCar(event.getCarId()),
                        sessionId);
            } else {
                stagedAccident = stagedAccident.addCar(sessionTime,
                        client.getModel().getCar(event.getCarId()),
                        System.currentTimeMillis());
            }
        }
    }

    public void addEmptyAccident() {
        commitAccident(new IncidentInfo(client.getModel().getSessionInfo().getSessionTime(),
                client.getSessionId()));
    }

    private void commitAccident(IncidentInfo a) {
        List<IncidentInfo> newAccidents = new LinkedList<>();
        newAccidents.addAll(accidents);
        newAccidents.add(a);
        accidents = newAccidents;
        model.setAccidents(accidents);

        EventBus.publish(new Accident(a));
        panel.invalidate();
    }

    @Override
    public void removeExtension() {
        EventBus.unregister(this);
    }

    private void updateAccidentsWithReplayTime() {
        SessionId currentSessionId = Main.getClient().getSessionId();
        List<IncidentInfo> newAccidents = new LinkedList<>();
        for (IncidentInfo incident : accidents) {
            if (incident.getSessionID().equals(currentSessionId)) {
                newAccidents.add(incident.withReplayTime(
                        ReplayOffsetExtension.getReplayTimeFromSessionTime((int) incident.getSessionEarliestTime())
                ));
            }
        }
        accidents = newAccidents;
        model.setAccidents(accidents);
        panel.invalidate();

        if (stagedAccident != null) {
            stagedAccident = stagedAccident.withReplayTime(
                    ReplayOffsetExtension.getReplayTimeFromSessionTime((int) stagedAccident.getSessionEarliestTime())
            );
        }
    }

}
