/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.debug;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class DebugExtensionModule
        implements AccBroadcastingClientExtensionModule {

    private final DebugConfigPanel configPanel;
    private DebugExtension extension;

    public DebugExtensionModule() {
        configPanel = new DebugConfigPanel();
    }

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        if (configPanel.isExtensionEnabled()) {
            extension = new DebugExtension();
        }
        return extension;
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return configPanel;
    }

    @Override
    public void removeExtension() {
        if (extension != null) {
            extension.removeExtension();
            extension = null;
        }
    }
}
