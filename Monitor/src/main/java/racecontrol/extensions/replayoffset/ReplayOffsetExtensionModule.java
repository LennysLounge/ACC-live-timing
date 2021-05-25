/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.replayoffset;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class ReplayOffsetExtensionModule
        implements AccBroadcastingClientExtensionModule {

    @Override
    public boolean isEnabled() {
        return true;
    }

    public ReplayOffsetExtensionModule() {
    }

    @Override
    public AccClientExtension createExtension() {
        return new ReplayOffsetExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
}
