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

    private AccClientExtension extension;

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        if (GeneralExtentionConfigPanel.getInstance().isLoggingEnabled()) {
            extension = new LoggingExtension();
        }
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
