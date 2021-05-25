/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.livetiming;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.persistance.PersistantConfig;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class LiveTimingExtensionFactory
        implements AccBroadcastingClientExtensionModule {

    private AccClientExtension extension;

    @Override
    public String getName() {
        return "Live Timing Extension";
    }

    @Override
    public void createExtension() {        
        removeExtension();
        if (GeneralExtentionConfigPanel.getInstance().isLiveTimingEnabled()) {
            extension = new LiveTimingExtension();
        }
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

    @Override
    public AccClientExtension getExtension() {
        return extension;
    }

}
