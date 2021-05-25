/*
 * Copyright (c) 2021 Leonard Schüngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.cameracontrolraw;

import racecontrol.client.extension.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;
import racecontrol.client.extension.AccBroadcastingClientExtensionModule;

/**
 *
 * @author Leonard
 */
public class CameraControlRawModule
        implements AccBroadcastingClientExtensionModule {

    CameraControlRawExtension extension;

    @Override
    public AccClientExtension createExtension() {
        removeExtension();
        if (GeneralExtentionConfigPanel.getInstance().isCameraControlsEnabled()) {
            extension = new CameraControlRawExtension();
        }
        return extension;
    }

    @Override
    public void removeExtension() {
        if (extension != null) {
            extension.removeExtension();
            extension = null;
        }
    }

    @Override
    public LPContainer getExtensionConfigurationPanel() {
        return null;
    }
}
