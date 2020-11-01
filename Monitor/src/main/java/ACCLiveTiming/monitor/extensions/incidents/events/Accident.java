/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ACCLiveTiming.monitor.extensions.incidents.events;

import ACCLiveTiming.monitor.eventbus.Event;
import ACCLiveTiming.monitor.extensions.incidents.IncidentInfo;

/**
 *
 * @author Leonard
 */
public class Accident extends Event {

    private IncidentInfo info;

    public Accident(IncidentInfo info) {
        this.info = info;
    }

    public IncidentInfo getInfo() {
        return info;
    }

}
