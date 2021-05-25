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
public class CameraControlRawFactory
        implements AccBroadcastingClientExtensionModule {

    CameraControlRawExtension extension;

    @Override
    public String getName() {
        return "Camera Controls";
    }

    @Override
    public void createExtension() {
        removeExtension();
        if (GeneralExtentionConfigPanel.getInstance().isCameraControlsEnabled()) {
            extension = new CameraControlRawExtension();
        }

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

    @Override
    public AccClientExtension getExtension() {
        return extension;
    }

}
