/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.results;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class ResultsExtensionModule
        implements AccBroadcastingClientExtensionModule {

    @Override
    public AccClientExtension createExtension() {
        return new ResultsExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
}
