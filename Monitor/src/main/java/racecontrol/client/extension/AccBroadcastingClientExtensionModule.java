/*
 * Copyright (c) 2021 Leonard Sch�ngel
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
     * Returns true if the extension is enabled.
     * @return true if the extension is enabled.
     */
    public boolean isEnabled();
    
    /**
     * Creates the client extension.
     * @return the created extension.
     */
    public AccClientExtension createExtension();

    /**
     * Gives the configuration dialog panel for this extension. Returns null if
     * this extension does not have a configuration panel.
     *
     * @return
     */
    public LPContainer getExtensionConfigurationPanel();
}
