/*
 * Copyright (c) 2021 Leonard Schüngel
 *
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.client.extension;

import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public interface AccBroadcastingClientExtensionModule {

    /**
     * Returns the name for this extension.
     *
     * @return The name.
     */
    public String getName();

    /**
     * Creates the client extension.
     * @return the created extension.
     */
    public AccClientExtension createExtension();

    /**
     * Removes the extension.
     */
    public void removeExtension();

    /**
     * Gives the configuration dialog panel for this extension. Returns null if
     * this extension does not have a configuration panel.
     *
     * @return
     */
    public LPContainer getExtensionConfigurationPanel();
}
