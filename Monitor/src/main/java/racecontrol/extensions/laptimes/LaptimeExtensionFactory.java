/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.laptimes;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class LaptimeExtensionFactory
        implements AccBroadcastingClientExtensionModule {

    private AccClientExtension extension;

    @Override
    public String getName() {
        return "Laptime extension";
    }

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        extension = new LapTimeExtension(false);
        return extension;
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }

    @Override
    public void removeExtension() {
        if (extension != null) {
            extension.removeExtension();
            extension = null;
        }
    }
}
