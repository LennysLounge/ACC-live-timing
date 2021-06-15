/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.base.incidentdetection;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.BroadcastingEvent;
import racecontrol.client.data.SessionId;
import racecontrol.client.data.enums.BroadcastingEventType;
import racecontrol.client.events.AfterPacketReceived;
import racecontrol.client.events.BroadcastingEventEvent;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.eventbus.Event;
import racecontrol.eventbus.EventBus;
import racecontrol.extensions.base.incidentdetection.events.Accident;
import racecontrol.extensions.logging.LoggingExtension;
import racecontrol.extensions.base.replayoffset.ReplayOffsetExtension;
import racecontrol.utility.TimeUtils;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class IncidentDetectionExtension
        extends AccClientExtension {

    /**
     * This class' logger.
     */
    public static final Logger LOG = Logger.getLogger(IncidentDetectionExtension.class.getName());
    /**
     * Last accident that is waiting to be commited.
     */
    private IncidentInfo stagedAccident = null;
    /**
     * List of accidents that have happened.
     */
    private List<IncidentInfo> accidents = new LinkedList<>();
    /**
     * Reference to the logging extension.
     */
    private final LoggingExtension loggingExtension;
    /**
     * Reference to the replay offset extension
     */
    private final ReplayOffsetExtension replayOffsetExtension;

    public IncidentDetectionExtension(AccBroadcastingClient client) {
        super(client);
        loggingExtension = client.getOrCreateExtension(LoggingExtension.class);
        replayOffsetExtension = client.getOrCreateExtension(ReplayOffsetExtension.class);
    }

    @Override
    public LPContainer getPanel() {
        return null;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof AfterPacketReceived) {
            afterPacketReceived(((AfterPacketReceived) e).getType());
        } else if (e instanceof BroadcastingEventEvent) {
            BroadcastingEvent event = ((BroadcastingEventEvent) e).getEvent();
            if (event.getType() == BroadcastingEventType.ACCIDENT) {
                onAccident(event);
            }
        }
    }

    public void onAccident(BroadcastingEvent event) {
        float sessionTime = getClient().getModel().getSessionInfo().getSessionTime();
        String logMessage = "Accident: #" + getClient().getModel().getCar(event.getCarId()).getCarNumber()
                + "\t" + TimeUtils.asDuration(sessionTime)
                + "\t" + TimeUtils.asDuration(replayOffsetExtension.getReplayTimeFromConnectionTime(event.getTimeMs()));
        loggingExtension.log(logMessage);
        LOG.info(logMessage);

        SessionId sessionId = getClient().getSessionId();
        if (stagedAccident == null) {
            stagedAccident = new IncidentInfo(sessionTime,
                    replayOffsetExtension.isReplayTimeKnown() ? replayOffsetExtension.getReplayTimeFromSessionTime((int) sessionTime) : 0,
                    getClient().getModel().getCar(event.getCarId()),
                    sessionId);
        } else {
            float timeDif = stagedAccident.getSessionLatestTime() - sessionTime;
            if (timeDif > 1000) {
                commitAccident(stagedAccident);
                stagedAccident = new IncidentInfo(sessionTime,
                        replayOffsetExtension.isReplayTimeKnown() ? replayOffsetExtension.getReplayTimeFromSessionTime((int) sessionTime) : 0,
                        getClient().getModel().getCar(event.getCarId()),
                        sessionId);
            } else {
                stagedAccident = stagedAccident.addCar(sessionTime,
                        getClient().getModel().getCar(event.getCarId()),
                        System.currentTimeMillis());
            }
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

    private void commitAccident(IncidentInfo a) {
        List<IncidentInfo> newAccidents = new LinkedList<>(accidents);
        newAccidents.add(a);
        accidents = newAccidents;

        LOG.info("publishing accident");
        EventBus.publish(new Accident(a));
    }

}
