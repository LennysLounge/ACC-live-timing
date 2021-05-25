/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.replayoffset;

import racecontrol.client.extension.ACCLiveTimingExtensionFactory;
import racecontrol.extensions.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class ReplayOffsetExtensionFactory
        implements ACCLiveTimingExtensionFactory {

    private static ReplayOffsetExtension extension;

    public ReplayOffsetExtensionFactory() {
    }

    @Override
    public String getName() {
        return "Replay Offset";
    }

    @Override
    public void createExtension() {
        removeExtension();
        extension = new ReplayOffsetExtension();
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
