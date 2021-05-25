/*
 * Copyright (c) 2021 Leonard Schüngel
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
public class VelocityMapExtensionFactory
        implements AccBroadcastingClientExtensionModule {

    VelocityMapExtension extension;

    @Override
    public String getName() {
        return "Velocity Map";
    }

    @Override
    public void createExtension() {
        removeExtension();
        if (GeneralExtentionConfigPanel.getInstance().isVelocityMapEnabled()) {
            extension = new VelocityMapExtension();
        }
    }

    @Override
    public void removeExtension() {
        if (extension != null) {
            extension.removeExtension();
        }
        extension = null;
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }

    @Override
    public AccClientExtension getExtension() {
        return extension;
    }

}
