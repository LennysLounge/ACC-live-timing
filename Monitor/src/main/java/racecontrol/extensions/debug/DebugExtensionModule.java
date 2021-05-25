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

    public DebugExtensionModule() {
        configPanel = new DebugConfigPanel();
    }

    @Override
    public boolean isEnabled() {
        return configPanel.isExtensionEnabled();
    }

    @Override
    public AccClientExtension createExtension() {
        return new DebugExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return configPanel;
    }

}
