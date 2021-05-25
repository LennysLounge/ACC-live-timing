/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.livetiming;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class LiveTimingExtensionModule
        implements AccBroadcastingClientExtensionModule {

    @Override
    public AccClientExtension createExtension() {
        if (GeneralExtentionConfigPanel.getInstance().isLiveTimingEnabled()) {
            return new LiveTimingExtension();
        }
        return null;
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
}
