/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.incidents;

import racecontrol.client.extension.AccBroadcastingClientExtensionModule;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class IncidentExtensionModule
        implements AccBroadcastingClientExtensionModule {

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public AccClientExtension createExtension() {
        return new IncidentExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
    
    @Override
    public Class getExtensionClass() {
        return IncidentExtension.class;
    }
}
