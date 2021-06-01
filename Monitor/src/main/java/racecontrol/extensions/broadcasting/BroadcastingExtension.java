/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.broadcasting;

import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.eventbus.Event;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class BroadcastingExtension
        extends AccClientExtension {

    private BroadcastingPanel panel;

    public BroadcastingExtension(AccBroadcastingClient client) {
        super(client);

        this.panel = new BroadcastingPanel();
    }

    @Override
    public LPContainer getPanel() {
        return panel;
    }

    @Override
    public void onEvent(Event e) {
    }

}
