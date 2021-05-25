/*
 * Copyright (c) 2021 Leonard Sch�ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.cameracontrolraw;

import racecontrol.client.extension.ACCLiveTimingExtensionFactory;
import racecontrol.extensions.AccClientExtension;
import racecontrol.extensions.GeneralExtentionConfigPanel;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class CameraControlRawFactory
        implements ACCLiveTimingExtensionFactory {

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
