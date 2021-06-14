/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.base.complexdata;

import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.AccBroadcastingData;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.eventbus.Event;
import racecontrol.visualisation.gui.LPContainer;

/**
 * An extension that holds current and past game data and processed data from
 * extensions.
 *
 * @author Leonard
 */
public class ComplexDataExtension
        extends AccClientExtension {

    public ComplexDataExtension(AccBroadcastingClient client) {
        super(client);
    }

    @Override
    public LPContainer getPanel() {
        return null;
    }

    @Override
    public void onEvent(Event e) {
    }
    
    /**
     * Returns the current modle from the connection client.
     * @return 
     */
    public AccBroadcastingData getCurrentModel(){
        return getClient().getModel();
    }
    
    
    
    
}
