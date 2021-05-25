/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.debug;

import racecontrol.eventbus.Event;
import racecontrol.eventbus.EventBus;
import racecontrol.eventbus.EventListener;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.replayoffset.ReplayStart;
import racecontrol.visualisation.gui.LPContainer;

/**
 * A Basic Extension to test stuff out with.
 *
 * @author Leonard
 */
public class DebugExtension
        implements AccClientExtension, EventListener {

    DebugPanel panel;

    public DebugExtension() {
        this.panel = new DebugPanel();
        EventBus.register(this);
    }

    @Override
    public LPContainer getPanel() {
        return panel;
    }

    @Override
    public void removeExtension() {
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof ReplayStart) {
            panel.setReplayTimeKnown();
        }
    }

}
