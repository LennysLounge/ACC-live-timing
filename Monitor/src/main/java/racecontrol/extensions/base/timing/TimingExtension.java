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
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.RealtimeInfo;
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

    private TimingPanel panel;

    private Map<Integer, List<Float>> rawRaceDistance = new HashMap<>();

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
            if (!rawRaceDistance.containsKey(info.getCarId())) {
                rawRaceDistance.put(info.getCarId(), new LinkedList<>());
            }
            float raceDistance = info.getSplinePosition() + info.getLaps();
            rawRaceDistance.get(info.getCarId()).add(raceDistance);
        } else if (e instanceof RealtimeUpdate) {
            int focusedCarId = ((RealtimeUpdate) e).getSessionInfo().getFocusedCarIndex();

            if (rawRaceDistance.containsKey(focusedCarId)) {
                panel.rawRaceDistance = rawRaceDistance.get(focusedCarId);
                panel.invalidate();
            }
        } else if(e instanceof SessionChanged){
            rawRaceDistance.clear();
        }
    }

}
