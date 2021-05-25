/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.incidents;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class IncidentExtensionFactory
        implements racecontrol.client.extension.AccBroadcastingClientExtensionModule {

    private IncidentExtension extension;

    @Override
    public String getName() {
        return "Incident extension";
    }

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        extension = new IncidentExtension();
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
