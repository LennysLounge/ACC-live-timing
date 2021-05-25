/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.logging;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class LoggingExtensionModule
        implements AccBroadcastingClientExtensionModule {
    
    @Override
    public AccClientExtension createExtension() {
        if (GeneralExtentionConfigPanel.getInstance().isLoggingEnabled()) {
            return new LoggingExtension();
        }
        return null;
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }

}
