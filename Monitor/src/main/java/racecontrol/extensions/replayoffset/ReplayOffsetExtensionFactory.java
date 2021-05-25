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
public class ReplayOffsetExtensionFactory
        implements AccBroadcastingClientExtensionModule {

    private static ReplayOffsetExtension extension;

    public ReplayOffsetExtensionFactory() {
    }

    @Override
    public String getName() {
        return "Replay Offset";
    }

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        extension = new ReplayOffsetExtension();
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
