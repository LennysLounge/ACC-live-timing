/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming.monitor;

import ACCLiveTiming.monitor.extensions.AccClientExtension;
import ACCLiveTiming.monitor.visualisation.gui.LPContainer;
import javax.swing.JPanel;

/**
 *
 * @author Leonard
 */
public interface ACCLiveTimingExtension {
    /**
     * Returns the name for this extension.
     * @return The name.
     */
    public String getName();
    /**
     * Gives the client extension.
     * @return the client extension.
     */
    public AccClientExtension getExtension();
    /**
     * Gives the visualisation panel for this extension. Returns null if 
     * this extension has no visualisation panel.
     * @return 
     */
    public LPContainer getExtensionPanel();
    /**
     * Gives the configuration dialog panel for this extension. Returns null
     * if this extension does not have a configuration panel.
     * @return 
     */
    public JPanel getExtensionConfigurationPanel();
    
    
}
