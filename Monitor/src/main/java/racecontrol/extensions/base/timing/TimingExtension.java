/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.base.timing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import racecontrol.client.AccBroadcastingClient;
import racecontrol.client.data.CarInfo;
import racecontrol.client.events.RealtimeUpdate;
import racecontrol.client.extension.AccClientExtension;
import racecontrol.eventbus.Event;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class TimingExtension
        extends AccClientExtension {

    public TimingExtension(AccBroadcastingClient client) {
        super(client);
    }

    @Override
    public LPContainer getPanel() {
        return null;
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof RealtimeUpdate) {

            try {
                for (CarInfo car : getClient().getModel().getCarsInfo().values()) {
                    String fileName = String.valueOf(car.getCarNumber());
                    FileWriter fw = new FileWriter(fileName, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(String.valueOf(car.getRealtime().getSplinePosition()));
                    bw.write("\t");
                    bw.write(String.valueOf(car.getRealtime().getLaps()));
                    bw.newLine();
                    bw.close();
                }
            } catch (Exception ex) {

            }
        }
    }

}
