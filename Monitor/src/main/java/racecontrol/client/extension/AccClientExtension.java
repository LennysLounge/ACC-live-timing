/*
 * Copyright (c) 2021 Leonard Schüngel
 *
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.client.extension;

import racecontrol.client.AccBroadcastingClient;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public abstract class AccClientExtension {

    /**
     * Reference to the client.
     */
    private final AccBroadcastingClient client;

    public AccClientExtension(AccBroadcastingClient client) {
        this.client = client;
    }

    /**
     * Returns the attached panel for this extension.
     *
     * @return The attached panel.
     */
    public abstract LPContainer getPanel();

    /**
     * Removes all necessary references related to this extension.
     */
    public abstract void removeExtension();

    /**
     * Returns the reference to the client.
     *
     * @return the client.
     */
    public AccBroadcastingClient getClient() {
        return client;
    }
}
