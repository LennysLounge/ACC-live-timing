/*
 * Copyright (c) 2021 Leonard Sch?ngel
 * 
 * For licensing information see the included license (LICENSE.txt)
 */
package racecontrol.extensions.base.timing;

import java.util.LinkedList;
import java.util.List;
import static processing.core.PConstants.OPEN;
import racecontrol.visualisation.gui.LPContainer;

/**
 *
 * @author Leonard
 */
public class TimingPanel
        extends LPContainer {

    private TimingExtension extension;

    public List<Float> rawRaceDistance = new LinkedList<>();
    public List<Float> raceDistance = new LinkedList<>();

    public TimingPanel(TimingExtension extension) {
        this.extension = extension;
    }

    @Override
    public void draw() {
        applet.fill(0);
        applet.rect(20, 20, getWidth() - 40, getHeight() - 40);

        float max = -1;
        float min = 99999;
        for (Float v : rawRaceDistance) {
            if (v > max) {
                max = v;
            }
            if (v < min) {
                min = v;
            }
        }

        applet.stroke(100);
        for (int i = (int) min; i < max; i++) {
            float y = applet.map(i, min, max, getHeight() - 20, 20);
            applet.line(20, y, getWidth() - 20, y);
        }

        applet.stroke(255);
        applet.noFill();
        applet.beginShape();
        for (int i = 0; i < rawRaceDistance.size(); i++) {
            float y = applet.map(rawRaceDistance.get(i), min, max, getHeight() - 20, 20);
            float x = applet.map(i, 0, rawRaceDistance.size() - 1, 20, getWidth() - 20);
            applet.vertex(x, y);
        }
        applet.endShape(OPEN);

        applet.stroke(255, 255, 0);
        applet.noFill();
        applet.beginShape();
        for (int i = 0; i < raceDistance.size(); i++) {
            float y = applet.map(raceDistance.get(i), min, max, getHeight() - 20, 20);
            float x = applet.map(i, 0, raceDistance.size() - 1, 20, getWidth() - 20);
            applet.vertex(x, y);
        }
        applet.endShape(OPEN);

    }

}
