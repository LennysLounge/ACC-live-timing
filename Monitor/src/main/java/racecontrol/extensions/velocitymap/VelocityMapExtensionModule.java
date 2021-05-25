/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.velocitymap;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class VelocityMapExtensionModule
        implements AccBroadcastingClientExtensionModule {

    @Override
    public boolean isEnabled() {
        return GeneralExtentionConfigPanel.getInstance().isVelocityMapEnabled();
    }

    @Override
    public AccClientExtension createExtension() {
        return new VelocityMapExtension();
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
}
