/*
 * Copyright (c) 2021 Leonard Sch�ngel
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
public class ResultsExtensionFactory
        implements AccBroadcastingClientExtensionModule {

    private AccClientExtension extension;

    @Override
    public String getName() {
        return "Results extension";
    }

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        extension = new ResultsExtension();
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
